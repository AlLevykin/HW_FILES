package netology.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

public class ZipFilesCommand implements Command {

    private final String zipFileName;
    private final String[] files;

    public ZipFilesCommand(String zipFileName, String[] files) {
        this.zipFileName = zipFileName;
        this.files = files;
    }

    @Override
    public void execute() throws Exception {
        File zipFile = new File(this.zipFileName);
        if (zipFile.exists()) {
            zipFile.delete();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(this.zipFileName);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        for (String fileName : files) {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            if (fileInputStream.available() > 0) {
                zipOutputStream.putNextEntry(new java.util.zip.ZipEntry(fileName));
                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        }
        zipOutputStream.close();

        for (String fileName : files) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
