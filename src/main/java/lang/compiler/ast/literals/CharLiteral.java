package lang.compiler.ast.literals;

import org.apache.commons.text.StringEscapeUtils;

import lang.compiler.visitors.AstVisitor;

public class CharLiteral extends AbstractLiteral<Character> {
  public CharLiteral(int line, int column, Character value) {
    super(line, column, value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitCharLiteral(this);
  }

  @Override
  public String toString() {
    return "\'" +  StringEscapeUtils.escapeJava(getValue().toString()) + "\'";
  }
}
