package lang.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.Data;
import lang.compiler.ast.Declaration;
import lang.compiler.ast.Function;
import lang.compiler.ast.Parameter;
import lang.compiler.ast.commands.AbstractCommand;

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

        for (AbstractCommand cmd : f.getCommands())
          evaluations.add("Command: " + cmd.getName());
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
