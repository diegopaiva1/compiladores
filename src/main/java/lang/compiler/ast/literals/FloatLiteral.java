package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class FloatLiteral extends AbstractLiteral<java.lang.Float> {
  public FloatLiteral() {
    super();
  }

  public FloatLiteral(java.lang.Float value) {
    super(value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitFloatLiteral(this);
  }
}
