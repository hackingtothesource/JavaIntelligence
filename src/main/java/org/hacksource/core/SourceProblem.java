package org.hacksource.core;

import com.github.javaparser.Range;
import com.github.javaparser.TokenRange;

public class SourceProblem {

    private String codeStyleTag;
    private TokenRange tokenRange;

    SourceProblem(String tag, TokenRange range) {
        codeStyleTag = tag;
        tokenRange = range;
    }

    public String getCodeStyleTag() {
        return codeStyleTag;
    }

    public TokenRange getTokenRange() {
        return tokenRange;
    }

    public Range getRange() {
        return getTokenRange().toRange().orElseThrow(() -> new RuntimeException("cannot get range"));
    }

    @Override
    public String toString() {
        return getRange().toString() + ":" + codeStyleTag;
    }
}
