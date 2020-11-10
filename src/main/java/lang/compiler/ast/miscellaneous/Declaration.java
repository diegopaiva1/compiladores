package lang.compiler.ast.miscellaneous;

import lang.compiler.ast.types.AbstractType;
import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.lvalues.Identifier;

public class Declaration extends AbstractExpression {
  private Identifier id;
  private AbstractType type;

  public Declaration(Identifier id, AbstractType type) {
    this.id = id;
    this.type = type;
  }

  public Identifier getId() {
    return id;
  }

  public void setId(Identifier id) {
    this.id = id;
  }

  public AbstractType getType() {
    return type;
  }

  public void setType(AbstractType type) {
    this.type = type;
  }

  @Override
  public <T> Object accept(AbstractExpressionEvaluatorVisitor v) {
    // TODO Auto-generated method stub
    return null;
  }
}
