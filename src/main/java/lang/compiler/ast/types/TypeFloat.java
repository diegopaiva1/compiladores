package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class TypeFloat extends AbstractType {

  public TypeFloat() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof TypeFloat;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitTypeFloat(this);
  }

  @Override
  public String toString() {
    return "Float";
  }
}
