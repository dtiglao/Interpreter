package JuliaScanner;

import java.io.*;

/** Rosny Colas and Darius Tiglao
 * This class TestProgram simply runs the JuliaScanner
 * The main method instantiates a JuliaScanner object and scans the input file line by line
 */

public class TestProgram {

    public static void main(String[] args) throws IOException {
        String[] files = {"test1", "test2", "test3", "test4"};
        String fileName = files[1];
        File file = new File("src\\InputFiles\\" + fileName + ".jl");
        JuliaScanner jScan = new JuliaScanner(file);

        Bucket bucket = jScan.getBucket();

        File out = new File("src\\OutputFiles\\" + fileName + "_Token_Output.txt");
        try (BufferedWriter output = new BufferedWriter(new FileWriter(out))) {
            for (TokenRecord[] line : bucket.getBucket()) {
                for (TokenRecord tr : line) {
                    String str = tr.printRecord();
                    output.write(str);
                    output.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JuliaParser jParse = new JuliaParser(bucket);

        File p_out = new File("src\\OutputFiles\\" + fileName + "_Parser_Output.txt");
        try (BufferedWriter p_output = new BufferedWriter(new FileWriter(p_out))) {
            p_output.write(jParse.parsed_output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JuliaInterpreter jInterpreter = new JuliaInterpreter(jParse.parsed_output);
    }
}