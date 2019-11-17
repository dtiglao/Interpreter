package JuliaScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class JuliaScanner {
    private String[] array = {"begin", "and", "if", "end", "function", "elseif", "else", "for", "while", "then", "print"};
    private List<String> keywords;
    private Bucket bucket;
    private int block_num = 0;

    JuliaScanner(File file) throws FileNotFoundException {
        this.keywords = Arrays.asList(array);
        this.bucket = new Bucket();
        int s_line = 0;
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            s_line++;
            String[] tokens = line.split(" ");
            tokenCheck(tokens, bucket, s_line);
        }
    }

    Bucket getBucket() {
        return this.bucket;
    }
    int incBlock() { return block_num++; }
    int decBlock() { return block_num--; }
    int getBlockNum() { return block_num; }

    /** TokenCheck takes in an array of possible tokens, a tokenList to add the tokens to, and srcLine for recording purposes
     * @param tokens
     * @param tokenList
     * @param srcLine
     */
    private void tokenCheck(String[] tokens, Bucket tokenList, int srcLine) {
        TokenRecord token = new TokenRecord();
        boolean tokenAdded;
        TokenRecord[] lineArray = new TokenRecord[tokens.length];
        int index = 0;
        for (String t : tokens) {
            if (index == lineArray.length) { lineArray = Arrays.copyOf(lineArray, lineArray.length + 1); }
            tokenAdded = false;
            if (t.contains(";")) {
                t = t.substring(0, t.indexOf(";"));
            }
            while (t.contains("\t")) {
                t = checkForTabs(t);
            }
            // System.out.println(t);
            if (t.contains("#") || t == " ") return;
            else if (t.contains("(") && t.length() > 1) {
                int i = t.indexOf("(");
                if (i > 0) {
                    if (Character.isAlphabetic(t.charAt(i - 1))) {
                        token = new TokenRecord(t, "Function", srcLine);
                        
                        t = t.substring(i);
                        tokenAdded = true;
                        tokenCheck(t.split("[()]+"), tokenList, srcLine);
                    }
                }
            }
            else if (t.matches("[-0-9.]+")) {
                if ((t.startsWith("-") && t.contains(".")) || (t.contains(".") && t.length() > 1)) {
                    token = new TokenRecord(t, "<Float_Literal>", srcLine);
                    
                    tokenAdded = true;
                } else if ((t.startsWith("-") && t.length() > 1) || (!t.startsWith("-") && !t.contains("."))) {
                    token = new TokenRecord(t, "<Integer_Literal>", srcLine);
                    
                    tokenAdded = true;
                }
            } else if (t.matches("[A-Za-z0-9_]+")) {
                if (this.keywords.contains(t)) {
                    token = new TokenRecord(t, "Keyword", srcLine);
                    
                    tokenAdded = true;
                } else if (t.matches("[A-Za-z0-9_]+")) {
                    token = new TokenRecord(t, "id", srcLine);
                    // find what kind
                    
                    tokenAdded = true;
                }
            } else if (t.startsWith("\"")) {
                token = new TokenRecord(t, "<String_Literal>", srcLine);
                
                tokenAdded = true;
            } if (!tokenAdded){
                switch (t) {
                    case ":":
                        token = new TokenRecord(t, "<Iter_Operator>", srcLine);
                        break;
                    case "=":
                        token = new TokenRecord(t, "<Assignment_Operator>", srcLine);
                        
                        break;
                    case "+":
                        token = new TokenRecord(t, "<Plus_Operator>", srcLine);
                        
                        break;
                    case "-":
                        token = new TokenRecord(t, "<Minus_Operator>", srcLine);
                        
                        break;
                    case "*":
                        token = new TokenRecord(t, "<Multiplication_Operator>", srcLine);
                        
                        break;
                    case "/":
                        token = new TokenRecord(t,"<Division_Operator>", srcLine);
                        
                        break;
                    case "^":
                        token = new TokenRecord(t, "<Power_Operator>", srcLine);
                        
                        break;
                    case ">":
                        token = new TokenRecord(t, "<GT_Operator>", srcLine);
                        
                        break;
                    case ">=":
                        token = new TokenRecord(t, "<GE_Operator>", srcLine);
                        
                        break;
                    case "<":
                        token = new TokenRecord(t, "<LT_Operator>", srcLine);
                        
                        break;
                    case "<=":
                        token = new TokenRecord(t, "<LE_Operator>", srcLine);
                        
                        break;
                    case "==":
                        token = new TokenRecord(t, "<Eq_Operator>", srcLine);
                        
                        break;
                    case "!=":
                        token = new TokenRecord(t, "<NotEq_Operator>", srcLine);
                        
                        break;
                    case "\\":
                        token = new TokenRecord(t, "<ReverseDivision_Operator>", srcLine);
                        
                        break;
                    case "%":
                        token = new TokenRecord(t, "<Remainder_Operator>", srcLine);
                        
                        break;
                    case "+=":
                        token = new TokenRecord(t, "<IncBy_Operator>", srcLine);
                        
                        break;
                    case "-=":
                        token = new TokenRecord(t, "<DecBy_Operator>", srcLine);
                        
                        break;
                    case "(":
                        token = new TokenRecord(t, "<LP_Operator>", srcLine);
                        
                        break;
                    case ")":
                        token = new TokenRecord(t, "<RP_Operator>", srcLine);
                        
                        break;
                    case "&&":
                        token = new TokenRecord(t, "<And_Operator>", srcLine);
                        
                        break;
                    default:
                        char[] charArray = t.toCharArray();
                        String[] array = new String[charArray.length];
                        int i = 0;
                        for (char x : charArray) {
                            array[i] = Character.toString(x);
                            i++;
                        }
                        tokenCheck(array, tokenList, srcLine);
                }
            }
            int b = token.blockCheck();
            if (b == 0) decBlock();
            else if (b > 0) incBlock();
            token.setBlockNumber(block_num);

            lineArray[index] = token;
            index++;
        }
        tokenList.add(lineArray);
    }

    /** Forgot string s.replaceAll() was a thing so we created a function to get rid of all the tabs since the tabs were
     * breaking the program...
     * @param s
     * @return s
     */
    private String checkForTabs(String s) {
        if (s.contains("\t")) {
            return s.substring(s.indexOf("\t") + 1);
        }
        return s;
    }
}
