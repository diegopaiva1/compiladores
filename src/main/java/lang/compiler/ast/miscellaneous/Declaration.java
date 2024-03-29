package lang.compiler.ast.miscellaneous;

import lang.compiler.ast.types.AbstractType;
import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.lvalues.Identifier;

public class Declaration extends AbstractExpression {
  private Identifier id;
  private AbstractType type;

  public Declaration(int line, int column, Identifier id, AbstractType type) {
    super(line, column);
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
  public Object accept(AstVisitor v) {
    return v.visitDeclaration(this);
  }

  @Override
  public String toString() {
    return id.toString() + " :: " + type.toString();
  }
}
