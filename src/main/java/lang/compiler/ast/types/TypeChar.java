package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class TypeChar extends AbstractType {
  public TypeChar() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof TypeChar;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitTypeChar(this);
  }

  @Override
  public String toString() {
    return "Char";
  }
}
