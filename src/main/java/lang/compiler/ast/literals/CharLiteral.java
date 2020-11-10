package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class CharLiteral extends AbstractLiteral<Character> {
  public CharLiteral(){
    super();
  }

  public CharLiteral(Character value) {
    super(value);
  }

  @Override
  public Character accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitCharLiteral(this);
  }
}
