package com.antlrgrep;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: alexmin
 * Date: 11/23/13
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class DynamicCompilerTest {
    @Test
    public void testCompileFromString() throws Exception {

        StringBuilder contents = new StringBuilder(
                "package math;"+
                        "public class Calculator { "
                        + "  public void testAdd() { "
                        + "    System.out.println(200+300); "
                        + "  } "
                        + "  public static void main(String[] args) { "
                        + "    Calculator cal = new Calculator(); "
                        + "    cal.testAdd(); "
                        + "  } " + "} ");

        HashMap<String,String> classInfo = new HashMap<String, String>();
        classInfo.put("math.Calculator",contents.toString());
        DynamicCompiler.compileFromString(classInfo);

        assertTrue(true);

    }
}
