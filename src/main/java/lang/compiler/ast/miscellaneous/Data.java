package lang.compiler.ast.miscellaneous;

import java.util.List;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.types.CustomType;

public class Data extends AbstractExpression {
  private CustomType type;

  public Data(CustomType type) {
    this.type = type;
  }

  public CustomType getType() {
    return type;
  }

  public void setType(CustomType type) {
    this.type = type;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitData(this);
  }
}
