package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class IntLiteral extends AbstractLiteral<Integer> {
  public IntLiteral(int line, int column, Integer value) {
    super(line, column, value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitIntLiteral(this);
  }

  @Override
  public String toString() {
    return getValue().toString();
  }
}
