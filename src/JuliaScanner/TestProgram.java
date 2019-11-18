package JuliaScanner;

import java.io.*;

/** Rosny Colas and Darius Tiglao
 * This class TestProgram simply runs the JuliaScanner
 * The main method instantiates a JuliaScanner object and scans the input file line by line
 */

public class TestProgram {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src\\InputFiles\\test4.jl");
        JuliaScanner jScan = new JuliaScanner(file);

        Bucket bucket = jScan.getBucket();

        File out = new File("src\\OutputFiles\\Token_Output4.txt");
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
    }
}