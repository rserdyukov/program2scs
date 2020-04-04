package org.ostis.prosem;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;

public interface ITranslator {
    String getSourceFileExtention();

    void translate(CharStream input);

    Parser getParser();

    ParseTree getParseTree();
}
