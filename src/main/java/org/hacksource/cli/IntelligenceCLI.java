package org.hacksource.cli;

import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hacksource.core.*;
import org.hacksource.utils.CMDUtil;
import org.hacksource.utils.Pmd;
import org.hacksource.utils.SaveAsJava;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class IntelligenceCLI {
    static String pmdPath = ".\\pmd-bin-6.16.0\\bin";
    //static String pmdPath = "D:\\JavaEEworkplace\\SoftwareContest\\pmd-bin-6.16.0\\bin";
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
                case "--usePMD":
                    SourceStructureTransform.usePMD = true;

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
            List<String> problemStrings = new ArrayList<>();

            //是否使用pmd
            if(SourceStructureTransform.usePMD) {

                SaveAsJava.Save("test.java", input.toString());
                //parse with pmd solution
                List<String> problemListFromPmd = new ArrayList<>();
                problemListFromPmd = Pmd.Exc(pmdPath, "test.java");
                problemStrings.addAll(problemListFromPmd);
            }

            List<SourceProblem> problemList = new ArrayList<>();
            SourceFormat.format(cu, problemList);
            SourceNaming.naming(cu, problemList);
            SourceStructureTransform.transform(cu, problemList);
            
            resultMap.put("success", true);
            resultMap.put("originCode", input);
            resultMap.put("fixedCode", cu.toString());


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
