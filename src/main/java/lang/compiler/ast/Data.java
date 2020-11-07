package lang.compiler.ast;

import java.util.List;

public class Data extends AbstractExpression {
  private String typeName;
  private List<Declaration> decls;

  public Data(String typeName, List<Declaration> decls) {
    this.typeName = typeName;
    this.decls = decls;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public List<Declaration> getDeclarations() {
    return decls;
  }

  public void setDeclarations(List<Declaration> decls) {
    this.decls = decls;
  }
}
