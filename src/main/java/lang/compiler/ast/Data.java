package lang.compiler.ast;

import java.util.List;

public class Data extends AbstractExpression {
  private String typeName;
  private List<Declaration> declarations;

  public Data(String typeName, List<Declaration> declarations) {
    this.typeName = typeName;
    this.declarations = declarations;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public List<Declaration> getDeclarations() {
    return declarations;
  }

  public void setDeclarations(List<Declaration> declarations) {
    this.declarations = declarations;
  }
}
