package org.hacksource.core;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class SourceFormat {

    public static String format(CompilationUnit cu) {

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
                    Range range = stmt.getRange().orElseThrow(() -> new RuntimeException("cannot get range"));
                    System.out.println(range.toString());
                }
            }
        });
    }

    public static void main(String[] args) throws IOException, SourceException {

        String path = SourceFormat.class.getResource("/example.java").getPath();
        path = path.substring(1);

        String output = format(SourceParser.parseFile(Paths.get(path)));

        FileWriter fileWriter = new FileWriter("generated/example.java");
        fileWriter.write(output);
        fileWriter.close();

    }
}
