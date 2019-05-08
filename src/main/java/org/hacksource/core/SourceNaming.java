package org.hacksource.core;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserFieldDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserParameterDeclaration;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class SourceNaming {

    public static void naming(CompilationUnit cu, List<SourceProblem> list) {

        Map<Node, List<Node>> symbolMap = new HashMap<>();

        cu.findAll(NameExpr.class).forEach(ne -> {
            ResolvedValueDeclaration rne = ne.resolve();

            Node node = null;
            if(rne instanceof JavaParserSymbolDeclaration) {
                node = ((JavaParserSymbolDeclaration)rne).getWrappedNode();
            }
            else if (rne instanceof JavaParserFieldDeclaration) {
                node = ((JavaParserFieldDeclaration)rne).getWrappedNode();
            }
            else if (rne instanceof JavaParserParameterDeclaration) {
                node = ((JavaParserParameterDeclaration)rne).getWrappedNode();
            }

            if(symbolMap.containsKey(node)) {
                symbolMap.get(node).add(ne);
            }
            else {
                List<Node> newList = new ArrayList<>();
                newList.add(ne);
                symbolMap.put(node, newList);
            }

        });

        symbolMap.forEach((k, v) -> {
            if(k instanceof FieldDeclaration) {
                // TODO
            }
            else if(k instanceof Parameter) {
                // TODO
            }
            else if(k instanceof VariableDeclarator){
                // local vars
                if(k.getChildNodes().size() >= 2) {
                    Node child = k.getChildNodes().get(1);

                    fixNameToLower(child.toString()).ifPresent(name -> {
                        ((VariableDeclarator)k).setName(name);

                        for (Node n : v) {
                            ((NameExpr)n).setName(name);
                        }

                        TokenRange tokenRange = k.getTokenRange().orElseThrow(() -> new RuntimeException("cannot get range"));
                        list.add(new SourceProblem("s5.2.7-local-variable-names", tokenRange));
                    });
                }
            }
        });
    }

    private static Optional<String> fixNameToLower(String name) {
        if(name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') {
            return Optional.of(name.substring(0, 1).toLowerCase() + name.substring(1));
        }

        else return Optional.empty();
    }

    public static void main(String[] args) throws IOException, SourceException {
        String path = SourceFormat.class.getResource("/example2.java").getPath();
        path = path.substring(1);

        List<SourceProblem> problems = new ArrayList<>();
        CompilationUnit cu = SourceParser.parseFile(Paths.get(path));
        naming(cu, problems);

        problems.forEach(p -> System.out.println(p));

        FileWriter fileWriter = new FileWriter("generated/example2.java");
        fileWriter.write(cu.toString());
        fileWriter.close();

    }

}
