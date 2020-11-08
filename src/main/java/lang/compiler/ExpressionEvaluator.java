package lang.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.types.AbstractType;

public class ExpressionEvaluator {
  private List<AbstractExpression> exprs;
  private Map<String, Object> symbols;

  public ExpressionEvaluator(List<AbstractExpression> exprs) {
    this.exprs = exprs;
    this.symbols = new HashMap<>();
  }

  public List<String> getEvaluationResults() {
    List<String> evaluations = new ArrayList<>();

    for (AbstractExpression expr : exprs) {
      if (expr instanceof Function) {
        Function f = (Function) expr;
        evaluations.add(f.getId().getName());

        for (Parameter p : f.getParameters())
          evaluations.add("Param: " + p.getId().getName() + " :: " + p.getType());

        for (AbstractType type : f.getReturnTypes())
          evaluations.add("Return: " + type.getName());

        for (AbstractCommand cmd : f.getCommands()) {
          evaluations.add("Command: " + cmd.getName());

          if (cmd instanceof If) {
            If i = (If) cmd;
            evaluations.add("If condition = " + i.getExpression() + " " + i.getCommand());

            if (i.getCommand() instanceof CmdScope) {
              CmdScope cmdScope = (CmdScope) i.getCommand();
              for (AbstractCommand c : cmdScope.getCmds()) {
                evaluations.add("Command inside CmdScope: " + c.getName());
              }
            }
          }

          if (cmd instanceof Print) {
            Print cmdPrint = (Print) cmd;
            if (cmdPrint.getExpression() instanceof Identifier) {
              Identifier identifier = (Identifier) cmdPrint.getExpression();
              evaluations.add(identifier.getLabel() + " > name: " + identifier.getName());
            }
            if (cmdPrint.getExpression() instanceof ArrayAccess) {
              ArrayAccess arrayAccess = (ArrayAccess) cmdPrint.getExpression();
              evaluations.add(arrayAccess.getLabel() + " > lvalue: " + arrayAccess.getLvalue() + " expr: " + arrayAccess.getExpr());
            }
            if (cmdPrint.getExpression() instanceof DataIdentifierAccess) {
              DataIdentifierAccess dataIdentifierAccess = (DataIdentifierAccess) cmdPrint.getExpression();
              evaluations.add(dataIdentifierAccess.getLabel() + " > lvalue: " + dataIdentifierAccess.getLvalue() + " id: " + dataIdentifierAccess.getId());
            }
          }
        }
      } else if (expr instanceof Data) {
        Data d = (Data) expr;
        evaluations.add(d.getTypeName());

        for (Declaration decl : d.getDeclarations())
          evaluations.add(decl.getId().getName() + " :: " + decl.getType());
      }
    }

    return evaluations;
  }
}
