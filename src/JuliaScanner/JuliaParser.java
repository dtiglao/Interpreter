package JuliaScanner;

import java.util.Arrays;


class JuliaParser {
    private int lineNumber = 1;
    private boolean error = false;

    JuliaParser(Bucket bucket) {
        TokenRecord[] temp = bucket.grab(bucket.size() - 1);
        TokenRecord last = temp[temp.length - 1];
        if (last.getBlockNumber() > -1) {
            System.out.println("\nPARSING ERROR: Function not ended properly. Please include 'end' keyword!");
            return;
        } else if (last.getBlockNumber() < -1) {
            System.out.println("\nPARSING ERROR: Additional 'end' found. Please remove 'end' keyword");
            return;
        }
        for (TokenRecord[] line : bucket.getBucket()) {
            //System.out.println(Arrays.toString(line));
            if (!error) {
                // CREATE TEMP VAR TO WORK WITH
                String[] tempLine = new String[line.length];
                for (int i = 0; i < line.length; i++) {
                    tempLine[i] = line[i].getTokenCode();
                }
                //System.out.println();
                //System.out.println("\t" + Arrays.toString(tempLine));
                // NOW ONTO LOGIC

                // Don't need to run else through the parser since it's attached to if
                //System.out.println(Arrays.toString(tempLine) + ":");
                if (tempLine[0].equals("1009") || tempLine[0].equals("1002")) {
                    assert true;
                }
                else if (Arrays.asList(tempLine).contains("1003")) {
                    getFunction(line, tempLine);
                } else {
                    getStatement(line, tempLine);
                }
                lineNumber++;
            } else {
                // if an error is found, then the algorithm should close out
                return;
            }
            //System.out.println();
        }
    }

    private void getFunction(TokenRecord[] line, String[] tokenCodes) {
        if (!error) {
            // function should match template {function, id, (, )}
            String[] funcTemplate = {"1003", "3001", "0116", "0117"};
            if (Arrays.equals(tokenCodes, funcTemplate)) {
                System.out.println("<program> -> function id() <block> end");
            } else {
                System.out.println("\nPARSING ERROR: Cannot resolve function on line " + lineNumber + ":\t"
                + toString(line));
                System.out.println("Please ensure that program statement follows proper structure: " +
                        "<program> -> function id() <block> end");
                error = true;
            }
        }
    }

