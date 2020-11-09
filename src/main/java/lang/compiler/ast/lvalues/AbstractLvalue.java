package lang.compiler.ast.lvalues;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractLvalue extends AbstractExpression {
    public abstract String getLabel();
    public abstract String toString();
}
