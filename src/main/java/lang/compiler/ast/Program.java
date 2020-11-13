/**
 * Class containing the AST representation of a successfuly parsed program.
 */

package lang.compiler.ast;

import java.util.List;
import java.util.ArrayList;

import lang.compiler.ast.miscellaneous.Function;
import lang.compiler.visitors.AbstractExpressionVisitor;

public class Program {
  private List<AbstractExpression> exprs;

  public Program() {
    exprs = new ArrayList<>();
  }

  public void addExpression(AbstractExpression expr) {
    exprs.add(expr);
  }

  public List<AbstractExpression> getExpressions() {
    return exprs;
  }

  public void interpret() {
    AbstractExpressionVisitor ev = new AbstractExpressionVisitor();

    // Store functions before evaluation
    for (AbstractExpression expr : exprs) {
      if (expr instanceof Function)
        ev.addFunction((Function) expr);
    }

    if (!ev.hasFunction("main"))
      throw new RuntimeException("function main does not exist");

    for (AbstractExpression expr : exprs) {
      if (expr instanceof Function) {
        Function f = (Function) expr;

        if (f.getId().getName().equals("main"))
          expr.accept(ev);
      }
    }
  }
}
