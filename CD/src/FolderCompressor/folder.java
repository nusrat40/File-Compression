
package FolderCompressor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class folder {
    
     public static void main(String[] args) {
        String sourceFolder = "C:\\Users\\Dell\\Downloads\\New folder";
        String outputZipFile = "C:\\Users\\Dell\\Downloads\\A.zip";
        zipFolder(sourceFolder, outputZipFile);
    }

    public static void zipFolder(String sourceFolder, String outputZipFile) {
        try (FileOutputStream fos = new FileOutputStream(outputZipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            File folder = new File(sourceFolder);
            zipFolder(folder, folder.getName(), zos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFolder(File folder, String path, ZipOutputStream zos) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(file, path + File.separator + file.getName(), zos);
            } else {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(path + File.separator + file.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                }
            }
        }
    }
}
    

