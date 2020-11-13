package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class BoolType extends AbstractType {
  public BoolType() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof BoolType;
  }

  @Override
  public Void accept(AbstractExpressionVisitor v) {
    return v.visitBoolType(this);
  }

  @Override
  public String toString() {
    return "Bool";
  }
}
