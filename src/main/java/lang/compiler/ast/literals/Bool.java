package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class Bool extends AbstractLiteral<Boolean> {
  public Bool() {
    super();
  }

  public Bool(Boolean value) {
    super(value);
  }

  @Override
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitBool(this);
  }
}
