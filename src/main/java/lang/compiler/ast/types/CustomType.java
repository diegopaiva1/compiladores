package lang.compiler.ast.types;

import java.util.*;

import lang.compiler.ast.lvalues.Identifier;
import lang.compiler.visitors.AstVisitor;

public class CustomType extends AbstractType {
  private String name;
  private Map<String, AbstractType> varsTypes;

  public CustomType(int line, int column, String name) {
    super(line, column);
    this.name = name;
    this.varsTypes = new HashMap<>();
  }

  public void addVarType (String id, AbstractType type) {
    varsTypes.put(id, type);
  }

  public AbstractType getVarType (String id) {
    return varsTypes.get(id);
  }

  public Boolean hasVar (String id) {
    return varsTypes.containsKey(id);
  }

  @Override
  public boolean match(AbstractType type) {
    if (type instanceof CustomType) {
      CustomType typeCustom = (CustomType) type;
      return this.toString() == typeCustom.toString();
    }

    return false;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitCustomType(this);
  }
}
