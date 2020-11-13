package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class CharLiteral extends AbstractLiteral<Character> {
  public CharLiteral(){
    super();
  }

  public CharLiteral(Character value) {
    super(value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitCharLiteral(this);
  }
}
