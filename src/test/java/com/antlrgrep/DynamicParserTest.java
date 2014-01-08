package com.antlrgrep;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created with IntelliJ IDEA.
 * User: alexmin
 * Date: 12/1/13
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class DynamicParserTest {

    @Test
    public void testGetTopRule() throws Exception {

        DynamicParser parser = new DynamicParser();
        parser.loadFromFile("Java.g4");
        parser.getTopRule();

    }
}
