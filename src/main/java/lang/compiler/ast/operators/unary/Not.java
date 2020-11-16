package lang.compiler.ast.operators.unary;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class Not extends AbstractUnaryOperator {
  public Not(int line, int column, AbstractExpression expr) {
    super(line, column, expr);
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
