package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class TestProgram {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src\\InputFiles\\basic_julia.txt");
        InterpreterScanner test = new InterpreterScanner(file);
        test.printTokens();
        
    }

    
}