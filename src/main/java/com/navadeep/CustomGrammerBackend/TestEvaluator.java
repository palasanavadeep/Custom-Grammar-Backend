package com.navadeep.CustomGrammerBackend;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.gui.TreeViewer;
import javax.swing.*;
import java.util.Arrays;

public class TestEvaluator {
    public static void main(String[] args) {



        ExpressionEvaluator evaluator = new ExpressionEvaluator();

        String expression = """
            x = 10.555;
            y = 20.445;
            za = (x / y);
            return x + y;
            """;

        try {
            Double result = evaluator.evaluate(expression);
            System.out.println("Result: " + result);  // Output: Result: 10.2
        } catch (Exception e) {
            System.err.println("Evaluation failed: " + e.getMessage());
        }

        // Get the parse tree
        ParseTree tree = evaluator.getParseTree(expression);

        // Show the tree
        TreeVisualizer.show(tree, evaluator.getParser());


    }
}