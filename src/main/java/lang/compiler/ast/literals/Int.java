package lang.compiler.ast.literals;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

public class Int extends AbstractLiteral<Integer> {
  public Int(Integer value) {
    super(value);
  }

  @Override
  public Integer accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitInt(this);
  }
}