    private void getStatement(TokenRecord[] line, String[] tokenCodes) {
        if (!error) {
            if (Arrays.asList(tokenCodes).contains("1005")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getForStatement(line, tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("1006")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getWhileStatement(line, tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("1007")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getIfStatement(line, tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("1011")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getPrintStatement(line, tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("0100")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getAssignmentStatement(line, tokenCodes);
            } else {
                System.out.println("\nPARSING ERROR: Cannot resolve statement on line " + lineNumber+ ": "
                        + toString(line));
                error = true;
            }
        }
    }

    private void getIfStatement(TokenRecord[] line, String[] tokenCodes) {
        if (tokenCodes[0].equals("1007")) {
            String[] boolExp = Arrays.copyOfRange(tokenCodes, 1, tokenCodes.length);
            String bool = getBooleanExpression(Arrays.copyOfRange(line, 1, line.length), boolExp);
            if (!error) {
                System.out.println("<if_statement> -> if <boolean_expression> <block> else <block> end");
            }
            System.out.println(bool);
            System.out.println(Arrays.copyOfRange(line, 1, line.length));
        } else {
            System.out.println("\nPARSING ERROR: Cannot resolve if statement on line: " + lineNumber);
            System.out.println("Please ensure that if statement follows proper structure: " +
                    "<while_statement> -> while <boolean_expression> <block> end");
            error = true;
        }
    }

    private void getWhileStatement(TokenRecord[] line, String[] tokenCodes) {
        if (tokenCodes[0].equals("1006")) {
            String[] boolExp = Arrays.copyOfRange(tokenCodes, 1, tokenCodes.length);
            String bool = getBooleanExpression(Arrays.copyOfRange(line, 1, line.length), boolExp);
            if (!error) {
                System.out.println("<while_statement> -> while <boolean_expression> <block> end");
            }
            System.out.println(bool);
            System.out.println(tokenAssignments(Arrays.copyOfRange(line, 1, line.length)));
        } else {
            System.out.println("\nPARSING ERROR: Cannot resolve while statement on line " + lineNumber + ":\t"
                    + toString(line));
            System.out.println("Please ensure that while statement follows proper structure: " +
                    "<while_statement> -> while <boolean_expression> <block> end");
            error = true;
        }
    }

    private void getAssignmentStatement(TokenRecord[] line, String[] tokenCodes) {
        if (tokenCodes[0].equals("3001")) {
            if (tokenCodes[1].equals("0100")) {
                String ar_exp = getArithmeticExpression(Arrays.copyOfRange(line, 2, line.length),
                        Arrays.copyOfRange(tokenCodes, 2, tokenCodes.length));
                if (!error) {
                    System.out.println("<assignment_statement> -> id <assignment_operator> <arithmetic_expression>");
                }
                System.out.println(ar_exp);
                System.out.println(tokenAssignments(line));
                //
            } else {
                System.out.println("\nPARSING ERROR: Cannot resolve assignment statement on line " + lineNumber + ":\t"
                        + toString(line));
                System.out.println("Token '=' is in the wrong place.");
                System.out.println("Please ensure that assignment statement follows proper structure: " +
                        "<assignment_statement> -> id <assignment_operator> <arithmetic_expression>");
                error = true;
            }
        } else {
            System.out.println("\nPARSING ERROR: Cannot resolve assignment statement on line " + lineNumber + ":\t"
                    + toString(line));
            System.out.println("\tId token is incorrect.");
            System.out.println("Please ensure that assignment statement follows proper structure: " +
                    "<assignment_statement> -> id <assignment_operator> <arithmetic_expression>");
            error = true;
        }
    }

    private void getForStatement(TokenRecord[] line, String[] tokenCodes) {
        if (tokenCodes[0].equals("1005")) {
            if (!tokenCodes[1].equals("3001")) {
                error = true;
                System.out.println("\nPARSING ERROR: Cannot resolve for statement on line " + lineNumber + ":\t"
                        + toString(line));
                System.out.println("Token " + line[1].getLexeme() + " should be an identifier.");
                System.out.println("Please ensure that for statement follows proper structure: " +
                        "<for_statement> -> for id = <iter> <block> end");
            }
            else if (tokenCodes[2].equals("0100")) {
                String[] iterCodes = Arrays.copyOfRange(tokenCodes, 3, tokenCodes.length);
                String iter = getIter(Arrays.copyOfRange(line, 3, line.length), iterCodes);
                if (!error) {
                    System.out.println("<for_statement> -> for id = <iter> <block> end");
                }
                System.out.println(iter);
            } else {
                System.out.println("\nPARSING ERROR: Cannot resolve for statement on line " + lineNumber + ":\t"
                        + toString(line));
                System.out.println("Token " + line[2].getLexeme() + "should be Token '='.");
                System.out.println("Please ensure that for statement follows proper structure: " +
                        "<for_statement> -> for id = <iter> <block> end");
                error = true;
            }
        } else {
            System.out.println("\nPARSING ERROR: Cannot resolve for statement on line " + lineNumber + ":\t"
                    + toString(line));
            System.out.println("Please ensure that for statement follows proper structure: " +
                    "<for_statement> -> for id = <iter> <block> end");
            error = true;
        }
    }

    private void getPrintStatement(TokenRecord[] line, String[] tokenCodes) {
        if (tokenCodes[0].equals("1011")) {
            if (tokenCodes[1].equals("0116")) {
                if (tokenCodes[tokenCodes.length - 1].equals("0117")) {
                    String ar_exp = getArithmeticExpression(Arrays.copyOfRange(line, 2, line.length - 1),
                            Arrays.copyOfRange(tokenCodes, 2, tokenCodes.length - 1));
                    if (!error) {
                        System.out.println("<print_statement> -> print ( <arithmetic_expression> )");
                    }
                    System.out.println(ar_exp);
                } else {
                    System.out.println("\nPARSING ERROR: Cannot resolve print statement on line " + lineNumber + ":\t"
                            + toString(line));
                    System.out.println("Missing right parentheses.");
                    System.out.println("Please ensure that print statement follows proper structure: " +
                            "<print_statement> -> print ( <arithmetic_expression> )");
                    error = true;
                }
            } else {
                System.out.println("\nPARSING ERROR: Cannot resolve print statement on line " + lineNumber + ":\t"
                        + toString(line));
                System.out.println("Missing left parentheses.");
                System.out.println("Please ensure that print statement follows proper structure: " +
                        "<print_statement> -> print ( <arithmetic_expression> )");
                error = true;
            }
        } else {
            System.out.println("\nPARSING ERROR: Cannot resolve print statement on line " + lineNumber + ":\t"
                    + toString(line));
            System.out.println("Please ensure that print statement follows proper structure: " +
                    "<print_statement> -> print ( <arithmetic_expression> )");
            error = true;
        }
    }

    private String getIter(TokenRecord[] line, String[] tokenCodes) {
        String output = "";
        if (Arrays.asList(tokenCodes).contains("0019")) {
            output = output + "<iter> -> <arithmetic_expression> : <arithmetic_expression>";
            output = output + "\n" + getArithmeticExpression(line[0], tokenCodes[0]);
            if (!error) {
                output = output + "\n" + getArithmeticExpression(line[line.length - 1], tokenCodes[tokenCodes.length - 1]);
            }
        } else {
            error = true;
            output = output + "\nPARSING ERROR: Cannot resolve iter on line " + lineNumber + ":\t"
                    + toString(line);
            output = output + "\nMissing Token ':'";
            output = output + "\nPlease ensure that <iter> follows proper structure: " +
                    "<iter> -> <arithmetic_expression> : <arithmetic_expression>";
        }
        return output;
    }

    private String getBooleanExpression(TokenRecord[] line, String[] tokenCodes) {
        String output = "";
        String[] rel_op = {"0110", "0111", "0112", "0113", "0114", "0115"};
        if (Arrays.asList(rel_op).contains(tokenCodes[0])) {
            output = output + "<boolean_expression> -> <relative_op> <arithmetic_expression> <arithmetic_expression>";
            output = output + "\n" + getRelativeOp();
            output = output + "\n" + getArithmeticExpression(line[1], tokenCodes[1]);
            if (!error) {
                output = output + "\n" + getArithmeticExpression(line[2], tokenCodes[2]);
            }
        } else {
            error = true;
            output = output + "\nPARSING ERROR: Cannot resolve boolean expression on line " + lineNumber + ":\t"
                    + toString(line);
            output = output + "\nToken '" + line[0].getLexeme() + "' should be a relative operator.";
            output = output + "\nPlease ensure that boolean expression follows proper structure: " +
                    "<boolean_expression> -> <relative_op> <arithmetic_expression> <arithmetic_expression>";
        }
        return output;
    }

    private String getRelativeOp() {
        return "<relative_op> -> le_operator | lt_operator | ge_operator | gt_operator | eq_operator | " +
                "ne_operator";
    }

    private String getArithmeticExpression(TokenRecord[] line, String[] tokenCodes) {
        //System.out.println("ar -> array");
        //System.out.println(Arrays.toString(tokenCodes));
        String output = "";
        output = output + "<arithmetic_expression> -> <id> | <literal_integer> | <binary_expression>";
        if (tokenCodes.length > 1) {
            output = output + "\n" + getBinaryExpression(line, tokenCodes);
        } else {
            output = getArithmeticExpression(line[0], tokenCodes[0]);
        }
        return output;
    }

    private String getArithmeticExpression(TokenRecord tr, String tokenCode) {
        //System.out.println("ar -> string");
        if (tokenCode.equals("3001") || tokenCode.equals("2001")) {
            return "<arithmetic_expression> -> <id> | <literal_integer> | <binary_expression>";
        } else {
            error = true;
            return "\nPARSING ERROR: Cannot resolve arithmetic expression on line " + lineNumber + ":\t"
                    + tr.getLexeme() + "\nToken '" + tr + "' should either be of type <id> or <literal_integer>.";
        }
    }

    private String getBinaryExpression(TokenRecord[] line, String[] tokenCodes) {
        String output = "";
        String[] arithmetic_op = {"0101", "0102", "0103", "0104", "0105", "0106", "0107"};
        if (Arrays.asList(arithmetic_op).contains(tokenCodes[0])) {
            output = output + "<binary_expression> -> <arithmetic_op> <arithmetic_expression> <arithmetic_expression>";
            output = output + "\n" + getArithmeticOp();
            output = output + "\n" + getArithmeticExpression(line[1], tokenCodes[1]);
            if (!error) {
                try {
                    output = output + "\n" + getArithmeticExpression(line[2], tokenCodes[2]);
                } catch (ArrayIndexOutOfBoundsException e){
                    output = output + "\n\nPARSING ERROR: Cannot resolve binary expression on line " + lineNumber + ":\t"
                            + toString(line);
                    output = output + "\nMissing token.";
                    output = output + "\nPlease ensure that binary expression follows proper structure: " +
                            "<binary_expression> -> <arithmetic_op> <arithmetic_expression> <arithmetic_expression>";
                    error = true;
                }
            }
        } else {
            output = output + "\n\nPARSING ERROR: Cannot resolve binary expression on line " + lineNumber + ":\t"
                    + toString(line);
            output = output + "\nToken '" + line[0].getLexeme() + "' should be an arithmetic operator.";
            output = output + "\nPlease ensure that binary expression follows proper structure: " +
                    "<binary_expression> -> <arithmetic_op> <arithmetic_expression> <arithmetic_expression>";
            error = true;
        }
        return output;
    }

    private String getArithmeticOp() {
        return "<arithmetic_op> -> add_operator | sub_operator | mul_operator | div_operator | " +
                "mod_operator | exp_operator | rev_div_operator";
    }

    private String toString(TokenRecord[] line) {
        String output = "";
        for (TokenRecord tr : line) {
            output = output + tr.getLexeme() + " ";
        }
        return output;
    }

    private String tokenAssignments(TokenRecord[] line) {
        String output = "";
        int i = 0;
        for (TokenRecord tr : line) {
            if (!tr.getTokenCode().equals("0116") && !tr.getTokenCode().equals("0117")) {
                if (i == 0) {
                    output = output + tr.getLexeme() + " -> " + tr.getTokenName();
                } else {
                    output = output + "\n" + tr.getLexeme() + " -> " + tr.getTokenName();
                }
            }
            i++;
        }
        return output;
    }
}
