package lang.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;;

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
        evaluations.add(f.getId());

        for (Parameter p : f.getParameters())
          evaluations.add("Param: " + p.getId() + " :: " + p.getType());

        for (AbstractCommand cmd : f.getCommands()) {
          evaluations.add("Command: " + cmd.getName());

          if (cmd instanceof If) {
            If i = (If) cmd;
            evaluations.add("If condition = " + i.getExpression());
          }
        }
      } else if (expr instanceof Data) {
        Data d = (Data) expr;
        evaluations.add(d.getTypeName());

        for (Declaration decl : d.getDeclarations())
          evaluations.add(decl.getId() + " :: " + decl.getType());
      }
    }

    return evaluations;
  }
}
