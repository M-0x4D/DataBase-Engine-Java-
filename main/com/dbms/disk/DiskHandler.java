package main.com.dbms.disk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskHandler {
    
    public boolean store(byte[] data){
         // Specify the path where the file will be created
        String filePath = "8KBFile.txt";
        // 8 KB = 8192 bytes
        int fileSizeInBytes = 8192;

        // Create a file object
        File file = new File(filePath);

        // Create the file and write random data
        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Generate random bytes to write into the file
            byte[] block = data;
            // new Random().nextBytes(block);  // Fill the array with random bytes

            // Write the data to the file
            fos.write(data);

            System.out.println("8KB file created successfully at " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            e.getStackTrace();
            return false;
        }
    }
}
