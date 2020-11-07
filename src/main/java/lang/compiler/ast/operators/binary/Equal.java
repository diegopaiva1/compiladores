package lang.compiler.ast.operators.binary;

import lang.compiler.ast.AbstractExpression;

public class Equal extends AbstractBinaryOperator {
  public Equal(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "==";
  }
}
