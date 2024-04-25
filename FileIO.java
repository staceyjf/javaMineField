import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
    public static void main(String[] args) {
        String[] players = { "Stacey", "Tim", "Isaac" };
        boolean append = false;

        // create the file
        File file = new File("gameDetails.txt");
        append = file.exists(); // check if it exists using the exists method on File

        // write from file
        // create and write to a file using BufferedWritter
        try {
            // relative path will create the file in your workspace
            // absolute path will create it in a specific place
            // this method overwrites whats in the file each time
            // to append instead of right, we pass in an additional param to FileWriter
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            writer.write("Game details. \n");
            writer.write("Some updated details ");

            // use a forEach map
            for (String player : players) {
                writer.write("\n" + player);
            }
            writer.close();
        } catch (IOException error) {
            System.err.println("An error occurred while writing to the file."); // update the user
            error.printStackTrace();
        }

        // read from our file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // returns a string
            // to read all the lines we use a while loop
            // when line becomes null (eg there are no more lines), it becomes flase and
            // exists the while loop
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException error) {
            System.err.println("An error occurred while reading from the file."); // update the user
            error.printStackTrace();
        }
    }
}
