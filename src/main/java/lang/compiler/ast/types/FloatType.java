package lang.compiler.ast.types;

import lang.compiler.visitors.AstVisitor;

public class FloatType extends AbstractType {
  public FloatType(int line, int column) {
    super(line, column);
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof FloatType;
  }

  @Override
  public String toString() {
    return "Float";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitFloatType(this);
  }
}
