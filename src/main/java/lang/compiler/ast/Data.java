package lang.compiler.ast;

import java.util.List;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.types.TypeCustom;

public class Data extends AbstractExpression {
  private TypeCustom type;
  private List<Declaration> decls;

  public Data(TypeCustom type, List<Declaration> decls) {
    this.type = type;
    this.decls = decls;
  }

  public TypeCustom getType() {
    return type;
  }

  public void setType(TypeCustom type) {
    this.type = type;
  }

  public List<Declaration> getDeclarations() {
    return decls;
  }

  public void setDeclarations(List<Declaration> decls) {
    this.decls = decls;
  }

  @Override
  public <T> Object accept(AbstractExpressionEvaluatorVisitor v) {
    // TODO Auto-generated method stub
    return null;
  }
}
