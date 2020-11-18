package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class FloatLiteral extends AbstractLiteral<Float> {
  public FloatLiteral(int line, int column, Float value) {
    super(line, column, value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitFloatLiteral(this);
  }

  @Override
  public String toString() {
    return getValue().toString();
  }
}
