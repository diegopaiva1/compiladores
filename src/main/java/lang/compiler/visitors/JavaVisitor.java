package lang.compiler.visitors;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.apache.commons.lang3.StringUtils;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.miscellaneous.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.ast.types.*;

public class JavaVisitor extends AstVisitor {
  private Program program;
  private Scope currentScope;
  private STGroup groupTemplate;

  public JavaVisitor() {
    this.groupTemplate = new STGroupFile("src/main/java/lang/compiler/templates/java.stg");
  }

  @Override
  public Object visitAddition(Addition add) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", add.getLeft().accept(this));
    template.add("symbol", add.getSymbol());
    template.add("rightExpr", add.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitAnd(And and) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", and.getLeft().accept(this));
    template.add("symbol", and.getSymbol());
    template.add("rightExpr", and.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    return groupTemplate.getInstanceOf("lvalue").add("name", arrayAccess.toString());
  }

  @Override
  public Object visitArrayType(ArrayType arrayType) {
    return groupTemplate.getInstanceOf("type").add("name", ((ST) arrayType.getType().accept(this)).render() + "[]");
  }

  @Override
  public Object visitAssignableFunctionCall(AssignableFunctionCall fCall) {
    ST template = groupTemplate.getInstanceOf("assignableFunctionCall");

    // Java visitor will be executed after we ensured callee is valid and index is a IntLiteral
    Function callee = null;
    int index = ((IntLiteral) fCall.getIndex()).getValue();

    for (Function f : program.getFunctionSet()) {
      if (f.getId().getName().equals(fCall.getId().getName())) {
        if (fCall.getArgs().size() == f.getParameters().size()) {
          boolean match = true;

          // Check parameters compatibility
          for (int i = 0; i < f.getParameters().size(); i++) {
            AbstractType argType = (AbstractType) currentScope.search(fCall.getArgs().get(i).toString());
            AbstractType parameterType = (AbstractType) f.getParameters().get(i).getType();

            // Incompatible types
            if (argType != null && !argType.match(parameterType))
              match = false;
          }

          if (match)
            callee = f;
        }
      }
    }

    template.add("type", callee.getReturnTypes().get(index).accept(this));
    template.add("id", fCall.getId().accept(this));

    for (AbstractExpression expr : fCall.getArgs())
      template.add("args", expr.accept(this));

    template.add("index", fCall.getIndex().accept(this));
    return template;
  }

  @Override
  public Object visitAssignment(Assignment assignment) {
    if (!(assignment.getExpression() instanceof New) ||
    (assignment.getExpression() instanceof New &&
    ((New) assignment.getExpression()).getType() instanceof ArrayType &&
    !(((ArrayType) ((New) assignment.getExpression()).getType()).getType() instanceof ArrayType) &&
    ((New) assignment.getExpression()).getExpression() != null
    )) {
      ST template = groupTemplate.getInstanceOf("assignment");
      template.add("lvalue", assignment.getLvalue().accept(this));
      template.add("expr", assignment.getExpression().accept(this));
      return template;
    }
    else if (assignment.getExpression() instanceof New &&
    ((New) assignment.getExpression()).getType() instanceof DataType) {
      ST template = groupTemplate.getInstanceOf("assignment");
      template.add("lvalue", assignment.getLvalue().accept(this));
      template.add("expr", ((ST) assignment.getExpression().accept(this)).render() + "()");
      return template;
    }
    else
      return groupTemplate.getInstanceOf("comment").add("comment", "new command unnecessary: " + assignment.getExpression().toString());
  }

  @Override
  public Object visitBalancedParentheses(BalancedParenthesesExpression balanced) {
    return groupTemplate.getInstanceOf(
      "balancedParenthesesExpression").add("expr", balanced.getExpression().accept(this)
    );
  }

  @Override
  public Object visitBoolLiteral(BoolLiteral b) {
    return groupTemplate.getInstanceOf("literal").add("value", b.getValue());
  }

  @Override
  public Object visitBoolType(BoolType boolType) {
    return groupTemplate.getInstanceOf("type").add("name", "boolean");
  }

  @Override
  public Object visitCharLiteral(CharLiteral c) {
    return groupTemplate.getInstanceOf("literal").add("value", c.toString());
  }

  @Override
  public Object visitCharType(CharType charType) {
    return groupTemplate.getInstanceOf("type").add("name", "char");
  }

  @Override
  public Object visitCommandScope(CommandScope cmdScope) {
    currentScope = cmdScope.getScope();
    ST template = groupTemplate.getInstanceOf("commandScope");

    for (Map.Entry<String, AbstractType> decl : cmdScope.getScope().getSymbolTable().entrySet()) {
      ST declTemplate = groupTemplate.getInstanceOf("decl");
      declTemplate.add("type", decl.getValue().accept(this));
      declTemplate.add("id", decl.getKey());
      template.add("decls", declTemplate);
    }

    for (AbstractCommand cmd : cmdScope.getCommands()) {
      if (cmd instanceof If || cmd instanceof IfElse || cmd instanceof Iterate || cmd instanceof CommandScope)
        template.add("cmds", cmd.accept(this));
      else
        template.add("cmds", new ST("<cmd>;").add("cmd", cmd.accept(this)));
    }

    currentScope = currentScope.getFather();
    return template;
  }

  @Override
  public Object visitData(Data data) {
    ST dataTemplate = groupTemplate.getInstanceOf("data");
    dataTemplate.add("name", data.getType().accept(this));

    for (Map.Entry<String, AbstractType> property : data.getProperties().entrySet()) {
      ST declTemplate = groupTemplate.getInstanceOf("decl");
      String var = property.getKey();
      AbstractType type = property.getValue();
      declTemplate.add("type", type.accept(this));
      declTemplate.add("id", var);
      dataTemplate.add("decls", declTemplate);
    }

    return dataTemplate;
  }

  @Override
  public Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    return groupTemplate.getInstanceOf("lvalue").add("name", dataIdentifierAccess.toString());
  }

