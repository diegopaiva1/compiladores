package lang.compiler.ast;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import lang.compiler.ast.miscellaneous.Function;
import lang.compiler.ast.types.AbstractType;

public class Scope {
  private Function owningFunction;
  private Scope father;
  private Map<String, AbstractType> symbolTable;

  public Scope(Function owningFunction, Scope father) {
    this.owningFunction = owningFunction;
    this.father = father;
    this.symbolTable = new HashMap<>();
  }

  public Function getOwningFunction() {
    return owningFunction;
  }

  public void setOwningFunction(Function owningFunction) {
    this.owningFunction = owningFunction;
  }

  public Scope getFather() {
    return father;
  }

  public void setFather(Scope father) {
    this.father = father;
  }

  public Map<String, AbstractType> getSymbolTable() {
    return symbolTable;
  }

  public void setSymbolTable(Map<String, AbstractType> symbolTable) {
    this.symbolTable = symbolTable;
  }

  public void addSymbol(String key, AbstractType type) {
    symbolTable.put(key, type);
  }

  public AbstractType search(String key) {
    AbstractType type = symbolTable.get(key);

    if (type != null)
      return type;
    else if (father != null)
      return father.search(key);
    else
      return null;
  }

  @Override
  public String toString() {
    return symbolTable.toString();
  }
}
