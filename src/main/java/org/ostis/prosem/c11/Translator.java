package org.ostis.prosem.c11;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.C11Lexer;
import org.ostis.prosem.C11Parser;

import java.io.IOException;

public class Translator {
    public static void main(String[] args) throws IOException {
        CharStream input = CharStreams.fromFileName(args[0]);
        C11Lexer lexer = new C11Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        C11Parser parser = new C11Parser(tokens);

        ParseTree tree = parser.compilationUnit();
        System.out.println(tree.toStringTree(parser));
    }
}
