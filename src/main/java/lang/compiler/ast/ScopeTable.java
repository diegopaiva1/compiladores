package lang.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class ScopeTable {
  private int scopeLevel;
  private List<SymbolTable> scopes;

  public ScopeTable() {
    scopeLevel = 0;
    scopes = new ArrayList<>();
    scopes.add(new SymbolTable());
  }

  public int getScopeLevel() {
    return scopeLevel;
  }

  public void setScopeLevel(int scopeLevel) {
    this.scopeLevel = scopeLevel;
  }

  public void put(String key, Object object) {
    scopes.get(scopeLevel).put(key, object);
  }

  public int push(SymbolTable symbolTable) {
    scopes.add(symbolTable);
    scopeLevel++;
    return scopeLevel;
  }

  public int pop() throws RuntimeException {
    if (scopeLevel == 0)
      throw new RuntimeException("Attempted to pop level-0 scope");

    scopes.remove(scopeLevel);
    scopeLevel--;
    return scopeLevel;
  }

  public Object search(String key) {
    int level = scopeLevel;

    while (level >= 0) {
      Object object = scopes.get(level).get(key);

      if (object != null)
        return object;

      level--;
    }

    return null;
  }
}
