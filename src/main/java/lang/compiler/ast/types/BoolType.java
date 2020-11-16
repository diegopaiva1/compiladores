package lang.compiler.ast.types;

import lang.compiler.visitors.AstVisitor;

public class BoolType extends AbstractType {
  public BoolType(int line, int column) {
    super(line, column);
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof BoolType;
  }

  @Override
  public String toString() {
    return "Bool";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitBoolType(this);
  }
}
