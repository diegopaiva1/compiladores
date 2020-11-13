package lang.compiler.ast.miscellaneous;

import java.util.List;

import lang.compiler.visitors.AbstractExpressionVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.types.CustomType;

public class Data extends AbstractExpression {
  private CustomType type;
  private List<Declaration> decls;

  public Data(CustomType type, List<Declaration> decls) {
    this.type = type;
    this.decls = decls;
  }

  public CustomType getType() {
    return type;
  }

  public void setType(CustomType type) {
    this.type = type;
  }

  public List<Declaration> getDeclarations() {
    return decls;
  }

  public void setDeclarations(List<Declaration> decls) {
    this.decls = decls;
  }

  @Override
  public <T> Object accept(AbstractExpressionVisitor v) {
    // TODO Auto-generated method stub
    return null;
  }
}
