package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class TypeArray extends AbstractType {
  private AbstractType type;

  public TypeArray(AbstractType type) {
    this.type = type;
  }

  public AbstractType getType() {
    return type;
  }

  public void setType(AbstractType type) {
    this.type = type;
  }

  @Override
  public boolean match(AbstractType type) {
    if (type instanceof TypeArray) {
      TypeArray array = (TypeArray) type;
      return this.type.match(array.getType());
    }

    return false;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitTypeArray(this);
  }

  @Override
  public String toString() {
    return "Array";
  }
}
