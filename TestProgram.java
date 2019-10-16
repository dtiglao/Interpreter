import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Stream;

public class TestProgram {
    public static String[] array = {"begin", "add", "in", "end"};
    public static List<String> keywords = Arrays.asList(array);
    public static int s_line = 0;
    public static List<TokenRecord> bucket = new ArrayList<>(1000);

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(new File(".").getAbsoluteFile());
        File file = new File("test.txt");
        Scanner scan = new Scanner(file);

        String[] array = {"begin", "add", "in", "end"};
        List<String> keywords = Arrays.asList(array);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            s_line++;
            String[] tokens= line.split(" ");
            tokenCheck(tokens, bucket, s_line);
        }
        for (int i = 0; i < bucket.size(); i++) {
        	bucket.get(i).printRecord();
        }
    }

    public static void tokenCheck(String[] tokens, List<TokenRecord> tokenList, int srcLine) {
        TokenRecord token;
        for (String t : tokens) {
            if (t.contains(";")) {
                t = t.substring(0, t.indexOf(";"));
            }
            if (t.contains("#") || t.contains(" ") || t == " ") return;
            else if (t.matches("[0-9.]+")) {
                if (t.contains(".")) {
                    token = new TokenRecord(t, "12", "Float_Lit", srcLine);
                    tokenList.add(token);
                } else {
                    token = new TokenRecord(t, "11", "Int_Lit", srcLine);
                    tokenList.add(token);
                }
            } else if (t.matches("[A-Za-z0-9_]+")) {
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
                    case "(":
                    	token = new TokenRecord(t, "06", "Left_Pare", srcLine);
                    	tokenList.add(token);
                    	break;
                    case ")":
                    	token = new TokenRecord(t, "07", "Right_Pare", srcLine);
                    	tokenList.add(token);
                    	break;
                    default:
                        char[] charArray = t.toCharArray();
                        String[] array = new String[charArray.length];
                        int i = 0;
                        for (char x : charArray) {
                            array[i] = Character.toString(x);
                        }
                        tokenCheck(array, tokenList, srcLine);

                }
            }
        }
    }
}