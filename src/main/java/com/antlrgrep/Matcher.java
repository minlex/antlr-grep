package com.antlrgrep;

/**
 * Created by alexmin on 12/22/13.
 */
public class Matcher {

    private String topRule;
    private String matchRule;

    public Matcher(String topRule, String matchRule) {
        this.topRule = topRule;
        this.matchRule = matchRule;
    }


    public String getTopRule() {
        return topRule;
    }

    public String getMatchRule() {
        return matchRule;
    }
}
