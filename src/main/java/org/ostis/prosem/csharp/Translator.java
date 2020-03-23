package org.ostis.prosem.csharp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ostis.prosem.CSharpLexer;
import org.ostis.prosem.CSharpParser;

import java.io.IOException;

public class Translator {
    public static void main(String[] args) throws IOException {
        CharStream input = CharStreams.fromFileName(args[0]);
        CSharpLexer lexer = new CSharpLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CSharpParser parser = new CSharpParser(tokens);

        ParseTree tree = parser.compilation_unit();
        System.out.println(tree.toStringTree(parser));
    }
}
