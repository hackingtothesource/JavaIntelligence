package org.hacksource.core;

import com.github.javaparser.Problem;

import java.util.List;

public class SourceException extends Exception {

    private List<Problem> problems;

    SourceException(List<Problem> problem) {
        problems = problem;
    }

    public List<Problem> getProblems() {
        return problems;
    }

}
