package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class Char extends AbstractLiteral<Character> {
  public Char(){
    super();
  }

  public Char(Character value) {
    super(value);
  }

  @Override
  public Character accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitChar(this);
  }
}
