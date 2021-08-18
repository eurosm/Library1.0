import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;


public class FileUtils {

    String title;
    int index;


    public static File createFile(String path) {
        return new File(path);
    }

    public static boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static File openFile(String path) {
        return new File(path);
    }

}
