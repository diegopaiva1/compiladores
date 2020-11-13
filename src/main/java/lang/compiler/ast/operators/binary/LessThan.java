package lang.compiler.ast.operators.binary;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class LessThan extends AbstractBinaryOperator {
  public LessThan(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "<";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitLessThan(this);
  }
}
