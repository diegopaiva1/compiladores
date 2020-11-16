package lang.compiler.ast.types;

import lang.compiler.visitors.AstVisitor;

public class ArrayType extends AbstractType {
  private AbstractType type;

  public ArrayType(int line, int column, AbstractType type) {
    super(line, column);
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
  public String toString() {
    return type.toString() + "[]";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitArrayType(this);
  }
}
