package com.antlrgrep;
/**
* Created with IntelliJ IDEA.
* User: alexmin
* Date: 11/23/13
* Time: 4:29 PM
* To change this template use File | Settings | File Templates.
*/

//import org.antlr.runtime.ANTLRFileStream;
import org.antlr.v4.Tool;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.apache.commons.cli.*;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.*;

public class AntlrGrep {
    public static void main(String[] args) throws Exception {


        // create the command line parser
        CommandLineParser cmdParser = new GnuParser();

// create the Options
        Options options = new Options();


        options.addOption(OptionBuilder.withLongOpt("grammar")
                .withDescription("Filename of gramma to parse")
                .hasArg()
                .withArgName("FILE")
                .isRequired(true)
                .create("g"));
        options.addOption(OptionBuilder.withLongOpt("pattern")
                .withDescription("witch node of syntax tree select")
                .hasArg()
                .withArgName("PATTERN")
                .isRequired(true)
                .create("p"));

         try {
            // parse the command line arguments
            CommandLine line = cmdParser.parse( options, args );

            String fileName = line.getOptionValue("g");
            String pattern = line.getOptionValue("p");


            String[] otherArgs = line.getArgs();



      //  String fileName = line.args[0];
        Tool antlr = new Tool(new String[]{fileName});

        antlr.processGrammarsOnCommandLine();


        DynamicParser parser = new DynamicParser();
        parser.loadFromFile(fileName);
        //ParserRuleContext tree = parser.parseFile("JavaParser.java");
        ParserRuleContext tree = parser.parseFile(otherArgs[0]);

        ParseTreeWalker walker = new ParseTreeWalker();


        String[] terms = pattern.split(":");
        //Matcher match = new Matcher("classDeclaration", "Identifier");
        Matcher match = new Matcher(terms[0],terms[1]);

        CommonListener listener = new CommonListener(parser.getParser(), match);
        walker.walk(listener, tree);




      //  parser.getTree("file" );
//            Method method = ParserClass.getDeclaredMethod("file");
//        method.invoke(new Object());
   //    DynamicCompiler.compileFromFile(grammarName+"Parser.java");


        ;

        //antlr.exit(0);

       // GrammarRootAST grammaAST = tool.loadFromString("grammar String;\n" + args[0]);
        //final Grammar grammar = tool.createGrammar(grammaAST);

        }
        catch( ParseException exp ) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "antlr-grep", options );
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }

    }
}
