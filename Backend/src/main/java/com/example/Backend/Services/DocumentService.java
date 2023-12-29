package com.example.Backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class DocumentService {

    public String[][] saveDocs(MultipartFile[] docs, Long id) throws Exception {
        StringBuilder petDir = new StringBuilder(File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "static" + File.separator + "pets" + File.separator + id);

        String directoryPath = new File(".").getCanonicalPath() + petDir;
        // Create the directory if it does not exist
        createDirectoryIfNotExists(directoryPath);

        // Recreate the directory to clean it before saving the file
        recreateDirectory(directoryPath);
        String[][] links = new String[docs.length][2];
        for (int i = 0; i < docs.length; i++) {
            if (docs[i].isEmpty()) {
                throw new IOException("Cannot upload empty document");
            }
            String docLink = directoryPath + File.separator + docs[i].getOriginalFilename();
            System.out.println(docLink);
            StringBuilder linkToSave = new StringBuilder(petDir);
            linkToSave.append(File.separator).append(docs[i].getOriginalFilename());
            try {
                Files.write(Paths.get(docLink), docs[i].getBytes());
                links[i][0] = linkToSave.toString();
                links[i][1] = docs[i].getContentType();
            } catch (Exception e) {
                throw new IOException("Could not save the document: " + docs[i].getOriginalFilename());
            }
        }
        return links;
    }

    private void createDirectoryIfNotExists(String directoryPath) throws Exception {
        Path directory = Path.of(directoryPath);
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }
    }

    private void recreateDirectory(String directoryPath) throws Exception {
        Path directory = Path.of(directoryPath);
        if (Files.exists(directory)) {
            // Delete the directory and its contents
            Files.walk(directory, FileVisitOption.FOLLOW_LINKS)
                    .sorted((a, b) -> b.compareTo(a)) // reverse order for deleting from bottom to top
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (Exception e) {
                            e.printStackTrace(); // Handle deletion errors as needed
                        }
                    });
        }
        // Recreate the directory
        Files.createDirectories(directory);
    }

//    public void deleteDoc(String imageLink) {
//        try {
//            String path = new File("..").getCanonicalPath() + "/FrontEnd/src/assets";
//            File file = new File(path + imageLink);
//            if (file.delete()) {
//                System.out.println("File deleted successfully");
//            } else {
//                System.out.println("Failed to delete the file");
//            }
//        } catch (IOException e) {
//            System.out.println("Could not delete image: " + imageLink);
//        }
//    }
}