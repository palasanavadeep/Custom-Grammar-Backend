package com.navadeep.CustomGrammerBackend;


import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.util.Arrays;

import com.navadeep.CustomGrammerBackend.expression.generated.ExpressionGrammarParser;

public class TreeVisualizer {

    public static void show(ParseTree tree, ExpressionGrammarParser parser) {
        JFrame frame = new JFrame("ANTLR Parse Tree");
        JPanel panel = new JPanel();

        TreeViewer viewer =
                new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);

        viewer.setScale(1.5); // Optional zoom

        panel.add(viewer);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}