package tag.sources.checkmebot.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

@Service
public class GzipFileService {
    private static HashMap<String, Integer> attempts = new HashMap<>();

    public void gzipFile(File file, Long chatId) throws IllegalStateException {
        if (attempts.getOrDefault(chatId.toString(), 0) > 2) {
            throw new IllegalStateException("Слишком много файлов в очереди");
        }
        attempts.put(chatId.toString(), attempts.getOrDefault(chatId.toString(), 0) + 1);
        String inputFile = "input.txt";
        String outputFile = String.format("output%s.txt.gz", chatId);

        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                gzipOutputStream.write(buffer, 0, length);
            }
            attempts.put(chatId.toString(), attempts.getOrDefault(chatId.toString(), 1) - 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
