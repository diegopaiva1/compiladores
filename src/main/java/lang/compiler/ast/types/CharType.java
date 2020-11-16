package lang.compiler.ast.types;

import lang.compiler.visitors.AstVisitor;

public class CharType extends AbstractType {
  public CharType(int line, int column) {
    super(line, column);
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof CharType;
  }

  @Override
  public String toString() {
    return "Char";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitCharType(this);
  }
}
