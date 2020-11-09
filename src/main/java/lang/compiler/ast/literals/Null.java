package lang.compiler.ast.literals;

import javax.lang.model.type.NullType;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

public class Null extends AbstractLiteral<NullType> {
  public Null(NullType value) {
    super(value);
  }

  @Override
  public NullType accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitNull(this);
  }
}
