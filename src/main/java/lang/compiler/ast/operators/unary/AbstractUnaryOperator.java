package lang.compiler.ast.operators.unary;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.operators.AbstractOperator;

public abstract class AbstractUnaryOperator extends AbstractOperator {
  private AbstractExpression expr;

  public AbstractUnaryOperator(int line, int column, AbstractExpression expr) {
    super(line, column);
    this.expr = expr;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public String toString() {
    return getSymbol().toString() + expr.toString();
  }
}
