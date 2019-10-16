import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Stream;

public class TestProgram {
    public static String[] array = {"begin", "and", "if", "end", "function", "elseif", "else", "for", "while", "then", "print"};
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

    public static void tokenCheck(String[] tokens, List<TokenRecord> tokenList, int srcLine) {
        TokenRecord token;
        for (String t : tokens) {
            if (t.contains(";")) {
                t = t.substring(0, t.indexOf(";"));
            }
            while (t.contains("\t")) {
                t = checkForTabs(t);
            }
            // System.out.println(t);
            if (t.contains("#") || t == " ") return;
            else if (t.contains("\"")) {
                token = new TokenRecord(t, "10", "String_Lit", srcLine);
                tokenList.add(token);
            }
            else if (t.matches("[0-9.]+")) {
                if (t.contains(".")) {
                    token = new TokenRecord(t, "12", "Float_Lit", srcLine);
                    tokenList.add(token);
                } else {
                    token = new TokenRecord(t, "11", "Int_Lit", srcLine);
                    tokenList.add(token);
                }
            } else if (t.matches("[A-Za-z0-9_()]+")) {
                if (t.contains("()")) {
                    token = new TokenRecord(t, "30", "function", srcLine);
                    tokenList.add(token);
                }
            }
            else if (t.matches("[A-Za-z0-9_]+")) {
                if (keywords.contains(t)) {
                    token = new TokenRecord(t, "13", "Keyword", srcLine);
                    tokenList.add(token);
                } else {
                    token = new TokenRecord(t, "14", "Identifier", srcLine);
                    // find what kind
                    tokenList.add(token);
                }
            } else {
                switch (t) {
                    case "=":
                        token = new TokenRecord(t, "00", "Assign_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "+":
                        token = new TokenRecord(t, "01", "Plus_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "-":
                        token = new TokenRecord(t, "02", "Minus_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "*":
                        token = new TokenRecord(t, "03", "Mult_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "/":
                        token = new TokenRecord(t, "04", "Div_Lit", srcLine);
                        tokenList.add(token);
                        break;
                    case "^":
                        token = new TokenRecord(t, "05", "Power_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case ">":
                        token = new TokenRecord(t, "06", "GT_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case ">=":
                        token = new TokenRecord(t, "07", "GE_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "<":
                        token = new TokenRecord(t, "08", "LT_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "<=":
                        token = new TokenRecord(t, "09", "LE_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "==":
                        token = new TokenRecord(t, "010", "EQ_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "!=":
                        token = new TokenRecord(t, "011", "NE_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "\\":
                        token = new TokenRecord(t, "012", "Rev_Div_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "%":
                        token = new TokenRecord(t, "013", "Rem_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "+=":
                        token = new TokenRecord(t, "014", "Inc_By_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "-=":
                        token = new TokenRecord(t, "015", "Dec_By_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case "(":
                        token = new TokenRecord(t, "016", "LP_Op", srcLine);
                        tokenList.add(token);
                        break;
                    case ")":
                        token = new TokenRecord(t, "017", "RP_Op", srcLine);
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

    public static String checkForTabs(String s) {
        if (s.contains("\t")) {
            return s.substring(s.indexOf("\t") + 1);
        }
        return s;
    }
}