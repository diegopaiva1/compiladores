package lang.compiler.ast.operators.binary;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.operators.AbstractOperator;

public abstract class AbstractBinaryOperator extends AbstractOperator {
  private AbstractExpression left;
  private AbstractExpression right;

  public AbstractBinaryOperator(int line, int column, AbstractExpression left, AbstractExpression right) {
    super(line, column);
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

  @Override
  public String toString() {
    return left.toString() + " " + getSymbol() + " " + right.toString();
  }
}
