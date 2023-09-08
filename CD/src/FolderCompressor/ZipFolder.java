
package FolderCompressor;

import static FolderCompressor.folder.zipFolder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFolder {
    private String fileName, outputFilename;
    private long fileLen, outputFilelen;
    private String gSummary;

    public ZipFolder() {
        loadFile("", "");
    }

    public ZipFolder(String txt) {
        loadFile(txt);
    }

    //public ZipFolder(String txt, String txt2) {
        //loadFile(txt, txt2);
    //}

    public void loadFile(String txt) {
        fileName = txt;
        outputFilename = txt + ".gz";
        gSummary = "";
    }

    public void loadFile(String txt, String txt2) {
        fileName = txt;
        outputFilename = txt2;
        gSummary = "";
    }
    public ZipFolder(String sourceFolder, String outputZipFile) {
        loadFile (sourceFolder,outputZipFile);
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
     public String getSummary() {
        return gSummary;
    }
}
