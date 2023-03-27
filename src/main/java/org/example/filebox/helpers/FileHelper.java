package org.example.filebox.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class FileHelper {

    private final Logger logger;

    public FileHelper(Logger logger) {
        this.logger = logger;
    }

    public File createFile(String name, int sizeInMb) throws IOException {
        String projectPath = System.getProperty("user.dir");
        File file = new File(projectPath + "/" + name);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] data = new byte[sizeInMb * 1024 * 1024];
        fos.write(data);
        fos.close();
        logger.info(String.format("File %s created with size: %s", name, sizeInMb));
        return file;
    }
}
