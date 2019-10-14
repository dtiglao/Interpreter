import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestProgram {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(new File(".").getAbsoluteFile());
        File file = new File("src\\test.txt");
        Scanner scan = new Scanner(file);

        int s_line = 0;
        while (scan.hasNextLine()) {
            s_line++;
            String line = scan.nextLine();
            System.out.println(line);
            System.out.println("Src line is: " + s_line);
        }
    }
}
