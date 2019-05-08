package org.hacksource.core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import static com.github.javaparser.Providers.provider;

public class SourceParser {

    public static CompilationUnit parse(String source) throws SourceException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> result = javaParser.parse(COMPILATION_UNIT, provider(source));

        CompilationUnit cu = result.getResult().orElseThrow(() -> new SourceException(result.getProblems()));

        return cu;

    }

    public static CompilationUnit parseFile(Path path) throws SourceException, IOException {
        StringBuilder contentBuilder = new StringBuilder();

        Stream<String> stream = Files.lines(path);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));

        return SourceParser.parse(contentBuilder.toString());

    }

}
