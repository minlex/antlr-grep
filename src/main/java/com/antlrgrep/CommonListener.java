package com.antlrgrep;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.*;

import static java.util.Arrays.asList;


/**
 * Created by alexmin on 12/22/13.
 */
public class CommonListener implements ParseTreeListener{


    private final String[] ruleNames;
    private final String[] tokenNames;
    private final Matcher matcher;

    public CommonListener(Parser parser, Matcher match) {
        ruleNames = parser.getRuleNames();
        tokenNames = parser.getTokenNames();
        matcher = match;
    }

    @Override
    public void visitTerminal(@NotNull TerminalNode node) {
  //      System.out.println("consume "+node.getSymbol()+" rule ");

    }

    @Override
    public void visitErrorNode(@NotNull ErrorNode node) {

    }

    @Override
    public void enterEveryRule(@NotNull ParserRuleContext ctx) {

        String rule = ruleNames[ctx.getRuleIndex()];
  //      System.out.println("enter   " + rule);

        if (rule.equals(matcher.getTopRule())){
            int type = asList(tokenNames).indexOf(matcher.getMatchRule());

            for(int i = 0; i<ctx.getChildCount(); i++){
                TerminalNodeImpl child = ctx.getChild(TerminalNodeImpl.class, i);
                if (child == null)
                    continue;

               if (child.getSymbol().getType() == type)
                   System.out.println(child.getText());

            }
     //       System.out.println("Found class declarastion");
        }

    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx) {

    }
}
