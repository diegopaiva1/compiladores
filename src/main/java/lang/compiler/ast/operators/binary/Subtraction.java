package lang.compiler.ast.operators.binary;

import lang.compiler.ast.AbstractExpression;

public class Subtraction extends AbstractBinaryOperator {
  public Subtraction(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "-";
  }
}
