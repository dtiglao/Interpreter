package JuliaScanner;

import java.io.*;
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

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src\\InputFiles\\test4.jl");
        JuliaScanner jScan = new JuliaScanner(file);

        List<TokenRecord> bucket = jScan.getBucket();

        File out = new File("src\\OutputFiles\\Token_Output4.txt");
        try (BufferedWriter output = new BufferedWriter(new FileWriter(out))) {
            for (TokenRecord tr : bucket) {
                String str = tr.printRecord();
                output.write(str);
                output.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}