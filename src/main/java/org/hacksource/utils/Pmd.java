package org.hacksource.utils;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.err;

/**
 * Created by 周思成 on  2019/7/14 21:26
 */

public class Pmd {

public static List<String> Exc(String pmdPath,String path){
    List<String> problemStrings = new ArrayList<>();
    Runtime run = Runtime.getRuntime();

    try {
        String response = CMDUtil.excuteCMDCommand(pmdPath + "\\pmd.bat -d " +path+
                " -shortnames -R java-basic,java-design 2>nul");
        //如果没有冒号，说明未检测出错误，直接返回
        if (!response.contains(":")) {
            err.println("no pmd problem found:"+response);
            return problemStrings;
        }
        String[] strings = response.split(":");

        for (int i = 0; i <strings.length ; i++) {

            if (!strings[i].contains("test.java")) {

                problemStrings.add("(line "+strings[i]+",col 9)-(line "+strings[i]+",col 45):pmd-"
                        +strings[i+1].replace("\n","").replace("\t",""));
                i = i + 1;
            }
        }


    } catch (Exception e) {

        e.printStackTrace();

    }

return problemStrings;
}

@Test
public void test(){
    Exc("D:\\JavaEEworkplace\\SoftwareContest\\pmd-bin-6.16.0\\bin","test.java");
}
}
