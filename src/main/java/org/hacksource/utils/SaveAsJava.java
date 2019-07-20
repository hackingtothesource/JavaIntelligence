package org.hacksource.utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 周思成 on  2019/7/14 21:46
 */

public class SaveAsJava {

    public static boolean Save(String destPath,String content){
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(destPath);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            return false;
            }
        }
        return true;
    }
}
