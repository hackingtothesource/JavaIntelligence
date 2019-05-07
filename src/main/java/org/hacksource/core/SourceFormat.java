package org.hacksource.core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.*;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.github.javaparser.ParseStart.*;
import static com.github.javaparser.Providers.provider;

public class SourceFormat {

    public static String format(String source) throws SourceException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> result = javaParser.parse(COMPILATION_UNIT, provider(source));

        CompilationUnit cu = result.getResult().orElseThrow(() -> new SourceException(result.getProblems()));

        Arrays.stream(new Class[]{
                IfStmt.class, ForStmt.class, ForEachStmt.class, WhileStmt.class
        }).forEach(c -> addBracket(cu, c));

        return cu.toString();
    }

    private static <T extends Statement> void addBracket(CompilationUnit cu, Class<T> c) {
        cu.findAll(c).forEach(stmt -> {
            List<Node> children = stmt.getChildNodes();
            if(children.size() != 0) {
                Statement block = (Statement)children.get(children.size() - 1);
                if(!block.isBlockStmt()) {
                    stmt.replace(block, new BlockStmt(new NodeList<Statement>(block)));
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {

        String path = SourceFormat.class.getResource("/example.java").getPath();
        path = path.substring(1);

        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String output = "";
        try {
            output = format(contentBuilder.toString());
        } catch (SourceException e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = new FileWriter("generated/example.java");
        fileWriter.write(output);
        fileWriter.close();

    }
}
