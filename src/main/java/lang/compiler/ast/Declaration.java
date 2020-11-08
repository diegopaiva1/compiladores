package lang.compiler.ast;

import lang.compiler.ast.types.AbstractType;

public class Declaration extends AbstractExpression {
  private String id;
  private AbstractType type;

  public Declaration(String id, AbstractType type) {
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public AbstractType getType() {
    return type;
  }

  public void setType(AbstractType type) {
    this.type = type;
  }
}
