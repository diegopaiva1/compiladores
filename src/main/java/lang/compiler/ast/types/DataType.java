package lang.compiler.ast.types;

import lang.compiler.visitors.AstVisitor;

public class DataType extends AbstractType {
  private String name;

  public DataType(int line, int column, String name) {
    super(line, column);
    this.name = name;
  }

  @Override
  public boolean match(AbstractType type) {
    if (type instanceof DataType)
      return this.toString().equals(((DataType) type).toString());

    return false;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitDataType(this);
  }
}
