package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class CharLiteral extends AbstractLiteral<Character> {
  public CharLiteral(){
    super();
  }

  public CharLiteral(Character value) {
    super(value);
  }

  @Override
  public Character accept(AbstractExpressionVisitor v) {
    return v.visitCharLiteral(this);
  }
}
