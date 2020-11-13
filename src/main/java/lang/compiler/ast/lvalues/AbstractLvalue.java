package lang.compiler.ast.lvalues;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.visitors.AbstractExpressionVisitor;

public abstract class AbstractLvalue extends AbstractExpression {
    public abstract String getLabel();
    public abstract String toKey(AbstractExpressionVisitor v);
}
