package lang.compiler.ast.operators.unary;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class Not extends AbstractUnaryOperator {
  public Not(AbstractExpression expr) {
    super(expr);
  }

  @Override
  public String getSymbol() {
    return "!";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitNot(this);
  }
}
