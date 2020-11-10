package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class CharType extends AbstractType {
  public CharType() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof CharType;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitCharType(this);
  }

  @Override
  public String toString() {
    return "Char";
  }
}
