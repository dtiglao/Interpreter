package JuliaScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

class JuliaParser {

    JuliaParser(List<TokenRecord> bucket) {
        System.out.println("<program> -> function id() <block> end");

        // create class Bucket which changes JuliaScanner.bucket into 'bucket' of arrays that store the token records by line ofc
        // so each index of Bucket is an array which is just the token records found on that line
        // iterate line by line
        // local variable (for debugging) that records index/current line number in case error occurs
        // keep in mind tokenrecord.blocknumber to check for correct amount of 'end's
        //
    }

    private void getStatement() {
        System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                "<print_statement> | <for_statement>");
        // TODO determine which
    }

    private void getIfStatement() {
        System.out.println("<if_statement> -> if <boolean_expression>  <block> else <block> end");
        // TODO
    }

    private void getWhileStatement() {
        System.out.println("<while_statement> -> while <boolean_expression> <block> end");
        // TODO
    }

    private void getAssignmentStatement() {
        System.out.println("<assignment_statement> -> id <assignment_operator> <arithmetic_expression>");
        // TODO
    }

    private void getForStatement() {
        System.out.println("<for_statement> -> for id = <iter> <block> end");
        // TODO
    }

    private void getPrintStatement() {
        System.out.println("<print_statement> -> print ( <arithmetic_expression> )");
        // TODO
    }

    private void getIter() {
        System.out.println("<iter> -> <arithmetic_expression> : <arithmetic_expression>");
        // TODO
    }

    private void getBooleanExpression() {
        System.out.println("<boolean_expression> -> <relative_op> <arithmetic_expression> <arithmetic_expression>");
        // TODO
    }

    private void getRelativeOp() {
        System.out.println("<relative_op> -> le_operator | lt_operator | ge_operator | gt_operator | eq_operator | " +
                "ne_operator");
        // TODO
    }

    private void getArithmeticExpression() {
        System.out.println("<arithmetic_expression> -> <id> | <literal_integer> | <binary_expression>");
        // TODO
    }

    private void getBinaryExpression() {
        System.out.println("<binary_expression> -> <arithmetic_op> <arithmetic_expression> <arithmetic_expression>");
        // TODO
    }

    private void getArithmeticOp() {
        System.out.println("<arithmetic_op> -> add_operator | sub_operator | mul_operator | div_operator | " + 
                "mod_operator | exp_operator | rev_div_operator");
        // TODO
    }
}
