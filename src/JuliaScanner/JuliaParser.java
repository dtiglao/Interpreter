package JuliaScanner;

import java.util.Arrays;


class JuliaParser {
    private int lineNumber = 1;
    private boolean error = false;

    JuliaParser(Bucket bucket) {
        TokenRecord[] temp = bucket.grab(bucket.size() - 1);
        TokenRecord last = temp[temp.length - 1];
        if (last.getBlockNumber() > -1) {
            System.out.println("Parsing Error: Function not ended properly. Please include 'end' keyword!");
            return;
        } else if (last.getBlockNumber() < -1) {
            System.out.println("Parsing Error: Additional 'end' found. Please remove 'end' keyword");
            return;
        }
        for (TokenRecord[] line : bucket.getBucket()) {
            if (!error) {
                // CREATE TEMP VAR TO WORK WITH
                String[] tempLine = new String[line.length];
                for (int i = 0; i < line.length; i++) {
                    tempLine[i] = line[i].getTokenCode();
                }
                // NOW ONTO LOGIC

                // Don't need to run else through the parser since it's attached to if
                //System.out.println(Arrays.toString(tempLine) + ":");
                if (tempLine[0].equals("1009") || tempLine[0].equals("1002")) {
                }
                else if (Arrays.asList(tempLine).contains("1003")) {
                    getFunction(tempLine);
                } else {
                    getStatement(tempLine);
                }
                lineNumber++;
            } else {
                // if an error is found, then the algorithm should close out
                return;
            }
            //System.out.println();
        }

        // create class Bucket which changes JuliaScanner.bucket into 'bucket' of arrays that store the token records by line ofc
        // so each index of Bucket is an array which is just the token records found on that line
        // iterate line by line
        // local variable (for debugging) that records index/current line number in case error occurs
        // keep in mind tokenrecord.blocknumber to check for correct amount of 'end's
        //
    }

    private void getFunction(String[] tokenCodes) {
        if (!error) {
            // function should match template {function, id, (, )}
            String[] funcTemplate = {"1003", "3001", "0116", "0117"};
            if (Arrays.equals(tokenCodes, funcTemplate)) {
                System.out.println("<program> -> function id() <block> end");
            } else {
                System.out.println("Parsing Error: Cannot resolve function on line: " + lineNumber);
                error = true;
            }
        }
    }

