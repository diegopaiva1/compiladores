package lang.compiler.ast;

import java.util.List;
import java.util.ArrayList;

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
}
