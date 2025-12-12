package com.navadeep.CustomGrammerBackend;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.HashMap;
import java.util.Map;
import com.navadeep.CustomGrammerBackend.expression.generated.ExpressionGrammarBaseVisitor;
import com.navadeep.CustomGrammerBackend.expression.generated.ExpressionGrammarLexer;
import com.navadeep.CustomGrammerBackend.expression.generated.ExpressionGrammarParser;

public class ExpressionEvaluator extends ExpressionGrammarBaseVisitor<Double> {
    private Map<String, Double> variables = new HashMap<>();

    private ExpressionGrammarParser lastParser;

    public ExpressionGrammarParser getParser() {
        return lastParser;
    }

    public ParseTree getParseTree(String input) {
        ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        lastParser = new ExpressionGrammarParser(tokens);
        return lastParser.program();
    }
    // Entry point: Evaluate the whole program
    public Double evaluate(String input) {
        // Create lexer and parser
        ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionGrammarParser parser = new ExpressionGrammarParser(tokens);

        // Parse and visit the tree
        ParseTree tree = parser.program();
        return visit(tree);  // Returns the value from 'return'
    }


    //    @Override
    public Double visitProgram(ExpressionGrammarParser.ProgramContext ctx) {
        // Visit all statements, then the return
        for (ExpressionGrammarParser.StatementContext stmt : ctx.statement()) {
            visit(stmt);
        }
        return visit(ctx.returnStmt());
    }

//        @Override
    public Double visitStatement(ExpressionGrammarParser.StatementContext ctx) {
        return visit(ctx.assignment());  // Assignments return null, but we store in map
    }

    //@Override
    public Double visitAssignment(ExpressionGrammarParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();
        Double value = visit(ctx.expr());
        variables.put(varName, value);
        return null;  // Assignments don't return values
    }

    //    @Override
    public Double visitReturnStmt(ExpressionGrammarParser.ReturnStmtContext ctx) {
        return visit(ctx.expr());  // The final result
    }

    //    @Override
    public Double visitMulDiv(ExpressionGrammarParser.MulDivContext ctx) {
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        if (ctx.op.getText().equals("*")) return left * right;
        else return left / right;
    }

    //    @Override
    public Double visitAddSub(ExpressionGrammarParser.AddSubContext ctx) {
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        if (ctx.op.getText().equals("+")) return left + right;
        else return left - right;
    }

    //    @Override
    public Double visitNumber(ExpressionGrammarParser.NumberContext ctx) {
        return Double.parseDouble(ctx.NUMBER().getText());
    }

    //    @Override
    public Double visitVariable(ExpressionGrammarParser.VariableContext ctx) {
        String varName = ctx.ID().getText();
        if (!variables.containsKey(varName)) {
            throw new RuntimeException("Undefined variable: " + varName);
        }
        return variables.get(varName);
    }

    //    @Override
    public Double visitParens(ExpressionGrammarParser.ParensContext ctx) {
        return visit(ctx.expr());
    }




}