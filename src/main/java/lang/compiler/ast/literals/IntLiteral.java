package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class IntLiteral extends AbstractLiteral<Integer> {
  public IntLiteral() {
    super();
  }

  public IntLiteral(Integer value) {
    super(value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitIntLiteral(this);
  }
}