  @Override
  public Object visitDataType(DataType dataType) {
    return groupTemplate.getInstanceOf("type").add("name", dataType.toString());
  }

  @Override
  public Object visitDeclaration(Declaration decl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitDivision(Division div) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", div.getLeft().accept(this));
    template.add("symbol", div.getSymbol());
    template.add("rightExpr", div.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitEqual(Equal eq) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", eq.getLeft().accept(this));
    template.add("symbol", eq.getSymbol());
    template.add("rightExpr", eq.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitFloatLiteral(FloatLiteral f) {
    return groupTemplate.getInstanceOf("literal").add("value", f.getValue() + "f");
  }

  @Override
  public Object visitFloatType(FloatType floatType) {
    return groupTemplate.getInstanceOf("type").add("name", "float");
  }

  @Override
  public Object visitFunction(Function f) {
    currentScope = f.getScope();
    ST template = groupTemplate.getInstanceOf("function");
    template.add("name", f.getId().getName());

    if (f.getReturnTypes().size() == 0)
      template.add("returnType", "void");
    else // f.getReturnsTypes().size() > 1
      template.add("returnType", "Object[]");

    if (f.getId().getName().equals("main")) {
      ST declTemplate = groupTemplate.getInstanceOf("decl");
      declTemplate.add("type", "String[]");
      declTemplate.add("id", "args");
      template.add("params", declTemplate);
    }
    else {
      for (Parameter param : f.getParameters()) {
        ST declTemplate = groupTemplate.getInstanceOf("decl");
        declTemplate.add("type", param.getType().accept(this));
        declTemplate.add("id", param.getId().accept(this));
        template.add("params", declTemplate);
      }
    }

    for (Map.Entry<String, AbstractType> decl : f.getScope().getSymbolTable().entrySet()) {
      boolean isParam = false;

      for (int i = 0; i < f.getParameters().size(); i++)
        if (decl.getKey().equals(f.getParameters().get(i).getId().getName()))
          isParam = true;

      if (!isParam) {
        ST declTemplate = groupTemplate.getInstanceOf("decl");
        declTemplate.add("type", decl.getValue().accept(this));
        declTemplate.add("id", decl.getKey());
        template.add("decls", declTemplate);
      }
    }

    for (AbstractCommand cmd : f.getCommands()) {
      if (cmd instanceof If || cmd instanceof IfElse || cmd instanceof Iterate || cmd instanceof CommandScope)
        template.add("cmds", cmd.accept(this));
      else
        template.add("cmds", new ST("<cmd>;").add("cmd", cmd.accept(this)));
    }

    return template;
  }

  @Override
  public Object visitIdentifier(Identifier id) {
    return groupTemplate.getInstanceOf("lvalue").add("name", id.toString());
  }

  @Override
  public Object visitIf(If ifCmd) {
    ST template = groupTemplate.getInstanceOf("if");
    template.add("expr", ifCmd.getExpression().accept(this));

    if(ifCmd.getScopeCommand() instanceof CommandScope)
      template.add("ifCmd", ifCmd.getScopeCommand().accept(this));
    else
      template.add("ifCmd", ((ST) ifCmd.getScopeCommand().accept(this)).render() + ";");

    return template;
  }

  @Override
  public Object visitIfElse(IfElse ifElseCmd) {
    ST template = groupTemplate.getInstanceOf("if");
    template.add("expr", ifElseCmd.getExpression().accept(this));

    if(ifElseCmd.getIfScopeCommand() instanceof CommandScope)
      template.add("ifCmd", ifElseCmd.getIfScopeCommand().accept(this));
    else
      template.add("ifCmd", ((ST) ifElseCmd.getIfScopeCommand().accept(this)).render() + ";");

    if(ifElseCmd.getElseScopeCommand() instanceof CommandScope)
      template.add("elseCmd", ifElseCmd.getElseScopeCommand().accept(this));
    else
      template.add("elseCmd", ((ST) ifElseCmd.getElseScopeCommand().accept(this)).render() + ";");

    return template;
  }

  @Override
  public Object visitIntLiteral(IntLiteral i) {
    return groupTemplate.getInstanceOf("literal").add("value", i.getValue());
  }

  @Override
  public Object visitIntType(IntType intType) {
    return groupTemplate.getInstanceOf("type").add("name", "int");
  }

  @Override
  public Object visitErrorType(ErrorType errorType) {
    // This should never be reached!
    return null;
  }

  @Override
  public Object visitIterate(Iterate iterateCmd) {
    ST template = groupTemplate.getInstanceOf("iterate");
    template.add("expr", iterateCmd.getExpression().accept(this));

    if(iterateCmd.getCommand() instanceof CommandScope)
      template.add("cmd", iterateCmd.getCommand().accept(this));
    else
      template.add("cmd", ((ST) iterateCmd.getCommand().accept(this)).render() + ";");


    return template;
  }

  @Override
  public Object visitLessThan(LessThan lt) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", lt.getLeft().accept(this));
    template.add("symbol", lt.getSymbol());
    template.add("rightExpr", lt.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitModule(Module mod) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", mod.getLeft().accept(this));
    template.add("symbol", mod.getSymbol());
    template.add("rightExpr", mod.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitMultiplication(Multiplication mult) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", mult.getLeft().accept(this));
    template.add("symbol", mult.getSymbol());
    template.add("rightExpr", mult.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitNegate(Negate neg) {
    ST template = groupTemplate.getInstanceOf("unaryOperator");
    template.add("symbol", neg.getSymbol());
    template.add("expr", neg.getExpression().accept(this));
    return template;
  }

  @Override
  public Object visitNew(New newCmd) {
    ST template = groupTemplate.getInstanceOf("new");

    if (newCmd.getExpression() != null) {
      template.add("size", newCmd.getExpression().accept(this));
      template.add("type", ((ArrayType) newCmd.getType()).getType().accept(this));
    }
    else
      template.add("type", newCmd.getType().accept(this));

    return template;
  }

  @Override
  public Object visitNot(Not not) {
    ST template = groupTemplate.getInstanceOf("unaryOperator");
    template.add("symbol", not.getSymbol());
    template.add("expr", not.getExpression().accept(this));
    return template;
  }

  @Override
  public Object visitNotEqual(NotEqual neq) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", neq.getLeft().accept(this));
    template.add("symbol", neq.getSymbol());
    template.add("rightExpr", neq.getRight().accept(this));
    return template;
  }

  @Override
  public Object visitNullLiteral(NullLiteral n) {
    return groupTemplate.getInstanceOf("literal").add("value", "null");
  }

  @Override
  public Object visitParameter(Parameter param) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitPrint(Print printCmd) {
    return groupTemplate.getInstanceOf("print").add("expr", printCmd.getExpression().accept(this));
  }

  @Override
  public Object visitProgram(Program program) {
    this.program = program;
    ST template = groupTemplate.getInstanceOf("program");
    String className = program.getFileName();

    if (className.contains("/"))
      className = StringUtils.substringBetween(className, "/", ".");
    else
      className = className.substring(0, className.lastIndexOf('.'));

    className = StringUtils.capitalize(className.toLowerCase());
    template.add("className", className);

    for (Data d : program.getDataSet())
      template.add("data", d.accept(this));

    for (Function f : program.getFunctionSet())
      template.add("functions", f.accept(this));

    try (PrintStream out = new PrintStream(new FileOutputStream("target/generated-sources/" + className + ".java"))) {
        out.print(template.render());
    }
    catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  @Override
  public Object visitRead(Read readCmd) {
    return groupTemplate.getInstanceOf("read").add("lvalue", readCmd.getLvalue().accept(this));
  }

  @Override
  public Object visitReturn(Return returnCmd) {
    ST template = groupTemplate.getInstanceOf("return");
    template.add("size", returnCmd.getExpressions().size());

    for (int i = 0; i < returnCmd.getExpressions().size(); i++) {
      ST listElementTemplate = groupTemplate.getInstanceOf("listElement");
      listElementTemplate.add("pos", i);
      listElementTemplate.add("value", returnCmd.getExpressions().get(i).accept(this));
      template.add("list", listElementTemplate);
    }

    template.add("value", "_returnList");
    return template;
  }

  @Override
  public Object visitStaticFunctionCall(StaticFunctionCall fCall) {
    ST template = groupTemplate.getInstanceOf("staticFunctionCall");

    // Java visitor will be executed after we ensured callee is valid
    Function callee = null;

    for (Function f : program.getFunctionSet()) {
      if (f.getId().getName().equals(fCall.getId().getName())) {
        if (fCall.getArgs().size() == f.getParameters().size()) {
          boolean match = true;

          // Check parameters compatibility
          for (int i = 0; i < f.getParameters().size(); i++) {
            AbstractType argType = (AbstractType) currentScope.search(fCall.getArgs().get(i).toString());
            AbstractType parameterType = (AbstractType) f.getParameters().get(i).getType();

            // Incompatible types
            if (argType != null && !argType.match(parameterType))
              match = false;
          }

          if (match)
            callee = f;
        }
      }
    }

    for (int i = 0; i < fCall.getLvalues().size(); i++) {
      AbstractLvalue lvalue = fCall.getLvalues().get(i);
      ST assignmentTemplate = groupTemplate.getInstanceOf("assignment");
      assignmentTemplate.add("lvalue", lvalue.accept(this));

      ST assignableFunctionCallTemplate = groupTemplate.getInstanceOf("assignableFunctionCall");
      assignableFunctionCallTemplate.add("type", callee.getReturnTypes().get(i).accept(this));
      assignableFunctionCallTemplate.add("id", fCall.getId().accept(this));

      for (AbstractExpression arg : fCall.getArgs())
        assignableFunctionCallTemplate.add("args", arg);

      assignableFunctionCallTemplate.add("index", i);
      assignmentTemplate.add("expr", assignableFunctionCallTemplate);
      template.add("assignments", assignmentTemplate);
    }

    return template;
  }

  @Override
  public Object visitSubtraction(Subtraction sub) {
    ST template = groupTemplate.getInstanceOf("binaryOperator");
    template.add("leftExpr", sub.getLeft().accept(this));
    template.add("symbol", sub.getSymbol());
    template.add("rightExpr", sub.getRight().accept(this));
    return template;
  }
}
