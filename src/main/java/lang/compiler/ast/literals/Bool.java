package lang.compiler.ast.literals;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

public class Bool extends AbstractLiteral<Boolean> {
  public Bool(Boolean value) {
    super(value);
  }

  @Override
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitBool(this);
  }
}
