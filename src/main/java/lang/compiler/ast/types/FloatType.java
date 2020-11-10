package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class FloatType extends AbstractType {
  public FloatType() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof FloatType;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitFloatType(this);
  }

  @Override
  public String toString() {
    return "Float";
  }
}
