package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class ArrayType extends AbstractType {
  private AbstractType type;

  public ArrayType(AbstractType type) {
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
    if (type instanceof ArrayType) {
      ArrayType array = (ArrayType) type;
      return this.type.match(array.getType());
    }

    return false;
  }

  @Override
  public Void accept(AbstractExpressionVisitor v) {
    return v.visitArrayType(this);
  }

  @Override
  public String toString() {
    return "Array of " + type.toString();
  }
}
