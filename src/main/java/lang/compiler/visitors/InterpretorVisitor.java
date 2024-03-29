package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import javax.lang.model.type.NullType;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.miscellaneous.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.ast.types.*;

public class InterpretorVisitor extends AstVisitor {
  private Stack<Map<String, Object>> env;
  private HashMap<String, Function> functions;

  public InterpretorVisitor() {
    this.env = new Stack<>();
    this.env.push(new HashMap<String, Object>());
    this.functions = new HashMap<>();
  }

  public Object visitProgram(Program program) {
    // Store functions before evaluation
    for (Function f : program.getFunctionSet())
      functions.put(f.getId().getName(), f);

    if (!functions.containsKey("main"))
      throw new RuntimeException("function main does not exist");

    for (Function f : program.getFunctionSet())
      if (f.getId().getName().equals("main"))
        f.accept(this);

    return null;
  }

  public Boolean visitBoolLiteral(BoolLiteral b) {
    return b.getValue();
  }

  public Character visitCharLiteral(CharLiteral c) {
    return c.getValue();
  }

  public Float visitFloatLiteral(FloatLiteral f) {
    return f.getValue();
  }

  public Integer visitIntLiteral(IntLiteral i) {
    return i.getValue();
  }

  public NullType visitNullLiteral(NullLiteral n) {
    return n.getValue();
  }

  public Number visitAddition(Addition add) {
    try {
      Number leftNumber = (Number) add.getLeft().accept(this);
      Number rightNumber = (Number) add.getRight().accept(this);
      return leftNumber.floatValue() + rightNumber.floatValue();
    } catch (Exception e) {
      throw new RuntimeException("Addition OP: Invalid cast");
    }
  }

  public Boolean visitAnd(And and) {
    try {
      Boolean leftBoolean = (Boolean) and.getLeft().accept(this);
      Boolean rightBoolean = (Boolean) and.getRight().accept(this);
      return leftBoolean.booleanValue() && rightBoolean.booleanValue();
    } catch (Exception e) {
      throw new RuntimeException("And OP: Invalid cast");
    }
  }

  public Number visitDivision(Division div) {
    try {
      Number leftNumber = (Number) div.getLeft().accept(this);
      Number rightNumber = (Number) div.getRight().accept(this);
      return leftNumber.floatValue() / rightNumber.floatValue();
    } catch (Exception e) {
      throw new RuntimeException("Division OP: Invalid cast");
    }
  }

  public Boolean visitEqual(Equal eq) {
    return eq.getLeft().accept(this) == eq.getRight().accept(this);
  }

  public Boolean visitLessThan(LessThan lt) {
    Object leftExpr = lt.getLeft().accept(this);
    Object rightExpr = lt.getRight().accept(this);

    if (leftExpr instanceof Number && rightExpr instanceof Number) {
      Number leftNumber = (Number) leftExpr;
      Number rightNumber = (Number) rightExpr;
      return leftNumber.floatValue() < rightNumber.floatValue();
    } else if (leftExpr instanceof Number && rightExpr instanceof Character) {
      Number leftNumber = (Number) leftExpr;
      Character rightChar = (Character) rightExpr;
      return leftNumber.floatValue() < rightChar.charValue();
    } else if (leftExpr instanceof Character && rightExpr instanceof Character) {
      Character leftChar = (Character) leftExpr;
      Character rightChar = (Character) rightExpr;
      return leftChar.charValue() < rightChar.charValue();
    } else if (leftExpr instanceof Character && rightExpr instanceof Number) {
      Character leftChar = (Character) leftExpr;
      Number rightNumber = (Number) rightExpr;
      return leftChar.charValue() < rightNumber.floatValue();
    } else {
      throw new RuntimeException("Equal OP: Invalid operators");
    }
  }

  public Number visitModule(Module mod) {
    try {
      Number leftNumber = (Number) mod.getLeft().accept(this);
      Number rightNumber = (Number) mod.getRight().accept(this);
      return leftNumber.floatValue() % rightNumber.floatValue();
    } catch (Exception e) {
      throw new RuntimeException("Module OP: Invalid cast");
    }
  }

