package org.ostis.prosem.java;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.ITranslator;
import org.ostis.prosem.JavaLexer;
import org.ostis.prosem.JavaParser;

public class JavaTranslator implements ITranslator {
    private ParseTree tree = null;
    private JavaParser parser = null;

    @Override
    public String getSourceFileExtention() {
        return "java";
    }

    @Override
    public void translate(CharStream input) {
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new JavaParser(tokens);
        tree = parser.compilationUnit();
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
