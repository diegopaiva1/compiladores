package lang.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class Scope {
  private Scope enclosing;
  private Map<String, Symbol> symbolMap;

  public Scope(Scope enclosing) {
    this.symbolMap = new HashMap<>();
    this.enclosing = enclosing;
  }

  public void put(String key) {
    symbolMap.put(key, new Symbol());
  }

  public Symbol lookup(String key) {
    Symbol symbol = symbolMap.get(key);

    if (symbol != null)
      return symbol;
    else if (enclosing != null)
      return enclosing.lookup(key);
    else
      return null;
  }

  @Override
  public String toString() {
    return symbolMap.keySet().toString();
  }
}
