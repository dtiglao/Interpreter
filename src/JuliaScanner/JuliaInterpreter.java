package JuliaScanner;

import java.io.File;
import java.util.*;

class JuliaInterpreter {
    private Map<String, Integer> tokenDictionary;

    JuliaInterpreter(String p_output) {
        tokenDictionary = new HashMap<String, Integer>();
        Scanner scan = new Scanner(p_output);
        boolean tokenFound = false;
        boolean afterEquals = false;
        boolean print = false;
        String k = "";
        ArrayList<String> v = new ArrayList<>();
        int v_i = 0;

        if (p_output.contains("ERROR:")) {
            findError(scan);
            return;
        } else {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineArray = line.split(" ");
                //for (String l: lineArray) System.out.println(l);
                if (line.indexOf("<") != 0) {
                    tokenFound = true;
                } else if (!tokenFound && lineArray[0].equals("<print_statement>")) {
                    print = true;
                    afterEquals = true;
                } else {
                    tokenFound = false;
                }
                if (tokenFound) {
                    //context(scan, lineArray);
                    String t = lineArray[0];
                    if (t.equals("=")) {
                        afterEquals = true;
                    } else if (!afterEquals) {
                        k = t;
                    } else if (afterEquals) {
                        v.add(t);
                        v_i++;
                    }
                } else if (!tokenFound && v_i > 0) {
                    //System.out.println(tokenDictionary.toString());
                    if (print) k = "print";
                    keymapping(k, v);
                    print = false;
                    afterEquals = false;
                    v_i = 0;
                    k = "";
                    v = new ArrayList<String>();
                }
                //TODO NEED TO RESET THE K AND V ARRAYS (OR LISTS)
            }
            if (v.size() > 0 && !k.equals("")) {
                //System.out.println("doing this");
                keymapping(k, v);
            }
        }
    }

    void findError(Scanner scan) {
        boolean print = false;
        while (scan.hasNextLine()) {
           String line = scan.nextLine();
           if (line.contains("ERROR")) {
               print = true;
           }
           if (print) {
               System.out.println(line);
           }
        }
    }


    void keymapping(String key, ArrayList<String> values) {
        if (values.size() == 0) return;
        //System.out.println(values);
        String[] arithmetic_ops = {"+", "-", "/", "*", "%", "^"};
        //int[] arithmetic_bool = {0, 1, 2, 3, 4, 5};
        String curr = "";
        String next = "";

        int total = 0;
        int index = 0;
        int bool = -1;

        for (String v: values) {
            if (v.equals(null)) break;
            if (Arrays.asList(arithmetic_ops).contains(v)) {
                index = 0;
                switch (v) {
                    case "+":
                        bool = 0;
                        break;
                    case "-":
                        bool = 1;
                        break;
                    case "/":
                        bool = 2;
                        break;
                    case "*":
                        bool = 3;
                        break;
                    case "%":
                        bool = 4;
                        break;
                    case "^":
                        bool = 5;
                        break;
                    default:
                        bool = -1;
                        break;
                }

            } else if (bool < 0) {
                if (v.matches("[0-9]+")) {
                    total = Integer.parseInt(v);
                } else if (v.matches("[A-Za-z]+")) {
                    if (tokenDictionary.containsKey(v)) {
                        total = tokenDictionary.get(v);
                    }
                }
            } else {
                if (index == 0) {
                    curr = v;
                } else if (index == 1) {
                    next = v;
                }
                index++;
            }
        }

        if (bool > -1) total = compute(bool, curr, next);
        if (key.equals("print")) {
            System.out.println(total);
        } else {
            if (tokenDictionary.containsKey(key)) tokenDictionary.replace(key, total);
            else tokenDictionary.put(key, total);
        }

    }

    int compute(int op, String first, String second) {
        int a = 0;
        int b = 0;

        if (first.matches("[A-Za-z]+")) a = tokenDictionary.get(first);
        else a = Integer.parseInt(first);
        if (second.matches("[A-za-z]+")) b = tokenDictionary.get(second);
        else b = Integer.parseInt(second);

        switch (op) {
            case 0:
                return a + b;
            case 1:
                return a - b;
            case 2:
                return a / b;
            case 3:
                return a * b;
            case 4:
                return a % b;
            case 5:
                return a ^ b;
        }

        return -12345;
    }

}
