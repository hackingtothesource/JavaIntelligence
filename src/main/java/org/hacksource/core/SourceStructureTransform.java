package org.hacksource.core;

import com.github.javaparser.ast.CompilationUnit;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SourceStructureTransform {

    public static void transform(CompilationUnit cu, List<SourceProblem> list) {

    }

    public static void main(String[] args) throws IOException, SourceException {
        String path = SourceFormat.class.getResource("/example3.java").getPath();
        path = path.substring(1);

        List<SourceProblem> problems = new ArrayList<>();
        CompilationUnit cu = SourceParser.parseFile(Paths.get(path));
        transform(cu, problems);

        problems.forEach(p -> System.out.println(p));

        FileWriter fileWriter = new FileWriter("generated/example3.java");
        fileWriter.write(cu.toString());
        fileWriter.close();

    }

}
