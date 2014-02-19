package io.crate.operator.operator;

import io.crate.planner.symbol.*;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OrOperatorTest {

    @Test
    public void testNormalizeSymbolReferenceAndLiteral() throws Exception {
        OrOperator operator = new OrOperator();

        Function function = new Function(
                operator.info(), Arrays.<Symbol>asList(new Reference(), new BooleanLiteral(true)));
        Symbol normalizedSymbol = operator.normalizeSymbol(function);
        assertThat(normalizedSymbol, instanceOf(BooleanLiteral.class));
        assertThat(((BooleanLiteral)normalizedSymbol).value(), is(true));
    }

    @Test
    public void testNormalizeSymbolReferenceAndLiteralFalse() throws Exception {
        OrOperator operator = new OrOperator();

        Function function = new Function(
                operator.info(), Arrays.<Symbol>asList(new Reference(), new BooleanLiteral(false)));
        Symbol normalizedSymbol = operator.normalizeSymbol(function);
        assertThat(normalizedSymbol, instanceOf(Function.class));
    }

    @Test
    public void testNormalizeSymbolReferenceAndReference() throws Exception {
        OrOperator operator = new OrOperator();

        Function function = new Function(
                operator.info(), Arrays.<Symbol>asList(new Reference(), new Reference()));
        Symbol normalizedSymbol = operator.normalizeSymbol(function);
        assertThat(normalizedSymbol, instanceOf(Function.class));
    }

    @Test
    public void testEvaluateTrue() {
        OrOperator operator = new OrOperator();
        Boolean result = operator.evaluate(
                new BooleanLiteral(false),
                new BooleanLiteral(true),
                new BooleanLiteral(false)
        );
        assertTrue(result);
    }

    @Test
    public void testEvaluateFalse() {
        OrOperator operator = new OrOperator();
        Boolean result = operator.evaluate(
                new BooleanLiteral(false),
                new BooleanLiteral(false)
        );
        assertFalse(result);
    }

}
