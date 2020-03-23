package org.ostis.prosem.cpp14;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.CPP14Lexer;
import org.ostis.prosem.CPP14Parser;

import java.io.IOException;

public class Translator {
    public static void main(String[] args) throws IOException {
        CharStream input = CharStreams.fromFileName(args[0]);
        CPP14Lexer lexer = new CPP14Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPP14Parser parser = new CPP14Parser(tokens);

        ParseTree tree = parser.translationunit();
        System.out.println(tree.toStringTree(parser));
    }
}
