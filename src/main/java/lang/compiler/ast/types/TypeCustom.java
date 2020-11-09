package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class TypeCustom extends AbstractType {
  private String name;

  public TypeCustom(String name) {
    this.name = name;
  }

  @Override
  public boolean match(AbstractType type) {
    if (type instanceof TypeCustom) {
      TypeCustom typeCustom = (TypeCustom) type;
      return this.toString() == typeCustom.toString();
    }

    return false;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitTypeCustom(this);
  }

  @Override
  public String toString() {
    return name;
  }
}
