package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class IntLiteral extends AbstractLiteral<Integer> {
  public IntLiteral() {
    super();
  }

  public IntLiteral(Integer value) {
    super(value);
  }

  @Override
  public Integer accept(AbstractExpressionVisitor v) {
    return v.visitIntLiteral(this);
  }
}
