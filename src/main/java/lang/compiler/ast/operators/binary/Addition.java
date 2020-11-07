package lang.compiler.ast.operators.binary;

import lang.compiler.ast.AbstractExpression;

public class Addition extends AbstractBinaryOperator {
  public Addition(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "+";
  }
}
