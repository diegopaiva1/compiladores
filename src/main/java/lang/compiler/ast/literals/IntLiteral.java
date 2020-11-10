package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class IntLiteral extends AbstractLiteral<Integer> {
  public IntLiteral() {
    super();
  }

  public IntLiteral(Integer value) {
    super(value);
  }

  @Override
  public Integer accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitIntLiteral(this);
  }
}
