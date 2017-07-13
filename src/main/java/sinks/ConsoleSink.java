package sinks;

import models.Log;
import server.Sink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ConsoleSink extends Sink
{
    private String FILENAME;
    BufferedWriter bw;
    FileWriter fw;

    public ConsoleSink(HashMap<String, String> config) {
        super(config);
//        FILENAME = config.get("file_location") ;
        FILENAME = "/Users/codedog29/Code/office/logger/fs.txt" ;
        init();
    }

    @Override
    public void init() {
    }

    @Override
    public void writeMessage(Log log) {
        System.out.println(log.toString());
    }
}
