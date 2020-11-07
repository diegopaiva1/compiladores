package lang.compiler.ast.operators.unary;

import lang.compiler.ast.AbstractExpression;

public class Negate extends AbstractUnaryOperator {
  public Negate(AbstractExpression expr) {
    super(expr);
  }

  @Override
  public String getSymbol() {
    return "-";
  }
}
