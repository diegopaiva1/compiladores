package lang.compiler.ast.operators.unary;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.operators.AbstractOperator;

public abstract class AbstractUnaryOperator extends AbstractOperator {
  private AbstractExpression expr;

  public AbstractUnaryOperator(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }
}
