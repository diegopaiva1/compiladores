package lang.compiler.ast.operators.unary;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.visitors.AstVisitor;

public class Negate extends AbstractUnaryOperator {
  public Negate(AbstractExpression expr) {
    super(expr);
  }

  @Override
  public String getSymbol() {
    return "-";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitNegate(this);
  }
}
