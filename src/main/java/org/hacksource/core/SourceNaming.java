package org.hacksource.core;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserFieldDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserParameterDeclaration;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceNaming {

    public static void naming(CompilationUnit cu, List<SourceProblem> list) {

        Map<Node, List<Node>> symbolMap = new HashMap<>();

        cu.findAll(NameExpr.class).forEach(ne -> {
            ResolvedValueDeclaration rne = null;
            try {
                rne = ne.resolve();
            }
            catch (UnsolvedSymbolException e){
                // TODO some warning here
                return;
            }

            Node node = null;
            if (rne instanceof JavaParserSymbolDeclaration) {
                node = ((JavaParserSymbolDeclaration)rne).getWrappedNode();
            }
            else if (rne instanceof JavaParserFieldDeclaration) {
                FieldDeclaration field = ((JavaParserFieldDeclaration)rne).getWrappedNode();
                node = field.getVariables().stream()
                        .filter(d -> d.getName().equals(ne.getName()))
                        .findFirst().orElseThrow(() -> new RuntimeException("cannot find field"));
            }
            else if (rne instanceof JavaParserParameterDeclaration) {
                node = ((JavaParserParameterDeclaration)rne).getWrappedNode();
            }

            if (symbolMap.containsKey(node)) {
                symbolMap.get(node).add(ne);
            }
            else {
                List<Node> newList = new ArrayList<>();
                newList.add(ne);
                symbolMap.put(node, newList);
            }

        });

        symbolMap.forEach((k, v) -> {
            if (k.getChildNodes().size() >= 2) {
                Node child = k.getChildNodes().get(1);

                fixNameToLower(child.toString()).ifPresent(name -> {
                    if (symbolMap.entrySet().stream().noneMatch(x -> // filter out all of node with conflict
                        ((NodeWithSimpleName)x.getKey()).getName().toString().equals(name)
                    )) {
                        ((NodeWithSimpleName) k).setName(name);

                        for (Node n : v) {
                            ((NameExpr) n).setName(name);
                        }

                        TokenRange tokenRange = k.getTokenRange().orElseThrow(() -> new RuntimeException("cannot get range"));

                        if (k instanceof VariableDeclarator) {
                            Node p = k.getParentNode().orElse(null);
                            if (p instanceof FieldDeclaration) {
                                list.add(new SourceProblem("s5.2.5-non-constant-field-names", tokenRange));
                            } else {
                                list.add(new SourceProblem("s5.2.7-local-variable-names", tokenRange));
                            }

                        } else if (k instanceof Parameter) {
                            list.add(new SourceProblem("s5.2.6-parameter-names", tokenRange));
                        }
                    }
                });
            }
        });
    }

    private static Optional<String> fixNameToLower(String name) {
        StringBuffer result = new StringBuffer(name);

        // first char
        if (result.charAt(0) >= 'A' && result.charAt(0) <= 'Z') {
            result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
        }

        // underline to camel
        Matcher m = Pattern.compile("_[a-z]").matcher(result);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group().replace("_","").toUpperCase());
        }
        m.appendTail(sb);
        result = sb;

        if(result.toString().equals(name)) {
            return Optional.empty();
        }
        else {
            return Optional.of(result.toString());
        }
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
