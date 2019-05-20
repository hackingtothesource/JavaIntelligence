package org.hacksource.core;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SourceStructureTransform {

    public static void transform(CompilationUnit cu, List<SourceProblem> list) {
        cu.findAll(IfStmt.class).forEach(s -> expandSingleIf(s));
    }

    public static void expandSingleIf(IfStmt stmt) {

        Expression cond = stmt.getCondition();
        System.out.println(cond);

        if (cond.isBinaryExpr()) {
            BinaryExpr be = cond.asBinaryExpr();
            BinaryExpr.Operator op = be.getOperator();
            IfStmt newIf = null;

            if (op.equals(BinaryExpr.Operator.AND)) {
                newIf = new IfStmt(
                        be.getLeft(),
                        new IfStmt(
                                be.getRight(),
                                stmt.getThenStmt(),
                                stmt.getElseStmt().orElse(null)),
                        stmt.getElseStmt().orElse(null)
                );
            } else if (op.equals(BinaryExpr.Operator.OR)) {
                newIf = new IfStmt(
                        be.getLeft(),
                        stmt.getThenStmt(),
                        new IfStmt(
                                be.getRight(),
                                stmt.getThenStmt(),
                                stmt.getElseStmt().orElse(null)
                        )
                );
            }

            if (newIf != null) {
                stmt.replace(newIf);
                newIf.findAll(IfStmt.class).forEach(s -> expandSingleIf(s));
            }
        }
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
