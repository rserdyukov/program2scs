package org.ostis.prosem.c11;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.C11Lexer;
import org.ostis.prosem.C11Parser;
import org.ostis.prosem.ITranslator;

public class C11Translator implements ITranslator {
    private C11Parser parser = null;
    private ParseTree tree = null;

    @Override
    public String getSourceFileExtention() {
        return "c";
    }

    @Override
    public void translate(CharStream input) {
        C11Lexer lexer = new C11Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new C11Parser(tokens);
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
