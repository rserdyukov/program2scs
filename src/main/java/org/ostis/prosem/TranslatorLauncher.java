package org.ostis.prosem;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;
import org.ostis.prosem.c11.C11Translator;
import org.ostis.prosem.cpp14.Cpp14Translator;
import org.ostis.prosem.csharp.CSharpTranslator;
import org.ostis.prosem.java.JavaTranslator;
import org.ostis.prosem.javascript.JavaScriptTranslator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TranslatorLauncher {

    private Map<String, ITranslator> registeredTraslatorMap = new HashMap<>();

    public static void main(String[] args) {
        TranslatorLauncher launcher = new TranslatorLauncher();

        launcher.registerTranslator(new C11Translator());
        launcher.registerTranslator(new Cpp14Translator());
        launcher.registerTranslator(new CSharpTranslator());
        launcher.registerTranslator(new JavaTranslator());
        launcher.registerTranslator(new JavaScriptTranslator());

        CommandLine commandLine = launcher.parseProgramArguments(args);
        launcher.run(commandLine);
    }

    private void registerTranslator(ITranslator translator) {
        registeredTraslatorMap.put(translator.getSourceFileExtention(), translator);
    }

    private CommandLine parseProgramArguments(String[] args) {
        Options options = new Options();

        options.addOption(Option.builder().desc("Print only AST").longOpt("print-ast").build());

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine commandLine = null;

        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("program2scs", options);
            System.exit(1);
        }

        return commandLine;
    }

    private void run(CommandLine commandLine) {
        String[] fileList = commandLine.getArgs();

        if (fileList == null || fileList.length == 0) {
            System.out.println("Need input filename");
            System.exit(1);
        }

        // NOTE: Work with first filename
        String filepath = fileList[0];
        String fileext = FilenameUtils.getExtension(filepath);

        if (!registeredTraslatorMap.containsKey(fileext)) {
            System.out.println("Unknown file extension");
            System.exit(1);
        }

        ITranslator translator = registeredTraslatorMap.get(fileext);

        CharStream inputStream = null;
        try {
            inputStream = CharStreams.fromFileName(filepath);
        } catch (IOException e) {
            System.out.println("Fail read input filename, " + e.toString());
            System.exit(1);
        }

        translator.translate(inputStream);
        ParseTree tree = translator.getParseTree();
        //System.out.println(tree.toStringTree(translator.getParser()));

        new Tree2Dot(filepath + ".png", "dot").convert(tree, translator.getParser());
    }
}
