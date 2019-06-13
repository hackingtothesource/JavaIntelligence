package org.hacksource.core;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SourceFormat {

    public static String format(CompilationUnit cu, List<SourceProblem> list) {

        Arrays.stream(new Class[]{
                IfStmt.class, ForStmt.class, ForEachStmt.class, WhileStmt.class
        }).forEach(c -> addStmtBracket(cu, c, list));

        return cu.toString();
    }

    private static void addBlock(Statement stmt, Statement block, List<SourceProblem> list) {
        stmt.replace(block, new BlockStmt(new NodeList<>(block)));

        TokenRange tokenRange = stmt.getTokenRange().orElseThrow(() -> new RuntimeException("cannot get range"));
        list.add(new SourceProblem("s4.1.1-braces-always-used", tokenRange));
    }

    private static <T extends Statement> void addStmtBracket(CompilationUnit cu, Class<T> c, List<SourceProblem> list) {
        cu.findAll(c).forEach(stmt -> {
            List<Node> children = stmt.getChildNodes();

            if(children.size() != 0) {
                if (stmt.isIfStmt()) {
                    Statement thenBlock = stmt.asIfStmt().getThenStmt();
                    Statement elseBlock = stmt.asIfStmt().getElseStmt().orElse(null);

                    if(!thenBlock.isBlockStmt()) {
                        addBlock(stmt, thenBlock, list);
                    }
                    if(elseBlock != null && !elseBlock.isIfStmt() && !elseBlock.isBlockStmt()) {
                        addBlock(stmt, elseBlock, list);
                    }

                } else {
                    Statement block = (Statement) children.get(children.size() - 1);

                    if(!block.isBlockStmt()) {
                        addBlock(stmt, block, list);
                    }
                }
            }
        });
    }

    public static void main(String[] args) throws IOException, SourceException {

        String path = SourceFormat.class.getResource("/example.java").getPath();
        path = path.substring(1);

        List<SourceProblem> problems = new ArrayList<>();
        String output = format(SourceParser.parseFile(Paths.get(path)), problems);

        problems.forEach(p -> System.out.println(p));

        FileWriter fileWriter = new FileWriter("generated/example.java");
        fileWriter.write(output);
        fileWriter.close();

    }
}
