package org.hacksource.cli;

import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hacksource.core.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class IntelligenceCLI {
    public static void main(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "--expandSingleIf":
                    SourceStructureTransform.doExpandSingleIf = true;
                    break;
                case "--transferSwitchToIf":
                    SourceStructureTransform.doTransferSwitchToIf = true;
                    break;
                case "--transferForToWhile":
                    SourceStructureTransform.doTransferForToWhile = true;
                    break;
            }
        }

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
            SourceStructureTransform.transform(cu, problemList);

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

            List<String> problems = new ArrayList<>();
            for(Problem p : e.getProblems()) {
                problems.add(p.toString());
            }
            resultMap.put("error", problems);
        }

        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(resultMap));

    }
}
