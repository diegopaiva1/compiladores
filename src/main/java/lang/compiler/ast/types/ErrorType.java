package lang.compiler.ast.types;

import lang.compiler.visitors.AstVisitor;

public class ErrorType extends AbstractType {
  public ErrorType(int line, int column) {
    super(line, column);
  }

  @Override
  public boolean match(AbstractType type) {
    return true;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitErrorType(this);
  }

  @Override
  public String toString() {
    return "Error";
  }
}
