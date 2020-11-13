package lang.compiler.ast.literals;

import javax.lang.model.type.NullType;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class NullLiteral extends AbstractLiteral<NullType> {
  public NullLiteral(NullType value) {
    super(value);
  }

  @Override
  public NullType accept(AbstractExpressionVisitor v) {
    return v.visitNullLiteral(this);
  }
}
