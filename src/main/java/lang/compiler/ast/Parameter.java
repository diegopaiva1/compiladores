package lang.compiler.ast;

import lang.compiler.ast.lvalues.Identifier;

public class Parameter extends AbstractExpression {
  private Identifier id;
  private String type;

  public Parameter(Identifier id, String type) {
    this.id = id;
    this.type = type;
  }

  public Identifier getId() {
    return id;
  }

  public void setId(Identifier id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
