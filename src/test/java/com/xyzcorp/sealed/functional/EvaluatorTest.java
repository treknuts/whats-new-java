package com.xyzcorp.sealed.functional;

import org.junit.jupiter.api.Test;

/*
 * More from Dan on the interpreter pattern
 * https://github.com/dhinojosa/design_patterns_training/tree/master/src/main/java/com/xyzcorp/javapatterns/interpreter
*/
public class EvaluatorTest {

    @Test
    void testBasicEvaluation() {
        Expression expression = new Multiply(new Constant(40),
            new Sum(new Constant(40), new Constant(60)));
        System.out.println(Evaluator.evaluate(expression));
    }

    @Test
    void testAdvancedEvaluation() {
        Expression expression = new Multiply(new Constant(40),
            new Sum(new Constant(40), new Constant(60)));
        System.out.println(Evaluator.evaluatePatternMatchAdvanced(expression));
    }
}
