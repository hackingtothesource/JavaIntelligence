package org.hacksource.core;

import com.github.javaparser.ast.CompilationUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class IntelligenceCLI {
    public static void main(String[] args) {
        StringBuffer input = new StringBuffer();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            input.append(scanner.nextLine());
            input.append('\n');
        }


        Map<String, Object> resultMap = new HashMap<>();
        try {
            CompilationUnit cu = SourceParser.parse(input.toString());

            List<SourceProblem> problemList = new ArrayList<>();
            SourceFormat.format(cu, problemList);
            SourceNaming.naming(cu, problemList);

            resultMap.put("success", true);
            resultMap.put("originCode", input);
            resultMap.put("fixedCode", cu.toString());

            List<String> problemStrings = new ArrayList<>();
            for (SourceProblem p : problemList) {
                problemStrings.add(p.toString());
            }
            resultMap.put("problems", problemStrings);

        } catch (SourceException e) {
            resultMap.put("success", false);
            resultMap.put("error", e.getMessage());
        }

        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(resultMap));

    }
}
