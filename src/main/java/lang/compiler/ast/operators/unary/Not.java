package lang.compiler.ast.operators.unary;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
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
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitNot(this);
  }
}
