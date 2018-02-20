package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class FileTool {

    public static String readContent(File f) throws IOException{
        StringBuilder b = new StringBuilder();
        InputStream s = new FileInputStream(f);
        while(true){
            int i = s.read();
            if(i < 0) break;
            b.append((char) i);
        }
        s.close();
        return b.toString();
    }

    public static void writeContent(File f, String content) throws IOException{
        FileWriter writer = new FileWriter(f);
        writer.write(content);
        writer.close();
    }

}