  public Number visitMultiplication(Multiplication mult) {
    try {
      Number leftNumber = (Number) mult.getLeft().accept(this);
      Number rightNumber = (Number) mult.getRight().accept(this);
      return leftNumber.floatValue() * rightNumber.floatValue();
    } catch (Exception e) {
      throw new RuntimeException("Multiplication OP: Invalid cast");
    }
  }

  public Boolean visitNotEqual(NotEqual neq) {
    return neq.getLeft().accept(this) != neq.getRight().accept(this);
  }

  public Number visitSubtraction(Subtraction sub) {
    try {
      Number leftNumber = (Number) sub.getLeft().accept(this);
      Number rightNumber = (Number) sub.getRight().accept(this);
      return leftNumber.floatValue() - rightNumber.floatValue();
    } catch (Exception e) {
      throw new RuntimeException("Subtraction OP: Invalid cast");
    }
  }

  public Boolean visitNot(Not not) {
    try {
      boolean booleanExpr = (boolean) not.getExpression().accept(this);
      return !booleanExpr;
    } catch (Exception e) {
      throw new RuntimeException("Not OP: Invalid cast");
    }
  }

  public Number visitNegate(Negate neg) {
    try {
      Number num = (Number) neg.getExpression().accept(this);
      return -(num.floatValue());
    } catch (Exception e) {
      throw new RuntimeException("Negate OP: Invalid cast");
    }
  }

  public List<Object> visitFunction(Function f) {
    for (AbstractCommand cmd : f.getCommands()) {
      Object result = cmd.accept(this);

      // The only command that returns a List is the 'Return' command
      if (result instanceof List)
        return (List<Object>) result;
    }

    return new ArrayList<>();
  }

  public List<Object> visitReturn(Return returnCmd) {
    List<Object> objects = new ArrayList<>();

    for (AbstractExpression expr : returnCmd.getExpressions())
      objects.add(expr.accept(this));

    return objects;
  }

  public Object visitAssignableFunctionCall(AssignableFunctionCall fCall) {
    try {
      Map<String, Object> localEnv = new HashMap<>();
      Function f = (Function) functions.get(fCall.getId().getName());

      // Unpack parameters
      for (int i = 0; i < f.getParameters().size(); i++)
        localEnv.put(f.getParameters().get(i).getId().getName(), fCall.getArgs().get(i).accept(this));

      // Create local enviroment for function call
      env.push(localEnv);
      List<Object> returnedObjects = (List<Object>) f.accept(this);
      env.pop();

      if (!returnedObjects.isEmpty())
        return returnedObjects.get((int) fCall.getIndex().accept(this));

      return null;
    } catch (Exception e) {
      throw new RuntimeException("Assignable Function Call: Invalid use");
    }
  }

  public Void visitStaticFunctionCall(StaticFunctionCall fCall) {
    try {
      Map<String, Object> localEnv = new HashMap<>();
      Function f = (Function) functions.get(fCall.getId().getName());

      // Unpack parameters
      for (int i = 0; i < f.getParameters().size(); i++)
        localEnv.put(f.getParameters().get(i).getId().getName(), fCall.getArgs().get(i).accept(this));

      // Create local enviroment for function call
      env.push(localEnv);
      List<Object> returnedObjects = (List<Object>) f.accept(this);
      env.pop();

      for (int i = 0; i < returnedObjects.size(); i++)
        env.peek().put(fCall.getLvalues().get(i).toKey(this), returnedObjects.get(i));

      return null;
    } catch (Exception e) {
      throw new RuntimeException("Static Function Call: Invalid use");
    }
  }

  public Object visitCommandScope(CommandScope cmdScope) {
    for (AbstractCommand cmd : cmdScope.getCommands()) {
      Object result = cmd.accept(this);

      // The only command that returns a List is the 'Return' command
      if (result instanceof List)
        return (List<Object>) result;
    }

    return null;
  }

  public Void visitPrint(Print printCmd) {
    System.out.println(printCmd.getExpression().accept(this));
    return null;
  }

