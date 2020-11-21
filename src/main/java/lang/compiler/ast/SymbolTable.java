package lang.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
  private Map<String, Object> map;

  public SymbolTable() {
    map = new HashMap<>();
  }

  public Object get(String key) {
    return map.get(key);
  }

  public void put(String key, Object value) {
    map.put(key, value);
  }
}
