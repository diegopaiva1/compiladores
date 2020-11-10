package lang.compiler.ast.literals;

import javax.lang.model.type.NullType;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class NullLiteral extends AbstractLiteral<NullType> {
  public NullLiteral(NullType value) {
    super(value);
  }

  @Override
  public NullType accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitNullLiteral(this);
  }
}
