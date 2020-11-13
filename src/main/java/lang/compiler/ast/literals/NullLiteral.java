package lang.compiler.ast.literals;

import javax.lang.model.type.NullType;

import lang.compiler.visitors.AstVisitor;

public class NullLiteral extends AbstractLiteral<NullType> {
  public NullLiteral(NullType value) {
    super(value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitNullLiteral(this);
  }
}
