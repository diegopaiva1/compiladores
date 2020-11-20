package lang.compiler.ast.miscellaneous;

import lang.compiler.visitors.AstVisitor;

import java.util.Map;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.types.AbstractType;
import lang.compiler.ast.types.DataType;

public class Data extends AbstractExpression {
  private DataType type;
  private Map<String, AbstractType> properties;

  public Data(int line, int column, DataType type, Map<String, AbstractType> properties) {
    super(line, column);
    this.type = type;
    this.properties = properties;
  }

  public DataType getType() {
    return type;
  }

  public void setType(DataType type) {
    this.type = type;
  }

  public Map<String, AbstractType> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, AbstractType> properties) {
    this.properties = properties;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitData(this);
  }

  @Override
  public String toString() {
    return "data " + type.toString();
  }
}
