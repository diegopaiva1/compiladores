package lang.compiler.ast;

public class Int extends AbstractExpression {
  private Integer value;

  public Int(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }
}