  public Object visitIf(If ifCmd) {
    try {
      boolean expr = (boolean) ifCmd.getExpression().accept(this);

      if (expr)
        return ifCmd.getScopeCommand().accept(this);

      return null;
    } catch (Exception e) {
      throw new RuntimeException("If CMD: Invalid cast");
    }
  }

  public Object visitIfElse(IfElse ifElseCmd) {
    try {
      boolean expr = (boolean) ifElseCmd.getExpression().accept(this);

      if (expr)
        return ifElseCmd.getIfScopeCommand().accept(this);
      else
        return ifElseCmd.getElseScopeCommand().accept(this);
    } catch (Exception e) {
      throw new RuntimeException("If Else CMD: Invalid cast");
    }
  }

  public Object visitNew(New newCmd) {
    try {
      int length = newCmd.getExpression() != null ? (int) newCmd.getExpression().accept(this) : 0;

      if (newCmd.getType() instanceof ArrayType) {
        ArrayType arrayType = (ArrayType) newCmd.getType();
        List<Object> array = new ArrayList<Object>();

        // TODO: Parametros do New
        if (length == 0) {
          array.add(new New(0, 0, arrayType.getType(), null).accept(this));
          return array;
        }
        else {
          for (int i = 0; i < length; i++) {
            // Recursive call to the type this array holds
            array.add(new New(0, 0, arrayType.getType(), null).accept(this));
          }

          return array;
        }
      }
      // TODO: Parametros do literal
      else if (newCmd.getType() instanceof BoolType) {
        return length == 0 ? new BoolLiteral(0, 0, false) : new ArrayList<BoolLiteral>(length);
      }
      else if (newCmd.getType() instanceof CharType) {
        return length == 0 ? new CharLiteral(0, 0, 'a') : new ArrayList<CharLiteral>(length);
      }
      else if (newCmd.getType() instanceof FloatType) {
        return length == 0 ? new FloatLiteral(0, 0, 0.0f) : new ArrayList<FloatLiteral>(length);
      }
      else if (newCmd.getType() instanceof IntType) {
        return length == 0 ? new IntLiteral(0, 0, 0) : new ArrayList<IntLiteral>(length);
      }
      else { // TypeCustom
        // TODO
        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException("Invalid use of 'New'");
    }
  }

  public Void visitIterate(Iterate iterateCmd) {
    try {
      Integer number = (Integer) iterateCmd.getExpression().accept(this);

      for (int i = 0; i < number; i++)
        iterateCmd.getCommand().accept(this);

      return null;
    } catch (Exception e) {
      throw new RuntimeException("Interate: Invalid cast");
    }
  }

  public Object visitIdentifier(Identifier id) {
    return env.peek().get(id.toKey(this));
  }

  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    return env.peek().get(arrayAccess.toKey(this));
  }

  public Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    return env.peek().get(dataIdentifierAccess.toKey(this));
  }

  public Void visitAssignment(Assignment assignment) {
    env.peek().put(assignment.getLvalue().toKey(this), assignment.getExpression().accept(this));
    return null;
  }

  public Void visitRead(Read readCmd) {
    try {
      Scanner scanner = new Scanner(System.in);
      env.peek().put(readCmd.getLvalue().toKey(this), scanner.nextInt());
      scanner.close();
      return null;
    } catch (Exception e) {
      throw new RuntimeException("Read CMD: Invalid entry");
    }
  }

  public Object visitBalancedParentheses(BalancedParenthesesExpression balanced) {
    return balanced.getExpression().accept(this);
  }

  public Void visitArrayType(ArrayType arrayType) {
    return null;
  }

  public Void visitBoolType(BoolType boolType) {
    return null;
  }

  public Void visitCharType(CharType charType) {
    return null;
  }

  public Void visitFloatType(FloatType floatType) {
    return null;
  }

  public Void visitIntType(IntType intType) {
    return null;
  }

  @Override
  public Void visitErrorType(ErrorType errorType) {
    return null;
  }

  public Void visitDataType(DataType dataType) {
    return null;
  }

  @Override
  public Object visitData(Data data) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitDeclaration(Declaration decl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitParameter(Parameter param) {
    // TODO Auto-generated method stub
    return null;
  }
}
