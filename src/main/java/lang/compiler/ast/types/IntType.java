package lang.compiler.ast.types;

import lang.compiler.visitors.AstVisitor;

public class IntType extends AbstractType {
  public IntType(int line, int column) {
    super(line, column);
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof IntType;
  }

  @Override
  public String toString() {
    return "Int";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitIntType(this);
  }
}
