package lang.compiler.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.compiler.ast.types.AbstractType;

public class LocalEnvironment {
  private String name;
  private List<AbstractType> paramsTypes;
  private List<AbstractType> returnsTypes;
  private Map<String, AbstractType> varsTypes;

  public LocalEnvironment(String name) {
    this.name = name;
    this.paramsTypes = new ArrayList<>();
    this.returnsTypes = new ArrayList<>();
    this.varsTypes = new HashMap<>();
  }

  public void addParameterType(AbstractType parameterType) {
    paramsTypes.add(parameterType);
  }

  public void addReturnType(AbstractType returnType) {
    returnsTypes.add(returnType);
  }

  public void addVarType(String name, AbstractType type) {
    varsTypes.put(name, type);
  }

  public List<AbstractType> getParamsTypes() {
    return paramsTypes;
  }

  public void setParamsTypes(List<AbstractType> params) {
    this.paramsTypes = params;
  }

  public List<AbstractType> getReturnsTypes() {
    return returnsTypes;
  }

  public void setReturnsTypes(List<AbstractType> returnsTypes) {
    this.returnsTypes = returnsTypes;
  }

  public Map<String, AbstractType> getVarsTypes() {
    return varsTypes;
  }

  public void setVarsTypes(Map<String, AbstractType> varsTypes) {
    this.varsTypes = varsTypes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
