package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class CustomType extends AbstractType {
  private String name;

  public CustomType(String name) {
    this.name = name;
  }

  @Override
  public boolean match(AbstractType type) {
    if (type instanceof CustomType) {
      CustomType typeCustom = (CustomType) type;
      return this.toString() == typeCustom.toString();
    }

    return false;
  }

  @Override
  public Void accept(AbstractExpressionVisitor v) {
    return v.visitCustomType(this);
  }

  @Override
  public String toString() {
    return name;
  }
}
