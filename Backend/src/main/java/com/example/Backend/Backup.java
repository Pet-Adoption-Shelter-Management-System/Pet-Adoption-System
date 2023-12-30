package com.example.Backend;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class Backup {
    public static void Backupdbtosql() {
        try {

            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            CodeSource codeSource = BackendApplication.class.getProtectionDomain().getCodeSource();
//            System.out.println(codeSource.getLocation().toURI().getPath());
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
//            System.out.println(jarDir);

            /*NOTE: Creating Database Constraints*/
            String dbName = "petadoptiondb";
            String dbUser = "root";
            String dbPass = "MHD#2792#mhd";

            /*NOTE: Creating Path Constraints for folder saving*/
            /*NOTE: Here the backup folder is created for saving inside it*/
            String folderPath = jarDir + "\\backup";

            /*NOTE: Creating Folder if it does not exist*/
            File f1 = new File(folderPath);
            f1.mkdir();

            /*NOTE: Creating Path Constraints for backup saving*/
            /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
            String savePath = jarDir + "\\backup\\backup.sql";
//            savePath = savePath.replaceAll("\\\\", "/");

            String mysqlDumpPath = "C:\\Program Files\\MySQL\\MySQL Server 8.1\\bin\\mysqldump";
            String[] command = {mysqlDumpPath, "-u", dbUser, "--password=" + dbPass, dbName};

            /* NOTE: Executing the command and redirecting the output to the file using ProcessBuilder */
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectOutput(new File(savePath));
            Process runtimeProcess = processBuilder.start();
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Error Stream: " + new String(runtimeProcess.getErrorStream().readAllBytes()));
                System.out.println("Backup Failure");
            }

        } catch (URISyntaxException | IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Backupdbtosql();
    }
}