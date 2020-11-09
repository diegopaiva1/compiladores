package lang.compiler.ast;

import java.util.List;

import lang.compiler.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.types.BasicType;

public class Data extends AbstractExpression {
  private BasicType type;
  private List<Declaration> decls;

  public Data(BasicType type, List<Declaration> decls) {
    this.type = type;
    this.decls = decls;
  }

  public BasicType getType() {
    return type;
  }

  public void setType(BasicType type) {
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
