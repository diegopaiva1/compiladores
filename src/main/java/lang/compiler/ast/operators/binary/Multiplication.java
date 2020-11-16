package lang.compiler.ast.operators.binary;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class Multiplication extends AbstractBinaryOperator {
  public Multiplication(int line, int column, AbstractExpression left, AbstractExpression right) {
    super(line, column, left, right);
  }

  @Override
  public String getSymbol() {
    return "*";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitMultiplication(this);
  }
}
