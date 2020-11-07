package lang.compiler.ast.operators.binary;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractBinaryOperator extends AbstractExpression {
  private AbstractExpression left;
  private AbstractExpression right;

  public AbstractBinaryOperator(AbstractExpression left, AbstractExpression right) {
    this.left = left;
    this.right = right;
  }

  public AbstractExpression getLeft() {
    return left;
  }

  public void setLeft(AbstractExpression left) {
    this.left = left;
  }

  public AbstractExpression getRight() {
    return right;
  }

  public void setRight(AbstractExpression right) {
    this.right = right;
  }

  public abstract String getSymbol();
}
