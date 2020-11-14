package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class FloatLiteral extends AbstractLiteral<Float> {
  public FloatLiteral() {
    super();
  }

  public FloatLiteral(Float value) {
    super(value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitFloatLiteral(this);
  }
}
