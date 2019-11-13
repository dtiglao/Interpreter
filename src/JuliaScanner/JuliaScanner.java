package JuliaScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class JuliaScanner {
    private String[] array = {"begin", "and", "if", "end", "function", "elseif", "else", "for", "while", "then", "print"};
    private List<String> keywords = Arrays.asList(array);
    private List<TokenRecord> bucket = new ArrayList<>(200);
    private int block_num = 0;

    JuliaScanner(File file) throws FileNotFoundException {
        this.keywords = keywords;
        this.bucket = bucket;
        int s_line = 0;
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            s_line++;
            String[] tokens = line.split(" ");
            tokenCheck(tokens, bucket, s_line);
        }
    }

    JuliaScanner(String str) {
        this.keywords = keywords;
        int s_line = 0;
        Scanner scan = new Scanner(str);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            s_line++;
            String[] tokens = line.split(" ");
            tokenCheck(tokens, bucket, s_line);
        }
    }

    List<TokenRecord> getBucket() {
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
    private void tokenCheck(String[] tokens, List<TokenRecord> tokenList, int srcLine) {
        TokenRecord token = new TokenRecord();
        boolean tokenAdded;
        boolean space = false;
        for (String t : tokens) {
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
                    token = new TokenRecord(t, "Float_Lit", srcLine);
                    
                    tokenAdded = true;
                } else if ((t.startsWith("-") && t.length() > 1) || (!t.startsWith("-") && !t.contains("."))) {
                    token = new TokenRecord(t, "Int_Lit", srcLine);
                    
                    tokenAdded = true;
                }
            } else if (t.matches("[A-Za-z0-9_]+")) {
                if (this.keywords.contains(t)) {
                    token = new TokenRecord(t, "Keyword", srcLine);
                    
                    tokenAdded = true;
                } else if (t.matches("[A-Za-z0-9_]+")) {
                    token = new TokenRecord(t, "Identifier", srcLine);
                    // find what kind
                    
                    tokenAdded = true;
                }
            } else if (t.startsWith("\"")) {
                token = new TokenRecord(t, "Str_Lit", srcLine);
                
                tokenAdded = true;
            } if (!tokenAdded){
                switch (t) {
                    case " ":
                        space = true;
                        break;
                    case ":":
                        token = new TokenRecord(t, "Iter_Op", srcLine);
                        break;
                    case "=":
                        token = new TokenRecord(t, "Assign_Op", srcLine);
                        
                        break;
                    case "+":
                        token = new TokenRecord(t, "Plus_Op", srcLine);
                        
                        break;
                    case "-":
                        token = new TokenRecord(t, "Minus_Op", srcLine);
                        
                        break;
                    case "*":
                        token = new TokenRecord(t, "Mult_Op", srcLine);
                        
                        break;
                    case "/":
                        token = new TokenRecord(t,"Div_Lit", srcLine);
                        
                        break;
                    case "^":
                        token = new TokenRecord(t, "Power_Op", srcLine);
                        
                        break;
                    case ">":
                        token = new TokenRecord(t, "GT_Op", srcLine);
                        
                        break;
                    case ">=":
                        token = new TokenRecord(t, "GE_Op", srcLine);
                        
                        break;
                    case "<":
                        token = new TokenRecord(t, "LT_Op", srcLine);
                        
                        break;
                    case "<=":
                        token = new TokenRecord(t, "LE_Op", srcLine);
                        
                        break;
                    case "==":
                        token = new TokenRecord(t, "Eq_Op", srcLine);
                        
                        break;
                    case "!=":
                        token = new TokenRecord(t, "NotEq_Op", srcLine);
                        
                        break;
                    case "\\":
                        token = new TokenRecord(t, "RevDiv_Op", srcLine);
                        
                        break;
                    case "%":
                        token = new TokenRecord(t, "Rem_Op", srcLine);
                        
                        break;
                    case "+=":
                        token = new TokenRecord(t, "IncBy_Op", srcLine);
                        
                        break;
                    case "-=":
                        token = new TokenRecord(t, "DecBy_Op", srcLine);
                        
                        break;
                    case "(":
                        token = new TokenRecord(t, "LP_Op", srcLine);
                        
                        break;
                    case ")":
                        token = new TokenRecord(t, "RP_Op", srcLine);
                        
                        break;
                    case "&&":
                        token = new TokenRecord(t, "And_Op", srcLine);
                        
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

            if (!space) { tokenList.add(token); }
        }
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
