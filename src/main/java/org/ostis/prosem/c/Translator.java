package org.ostis.prosem.c;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.CLexer;
import org.ostis.prosem.CParser;

import java.io.IOException;

public class Translator {
    public static void main(String[] args) throws IOException {
        CharStream input = CharStreams.fromFileName(args[0]);
        CLexer lexer = new CLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ParseTree tree = parser.compilationUnit();
        System.out.println(tree.toStringTree(parser));
    }
}
