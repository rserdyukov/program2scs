package org.ostis.prosem.csharp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.CSharpLexer;
import org.ostis.prosem.CSharpParser;
import org.ostis.prosem.ITranslator;

public class CSharpTranslator implements ITranslator {
    private CSharpParser parser = null;
    private ParseTree tree = null;

    @Override
    public String getSourceFileExtention() {
        return "cs";
    }

    @Override
    public void translate(CharStream input) {
        CSharpLexer lexer = new CSharpLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new CSharpParser(tokens);
        tree = parser.compilation_unit();
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
