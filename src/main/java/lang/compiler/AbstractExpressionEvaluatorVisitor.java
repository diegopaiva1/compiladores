package lang.compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.lang.model.type.NullType;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;

public class AbstractExpressionEvaluatorVisitor {
  private Map<String, Object> symbols;

  public AbstractExpressionEvaluatorVisitor() {
    this.symbols = new HashMap<>();
  }

  public Boolean visitBool(Bool bool) {
    return bool.getValue();
  }

  public Character visitChar(Char c) {
    return c.getValue();
  }

  public java.lang.Float visitFloat(lang.compiler.ast.literals.Float f) {
    return f.getValue();
  }

  public Integer visitInt(Int i) {
    return i.getValue();
  }

  public NullType visitNull(Null n) {
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
    }
    else if (leftExpr instanceof Number && rightExpr instanceof Character) {
      Number leftNumber = (Number) leftExpr;
      Character rightChar = (Character) rightExpr;
      return leftNumber.floatValue() < rightChar.charValue();
    }
    else if (leftExpr instanceof Character && rightExpr instanceof Character) {
      Character leftChar = (Character) leftExpr;
      Character rightChar = (Character) rightExpr;
      return leftChar.charValue() < rightChar.charValue();
    }
    else if (leftExpr instanceof Character && rightExpr instanceof Number) {
      Character leftChar = (Character) leftExpr;
      Number rightNumber = (Number) rightExpr;
      return leftChar.charValue() < rightNumber.floatValue();
    }
    else {
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
      Boolean booleanExpr = (Boolean) not.getExpression().accept(this);
      return !booleanExpr;
    } catch (Exception e) {
      throw new RuntimeException("Not OP: Invalid cast");
    }
  }

  public Number visitNegate(Negate neg) {
    try {
      Number number = (Number) neg.getExpression().accept(this);
      return -(number.floatValue());
    } catch (Exception e) {
      throw new RuntimeException("Negate OP: Invalid cast");
    }
  }

  public Boolean visitFunction(Function f) {
    for (AbstractCommand cmd : f.getCommands())
      cmd.accept(this);

    return true;
  }

  public Boolean visitCommandScope(CommandScope cmdScope) {
    for (AbstractCommand cmd : cmdScope.getCommmands())
      cmd.accept(this);

    return true;
  }

  public Boolean visitPrint(Print p) {
    System.out.println(p.getExpression().accept(this));
    return true;
  }

  public Boolean visitIf(If ifCmd) {
    try {
      boolean expr = (boolean) ifCmd.getExpression().accept(this);

      if (expr)
        ifCmd.getCommand().accept(this);

      return true;
    } catch (Exception e) {
      throw new RuntimeException("If CMD: Invalid cast");
    }
  }

  public Boolean visitIfElse(IfElse ifElseCmd) {
    try {
      boolean expr = (boolean) ifElseCmd.getExpression().accept(this);

      if (expr)
        ifElseCmd.getIfCommand().accept(this);
      else
        ifElseCmd.getElseCommand().accept(this);

      return true;
    } catch (Exception e) {
      throw new RuntimeException("If Else CMD: Invalid cast");
    }
  }

  public Boolean visitIterate(Iterate it) {
    try {
      Integer number = (Integer) it.getExpression().accept(this);

      for (int i = 0; i < number; i++)
        it.getCommand().accept(this);

      return true;
    } catch (Exception e) {
      throw new RuntimeException("Interate: Invalid cast");
    }
  }

  public Object visitIdentifier(Identifier id) {
    return symbols.get(id.toString());
  }

  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    return symbols.get(arrayAccess.toString());
  }

  public Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    return symbols.get(dataIdentifierAccess.toString());
  }

  public Boolean visitAssignment(Assignment assignment) {
    symbols.put(assignment.getLvalue().toString(), assignment.getExpression().accept(this));
    return true;
  }

  public Boolean visitRead(Read read) {
    try {
      Scanner scanner = new Scanner(System.in);
      symbols.put(read.getLvalue().toString(), scanner.next());
      return true;
    }
    catch (Exception e) {
      throw new RuntimeException("Read CMD: Invalid entry");
    }
  }

  public Object visitBalancedParentheses(BalancedParenthesesExpression bpe) {
    return bpe.getExpression().accept(this);
  }

  // public List<String> getEvaluationResults() {
  //   List<String> evaluations = new ArrayList<>();

  //   for (AbstractExpression expr : exprs) {
  //     if (expr instanceof Function) {
  //       Function f = (Function) expr;
  //       evaluations.add(f.getId().getName());

  //       for (Parameter p : f.getParameters())
  //         evaluations.add("Param: " + p.getId().getName() + " :: " + p.getType().getName());

  //       for (AbstractType type : f.getReturnTypes())
  //         evaluations.add("Return: " + type.getName());

  //       for (AbstractCommand cmd : f.getCommands()) {
  //         evaluations.add("Command: " + cmd.getName());

  //         if (cmd instanceof StaticFunctionCall) {
  //           StaticFunctionCall st = (StaticFunctionCall) cmd;

  //           for (AbstractExpression arg : st.getArgs()) {
  //             System.out.println(arg);
  //           }
  //         }

  //         if (cmd instanceof If) {
  //           If i = (If) cmd;
  //           evaluations.add("If condition = " + i.getExpression() + " " + i.getCommand());

  //           if (i.getCommand() instanceof CmdScope) {
  //             CmdScope cmdScope = (CmdScope) i.getCommand();
  //             for (AbstractCommand c : cmdScope.getCmds()) {
  //               evaluations.add("Command inside CmdScope: " + c.getName());
  //             }
  //           }
  //         }

  //         if (cmd instanceof Print) {
  //           Print cmdPrint = (Print) cmd;
  //           if (cmdPrint.getExpression() instanceof Identifier) {
  //             Identifier identifier = (Identifier) cmdPrint.getExpression();
  //             evaluations.add(identifier.getLabel() + " > name: " + identifier.getName());
  //           }
  //           if (cmdPrint.getExpression() instanceof ArrayAccess) {
  //             ArrayAccess arrayAccess = (ArrayAccess) cmdPrint.getExpression();
  //             evaluations.add(arrayAccess.getLabel() + " > lvalue: " + arrayAccess.getLvalue() + " expr: " + arrayAccess.getExpr());
  //           }
  //           if (cmdPrint.getExpression() instanceof DataIdentifierAccess) {
  //             DataIdentifierAccess dataIdentifierAccess = (DataIdentifierAccess) cmdPrint.getExpression();
  //             evaluations.add(dataIdentifierAccess.getLabel() + " > lvalue: " + dataIdentifierAccess.getLvalue() + " id: " + dataIdentifierAccess.getId());
  //           }
  //         }
  //       }
  //     } else if (expr instanceof Data) {
  //       Data d = (Data) expr;
  //       evaluations.add(d.getTypeName());

  //       for (Declaration decl : d.getDeclarations())
  //         evaluations.add(decl.getId().getName() + " :: " + decl.getType());
  //     }
  //   }

  //   return evaluations;
  // }
}
