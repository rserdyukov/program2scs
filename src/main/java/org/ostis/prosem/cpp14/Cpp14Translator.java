package org.ostis.prosem.cpp14;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.CPP14Lexer;
import org.ostis.prosem.CPP14Parser;
import org.ostis.prosem.ITranslator;

public class Cpp14Translator implements ITranslator {
    private CPP14Parser parser = null;
    private ParseTree tree = null;

    @Override
    public String getSourceFileExtention() {
        return "cpp";
    }

    @Override
    public void translate(CharStream input) {
        CPP14Lexer lexer = new CPP14Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new CPP14Parser(tokens);
        tree = parser.translationunit();
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
