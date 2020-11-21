package lang.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
  private Map<String, Symbol> map;

  public SymbolTable() {
    map = new HashMap<>();
  }

  public Symbol lookup(String key) {
    return map.get(key);
  }

  public void put(String key, Symbol symbol) {
    map.put(key, symbol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (String key : map.keySet())
      sb.append(key);

    return sb.toString();
  }
}
