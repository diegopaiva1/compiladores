package lang.compiler.ast.operators.unary;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.visitors.AstVisitor;

public class Negate extends AbstractUnaryOperator {
  public Negate(int line, int column, AbstractExpression expr) {
    super(line, column,expr);
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
