package org.hacksource.core;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SourceStructureTransform {

    public static boolean doExpandSingleIf = false;
    public static boolean doTransferSwitchToIf = false;
    public static boolean doTransferForToWhile = false;

    public static void transform(CompilationUnit cu, List<SourceProblem> list) {
        if (doExpandSingleIf) cu.findAll(IfStmt.class).forEach(s -> expandSingleIf(s, list));
        if (doTransferSwitchToIf) cu.findAll(SwitchStmt.class).forEach(s -> transferSwitchToIf(s, list));
        if (doTransferForToWhile) cu.findAll(ForStmt.class).forEach(s -> transferForToWhile(s, list));
    }

    public static void transferForToWhile(ForStmt stmt, List<SourceProblem> list) {
        NodeList<Expression> init = stmt.getInitialization();
        Optional<Expression> compare = stmt.getCompare();
        NodeList<Expression> update = stmt.getUpdate();

        Statement body = stmt.getBody();

        BlockStmt newStmt = new BlockStmt();
        init.stream().map(e -> new ExpressionStmt(e)).forEach(s -> newStmt.addStatement(s));

        BlockStmt whileBody = null;
        if (body.isBlockStmt()) {
            whileBody = body.asBlockStmt();
        }
        else {
            whileBody = new BlockStmt();
            whileBody.addStatement(body);
        }

        for (Expression e : update) {
            whileBody.addStatement(new ExpressionStmt(e));
        }

        WhileStmt whileStmt = new WhileStmt(compare.orElse(new BooleanLiteralExpr(true)), whileBody);
        newStmt.addStatement(whileStmt);

        TokenRange tokenRange = stmt.getTokenRange().orElseThrow(() -> new RuntimeException("cannot get range"));
        list.add(new SourceProblem("t-transferForToWhile", tokenRange));

        stmt.replace(newStmt);
    }

    public static void transferSwitchToIf(SwitchStmt stmt, List<SourceProblem> list) {
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

        TokenRange tokenRange = stmt.getTokenRange().orElseThrow(() -> new RuntimeException("cannot get range"));
        list.add(new SourceProblem("t-transferSwitchToIf", tokenRange));

        stmt.replace(newStmt);
    }

    public static void expandSingleIf(IfStmt stmt, List<SourceProblem> list) {

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
                TokenRange tokenRange = stmt.getTokenRange().orElse(null);
                if (tokenRange != null) {
                    list.add(new SourceProblem("t-expandSingleIf", tokenRange));
                }

                stmt.replace(newIf);
                newIf.findAll(IfStmt.class).forEach(s -> expandSingleIf(s, list));
            }
        }
    }

    public static void main(String[] args) throws IOException, SourceException {
        doExpandSingleIf = true;
        doTransferForToWhile = true;
        doTransferSwitchToIf = true;

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
