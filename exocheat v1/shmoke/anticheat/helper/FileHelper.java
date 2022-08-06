package club.shmoke.anticheat.helper;

import java.util.*;
import java.io.*;

public class FileHelper
{
    public void writeData(String name, ArrayList<String> data) {
        try {
            String path = "data/" + name + ".json";
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            for (String d : data)
                writer.println(d);
            writer.close();
        }
        catch (FileNotFoundException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
}
