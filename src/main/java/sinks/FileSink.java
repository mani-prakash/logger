package sinks;

import models.Log;
import server.Sink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileSink extends Sink
{
    BufferedWriter bw;
    FileWriter fw;

    public FileSink(HashMap<String, String> config) {
        super(config);
        init();
    }

    @Override
    public void init() {

        try {

            File file = new File(getProperty("FILE_LOCATION"));

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeMessage(Log log) {

        while (true) {
            try {
                bw.write(log.toString() + "\n");
                bw.flush();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception writing message in sinks.FileSink: " + e.getMessage());
                init();
            }
        }
    }
}
