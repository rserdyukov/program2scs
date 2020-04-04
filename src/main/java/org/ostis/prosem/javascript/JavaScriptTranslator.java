package org.ostis.prosem.javascript;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.ITranslator;
import org.ostis.prosem.JavaScriptLexer;
import org.ostis.prosem.JavaScriptParser;

public class JavaScriptTranslator implements ITranslator {
    private JavaScriptParser parser = null;
    private ParseTree tree = null;

    @Override
    public String getSourceFileExtention() {
        return "js";
    }

    @Override
    public void translate(CharStream input) {
        JavaScriptLexer lexer = new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new JavaScriptParser(tokens);
        tree = parser.program();
    }

    @Override
    public Parser getParser() {
        return parser;
    }

    @Override
    public ParseTree getParseTree() {
        return tree;
    }
}
