package org.ostis.prosem;

import com.google.common.io.ByteStreams;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Tree2Dot {

    private int nodeIndex = 0;
    private StringBuilder builder = new StringBuilder();
    private String outputFilepath;
    private String dotFilepath;

    public Tree2Dot(String outputFilepath, String dotFilepath) {
        this.outputFilepath = outputFilepath;
        this.dotFilepath = dotFilepath;
    }

    public static String getNodeText(Tree t, List<String> ruleNames) {
        if (ruleNames != null) {
            if (t instanceof RuleContext) {
                int ruleIndex = ((RuleContext) t).getRuleContext().getRuleIndex();
                String ruleName = ruleNames.get(ruleIndex);
                int altNumber = ((RuleContext) t).getAltNumber();
                if (altNumber != ATN.INVALID_ALT_NUMBER) {
                    System.out.println(ruleName + ":" + altNumber);
                    return ruleName + ":" + altNumber;
                }
                return ruleName;
            } else if (t instanceof ErrorNode) {
                return t.toString();
            } else if (t instanceof TerminalNode) {
                Token symbol = ((TerminalNode) t).getSymbol();
                if (symbol != null) {
                    return symbol.getText();
                }
            }
        }
        // no recog for rule names
        Object payload = t.getPayload();
        if (payload instanceof Token) {
            return ((Token) payload).getText();
        }
        return t.getPayload().toString();
    }

    public void convert(Tree tree, Parser parser) {
        String[] ruleNames = parser != null ? parser.getRuleNames() : null;
        List<String> ruleNamesList = ruleNames != null ? Arrays.asList(ruleNames) : null;

        if (ruleNamesList == null) {
            return;
        }

        nodeIndex = 0;
        builder = new StringBuilder();
        builder.append("digraph {");
        convert(tree, ruleNamesList, null, null);
        builder.append("}");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(dotFilepath, "-Tpng");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            InputStream stdoutStream = process.getInputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(builder.toString());
            writer.flush();
            writer.close();

            ByteStreams.copy(stdoutStream, new FileOutputStream(outputFilepath));

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //System.out.println(builder.toString());
    }

    private void convert(Tree tree, final List<String> ruleNames, final String parentNode, final String parentLabel) {
        String label = getNodeText(tree, ruleNames).replace("\"", "\\\"");
        String node = "n" + nodeIndex++;
        if (parentNode != null) {
            builder.append(parentNode).append("->").append(node).append(";");
            builder.append(node).append("[label=\"").append(label).append("\"");
            if (tree instanceof TerminalNode) {
                builder.append(",shape=box");
            }
            builder.append("];");
        }
        for (int i = 0; i < tree.getChildCount(); i++) {
            convert(tree.getChild(i), ruleNames, node, label);
        }
    }
}
