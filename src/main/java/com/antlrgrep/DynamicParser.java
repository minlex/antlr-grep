package com.antlrgrep;

import org.antlr.runtime.*;

import org.antlr.v4.parse.ANTLRLexer;
import org.antlr.v4.parse.GrammarASTAdaptor;
import org.antlr.v4.parse.ToolANTLRLexer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alexmin
 * Date: 11/23/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class DynamicParser {

    private Lexer lexer;
    private Class lexerClass;
    private Parser parser;
    private Class parserClass;
    private String grammarName;

    public DynamicParser() {
    }

    /**
     * Compile parser and lexer class from file
     * @param fileName  - name of grammar file
     * @throws Exception
     */
    public void loadFromFile(String fileName) throws Exception {

        grammarName = fileName.substring(0,fileName.length()-3);

        // DynamicCompiler.compileFromFile(grammarName+"Lexer.java");

        List<String> files = new ArrayList<>();
        files.add(grammarName+"Lexer.java");
        files.add(grammarName+"Listener.java");
        files.add(grammarName+"Parser.java");
        Map<String, Class> classes = DynamicCompiler.compileFromFiles(files);



        lexerClass = classes.get("Lexer");
        parserClass = classes.get("Parser");


    }


    /**
     * Parse file with lexerClass and ParserClass
     * @param filename
     */
    public ParserRuleContext parseFile(String filename) throws Exception {
        if (lexerClass == null || parserClass == null )
            throw new Exception("No parser or lexer found") ;

       CharStream in = (CharStream) new ANTLRFileStream(filename);
       lexer = (Lexer) lexerClass.getConstructor(CharStream.class).newInstance(in);
       CommonTokenStream tokens = new CommonTokenStream(lexer);
       parser = (Parser) parserClass.getConstructor(TokenStream.class).newInstance(tokens);

       Method rule = parserClass.getDeclaredMethod("file");
       ParserRuleContext tree = (ParserRuleContext) rule.invoke(parser);
       return tree;
     //  System.out.println(tree.toStringTree());


      // getTopRule();
       //parserClass.getDeclaredMethods();



    }

    /**
     * Return top rule in grammar file. Top rule is which defined first.
     * @return
     */
    public void getTopRule() throws IOException {
        org.antlr.runtime.CharStream in = new org.antlr.runtime.ANTLRFileStream(grammarName+".g4");
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(in);
        ANTLRLexer antlrLexer = new ANTLRLexer(in);
        org.antlr.runtime.CommonTokenStream tokens = new org.antlr.runtime.CommonTokenStream(antlrLexer);
        int sizedfas = 200000;
       // List<? extends org.antlr.runtime.Token> rules = tokens.getTokens(0,size,56);

      //  System.out.println(rules.toString());


    }


    public Parser getParser() {
        return parser;
    }
}
