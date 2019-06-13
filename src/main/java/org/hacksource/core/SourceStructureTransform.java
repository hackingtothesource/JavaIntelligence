package org.hacksource.core;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SourceStructureTransform {

    public static void transform(CompilationUnit cu, List<SourceProblem> list) {
        cu.findAll(IfStmt.class).forEach(s -> expandSingleIf(s));
        cu.findAll(SwitchStmt.class).forEach(s -> transferSwitchToIf(s));
        cu.findAll(ForStmt.class).forEach(s -> transferForToWhile(s));
    }

    public static void transferForToWhile(ForStmt stmt) {
    }

    public static void transferSwitchToIf(SwitchStmt stmt) {
        Expression selector = stmt.getSelector();
        NodeList<SwitchEntryStmt> entries = stmt.getEntries();
        IfStmt newStmt = null;
        IfStmt nowStmt = null;

        for (SwitchEntryStmt entry : entries) {
            NodeList<Statement> entryStmts = entry.getStatements();
            Optional<Expression> label = entry.getLabel();
            // TODO switch cases with no breakStmt
            if (label.isPresent()) {
                if (!entryStmts.isEmpty() && !entryStmts.get(entryStmts.size() - 1).isBreakStmt()) {
                    return;
                }
                entryStmts.remove(entryStmts.size() - 1);
            }
            else {
                if (!entryStmts.isEmpty() && entryStmts.get(entryStmts.size() - 1).isBreakStmt()) {
                    entryStmts.remove(entryStmts.size() - 1);
                }
            }
            if (newStmt == null) {
                if (label.isPresent()) {
                    newStmt = new IfStmt(
                            new BinaryExpr(selector, label.orElse(null), BinaryExpr.Operator.EQUALS),
                            new BlockStmt(entryStmts),
                            null);
                    nowStmt = newStmt;
                }
                else {
                    // start with default label
                    return;
                }
            }
            else {
                if (label.isPresent()) {
                    nowStmt.setElseStmt(new IfStmt(
                            new BinaryExpr(selector, label.orElse(null), BinaryExpr.Operator.EQUALS),
                            new BlockStmt(entryStmts),
                            null));

                    nowStmt = nowStmt.getElseStmt().orElse(null).asIfStmt();
                }
                else {
                    // assert default case is the last case
                    if(entry != entries.get(entries.size() - 1)) {
                        return;
                    }
                    nowStmt.setElseStmt(new BlockStmt(entryStmts));
                }
            }
        }

        stmt.replace(newStmt);
    }

    public static void expandSingleIf(IfStmt stmt) {

        Expression cond = stmt.getCondition();

        if (cond.isBinaryExpr()) {
            BinaryExpr be = cond.asBinaryExpr();
            BinaryExpr.Operator op = be.getOperator();
            IfStmt newIf = null;

            if (op.equals(BinaryExpr.Operator.AND)) {
                newIf = new IfStmt(
                        be.getLeft(),
                        new BlockStmt(new NodeList<> (new IfStmt(
                                be.getRight(),
                                stmt.getThenStmt(),
                                stmt.getElseStmt().orElse(null)))),
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