    private void getStatement(String[] tokenCodes) {
        if (!error) {
            if (Arrays.asList(tokenCodes).contains("1005")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getForStatement(tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("1006")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getWhileStatement(tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("1007")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getIfStatement(tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("1011")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getPrintStatement(tokenCodes);
            } else if (Arrays.asList(tokenCodes).contains("0100")) {
                System.out.println("<statement> -> <if_statement> | <assignment_statement> | <while_statement> | " +
                        "<print_statement> | <for_statement>");
                getAssignmentStatement(tokenCodes);
            } else {
                System.out.println("Parsing Error: Cannot resolve statement on line: " + lineNumber);
                error = true;
            }
        }
    }

    private void getIfStatement(String[] tokenCodes) {
        if (tokenCodes[0].equals("1007")) {
            String[] boolExp = Arrays.copyOfRange(tokenCodes, 1, tokenCodes.length);
            String bool = getBooleanExpression(boolExp);
            if (!error) {
                System.out.println("<if_statement> -> if <boolean_expression> <block> else <block> end");
                System.out.println(bool);
            }
        } else {
            System.out.println("Parsing Error: Cannot resolve if statement on line: " + lineNumber);
            error = true;
        }
    }

    private void getWhileStatement(String[] tokenCodes) {
        if (tokenCodes[0].equals("1006")) {
            String[] boolExp = Arrays.copyOfRange(tokenCodes, 1, tokenCodes.length);
            String bool = getBooleanExpression(boolExp);
            if (!error) {
                System.out.println("<while_statement> -> while <boolean_expression> <block> end");
                System.out.println(bool);
            }
        } else {
            System.out.println("Parsing Error: Cannot resolve while statement on line: " + lineNumber);
            error = true;
        }
    }

    private void getAssignmentStatement(String[] tokenCodes) {
        if (tokenCodes[0].equals("3001")) {
            if (tokenCodes[1].equals("0100")) {
                String ar_exp = getArithmeticExpression(Arrays.copyOfRange(tokenCodes, 2, tokenCodes.length));
                if (!error) {
                    System.out.println("<assignment_statement> -> id <assignment_operator> <arithmetic_expression>");
                    System.out.println(ar_exp);
                }
            } else {
                System.out.println("Parsing Error: Cannot resolve assignment statement on line: " + lineNumber);
            }
        } else {
            System.out.println("Parsing Error: Cannot resolve assignment statement on line: " + lineNumber);
        }
    }

    private void getForStatement(String[] tokenCodes) {
        if (tokenCodes[0].equals("1005")) {
            if (tokenCodes[2].equals("0100")) {
                String[] iterCodes = Arrays.copyOfRange(tokenCodes, 3, tokenCodes.length);
                String iter = getIter(iterCodes);
                if (!error) {
                    System.out.println("<for_statement> -> for id = <iter> <block> end");
                    System.out.println(iter);
                }
            }
        } else {
            System.out.println("Parsing Error: Cannot resolve for statement on line: " + lineNumber);
            error = true;
        }
    }

    private void getPrintStatement(String[] tokenCodes) {
        if (tokenCodes[0].equals("1011")) {
            if (tokenCodes[1].equals("0116")) {
                if (tokenCodes[tokenCodes.length - 1].equals("0117")) {
                    String ar_exp = getArithmeticExpression(Arrays.copyOfRange(tokenCodes, 2, tokenCodes.length - 1));
                    if (!error) {
                        System.out.println("<print_statement> -> print ( <arithmetic_expression> )");
                        System.out.println(ar_exp);
                    } else {
                        System.out.println("Print error");
                    }
                } else {
                    System.out.println("Parsing Error: Cannot resolve print statement on line: " + lineNumber);
                    error = true;
                }
            } else {
                System.out.println("Parsing Error: Cannot resolve print statement on line: " + lineNumber);
                error = true;
            }
        } else {
            System.out.println("Parsing Error: Cannot resolve print statement on line: " + lineNumber);
            error = true;
        }
    }

    private String getIter(String[] tokenCodes) {
        String output = "";
        if (Arrays.asList(tokenCodes).contains("0019")) {
            output = output + "<iter> -> <arithmetic_expression> : <arithmetic_expression>";
            output = output + "\n" + getArithmeticExpression(tokenCodes[0]);
            if (!error) {
                output = output + "\n" + getArithmeticExpression(tokenCodes[tokenCodes.length - 1]);
            }
        } else {
            error = true;
            output = output + "Parsing Error: Cannot resolve iter on line: " + lineNumber;
        }
        return output;
    }

    private String getBooleanExpression(String[] tokenCodes) {
        String output = "";
        String[] rel_op = {"0110", "0111", "0112", "0113", "0114", "0115"};
        if (Arrays.asList(rel_op).contains(tokenCodes[0])) {
            output = output + "<boolean_expression> -> <relative_op> <arithmetic_expression> <arithmetic_expression>";
            output = output + "\n" + getRelativeOp();
            output = output + "\n" + getArithmeticExpression(tokenCodes[1]);
            if (!error) {
                output = output + "\n" + getArithmeticExpression(tokenCodes[2]);
            }
        } else {
            error = true;
            output = output + "Parsing Error: Cannot resolve boolean expression on line: " + lineNumber;
        }
        return output;
    }

    private String getRelativeOp() {
        return "<relative_op> -> le_operator | lt_operator | ge_operator | gt_operator | eq_operator | " +
                "ne_operator";
    }

    private String getArithmeticExpression(String[] tokenCodes) {
        //System.out.println("ar -> array");
        //System.out.println(Arrays.toString(tokenCodes));
        String output = "";
        output = output + "<arithmetic_expression> -> <id> | <literal_integer> | <binary_expression>";
        if (tokenCodes.length > 1) {
            output = output + "\n" + getBinaryExpression(tokenCodes);
        }

        return output;
    }

    private String getArithmeticExpression(String tokenCode) {
        //System.out.println("ar -> string");
        if (tokenCode.equals("3001") || tokenCode.equals("2001")) {
            return "<arithmetic_expression> -> <id> | <literal_integer> | <binary_expression>";
        } else {
            error = true;
            return "Parsing Error: Cannot resolve arithmetic expression on line: " + lineNumber;
        }
    }

    private String getBinaryExpression(String[] tokenCodes) {
        String output = "";
        String[] arithmetic_op = {"0101", "0102", "0103", "0104", "0105", "0106", "0107"};
        if (Arrays.asList(arithmetic_op).contains(tokenCodes[0])) {
            output = output + "<binary_expression> -> <arithmetic_op> <arithmetic_expression> <arithmetic_expression>";
            output = output + "\n" + getArithmeticOp();
            output = output + "\n" + getArithmeticExpression(tokenCodes[1]);
            if (!error) {
                output = output + "\n" + getArithmeticExpression(tokenCodes[2]);
            }
        } else {
            output = output + "Parsing Error: Cannot resolve binary expression on line: " + lineNumber;
            error = true;
        }
        return output;
    }

    private String getArithmeticOp() {
        return "<arithmetic_op> -> add_operator | sub_operator | mul_operator | div_operator | " +
                "mod_operator | exp_operator | rev_div_operator";
    }
}
