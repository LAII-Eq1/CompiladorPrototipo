package itc.automatas2.gui.model;

import java.io.*;

public class ArchivoModel {
    private File file;
    private String tmpPath;

    public ArchivoModel(File file) {
        this.file = file;
        this.tmpPath = file.getPath() + ".tmp";
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile() {
        return file;
    }

    public String readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            reader.lines()
                    .map(s -> s + "\n")
                    .forEach(sb::append);
            if (sb.length() > 1)
                sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void write(String text) {
        try {
            File tmp = new File(tmpPath);
            tmp.createNewFile();
            BufferedWriter writer = new BufferedWriter(new PrintWriter(tmp));
            writer.write(text);
            writer.close();
            file.delete();
            tmp.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
