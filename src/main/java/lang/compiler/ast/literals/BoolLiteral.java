package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class BoolLiteral extends AbstractLiteral<Boolean> {
  public BoolLiteral(int line, int column, Boolean value) {
    super(line, column, value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitBoolLiteral(this) ;
  }
}
