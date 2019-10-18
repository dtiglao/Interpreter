import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

/** Rosny Colas and Darius Tiglao
 * This class TestProgram simply runs the Scanner
 * Global Variables include an ArrayList of Keywords and an ArrayList that will hold TokenRecords
 * The main method instantiates a Scanner object and scans the input file line by line
 * These lines split by white spaces " " and the array of strings are fed into the TokenCheck method line by line
 */

public class TestProgram {
    public static String[] array = {"begin", "and", "if", "end", "function", "elseif", "else", "for", "while", "then"};
    public static List<String> keywords = Arrays.asList(array);
    public static int s_line = 0;
    public static List<TokenRecord> bucket = new ArrayList<>(200);

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src\\InputFiles\\basic_julia.txt");
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            s_line++;
            String[] tokens= line.split(" ");
            tokenCheck(tokens, bucket, s_line);
        }

        for (TokenRecord tr : bucket) {
            tr.printRecord();
        }
    }

    /** TokenCheck takes in an array of possible tokens, a tokenList to add the tokens to, and srcLine for recording purposes
     * @param tokens
     * @param tokenList
     * @param srcLine
     */
    public static void tokenCheck(String[] tokens, List<TokenRecord> tokenList, int srcLine) {
        TokenRecord token;
        boolean tokenAdded;
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
            else if (t.contains("\"")) {
                token = new TokenRecord(t, "Str_Lit", srcLine);
                tokenList.add(token);
                tokenAdded = true;
            }
            else if (t.matches("[-0-9.]+")) {
                if ((t.startsWith("-") && t.contains(".")) || (t.contains(".") && t.length() > 1)) {
                    token = new TokenRecord(t, "Float_Lit", srcLine);
                    tokenList.add(token);
                    tokenAdded = true;
                } else if ((t.startsWith("-") && t.length() > 1) || (!t.startsWith("-") && !t.contains("."))) {
                    token = new TokenRecord(t, "Int_Lit", srcLine);
                    tokenList.add(token);
                    tokenAdded = true;
                }
            } else if (t.matches("[A-Za-z0-9_()]+")) {
                if (keywords.contains(t)) {
                    token = new TokenRecord(t, "Keyword", srcLine);
                    tokenList.add(token);
                    tokenAdded = true;
                } else if (t.contains("()")) {
                    token = new TokenRecord(t, "function", srcLine);
                    tokenList.add(token);
                    tokenAdded = true;
                } else if (t.matches("[A-Za-z0-9_]+")){
                    token = new TokenRecord(t, "Identifier", srcLine);
                    // find what kind
                    tokenList.add(token);
                    tokenAdded = true;
                }
            } if (!tokenAdded){
                switch (t) {
                    case "=":
                        token = new TokenRecord(t, "Assign_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "+":
                        token = new TokenRecord(t, "Plus_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "-":
                        token = new TokenRecord(t, "Minus_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "*":
                        token = new TokenRecord(t, "Mult_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "/":
                        token = new TokenRecord(t,"Div_Lit", srcLine);
                        tokenList.add(token);
                        break;
                    case "^":
                        token = new TokenRecord(t, "Power_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case ">":
                        token = new TokenRecord(t, "GT_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case ">=":
                        token = new TokenRecord(t, "GE_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "<":
                        token = new TokenRecord(t, "LT_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "<=":
                        token = new TokenRecord(t, "LE_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "==":
                        token = new TokenRecord(t, "Eq_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "!=":
                        token = new TokenRecord(t, "NotEq_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "\\":
                        token = new TokenRecord(t, "RevDiv_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "%":
                        token = new TokenRecord(t, "Rem_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "+=":
                        token = new TokenRecord(t, "IncBy_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "-=":
                        token = new TokenRecord(t, "DecBy_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "(":
                        token = new TokenRecord(t, "LP_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case ")":
                        token = new TokenRecord(t, "RP_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "&&":
                        token = new TokenRecord(t, "And_Op", srcLine);
                        tokenList.add(token);
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
        }
    }

    /** Forgot string s.replaceAll() was a thing so we created a function to get rid of all the tabs since the tabs were
     * breaking the program...
     * @param s
     * @return s
     */
    public static String checkForTabs(String s) {
        if (s.contains("\t")) {
            return s.substring(s.indexOf("\t") + 1);
        }
        return s;
    }
}