package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class TypeBool extends AbstractType {
  public TypeBool() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof TypeBool;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitTypeBool(this);
  }

  @Override
  public String toString() {
    return "Bool";
  }
}
