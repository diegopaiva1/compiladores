package lang.compiler.ast;

import lang.compiler.ast.types.AbstractType;

public class Symbol {
  private AbstractType type;

  public Symbol() {
  }

  public AbstractType getType() {
    return type;
  }

  public void setType(AbstractType type) {
    this.type = type;
  }
}
