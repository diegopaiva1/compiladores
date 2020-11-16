package lang.compiler.ast.literals;

import javax.lang.model.type.NullType;

import lang.compiler.visitors.AstVisitor;

public class NullLiteral extends AbstractLiteral<NullType> {
  public NullLiteral(int line, int column, NullType value) {
    super(line, column, value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitNullLiteral(this);
  }
}
