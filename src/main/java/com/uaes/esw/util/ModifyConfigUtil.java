package com.uaes.esw.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * @author zhenghuan.wang
 */
public class ModifyConfigUtil {
    public void modifyConfig(String configFilePath, String mergeVersion, String replacePath) throws IOException {
        System.out.println("filePath:" + configFilePath + "---mergeVersion:" + mergeVersion);
        //原有的内容
        String srcStr = "<CMD_TOOL_PATH></CMD_TOOL_PATH>";
        //要替换的内容
        String replaceStr = "userName:";

        File file = new File(configFilePath);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter tempStream = new CharArrayWriter();
        // 替换
        String line = null;
        while ((line = bufIn.readLine()) != null) {
            // 替换每行中, 符合条件的字符串
            //line = line.replaceAll(srcStr, replaceStr);
            int s_int = line.indexOf("<CMD_TOOL_PATH>");
            int e_int = line.indexOf("</CMD_TOOL_PATH>");
            if (s_int != -1 && e_int != -1) {
                line = line.substring(0, s_int + 15) + replacePath + line.substring(e_int);
                System.out.println("====modify path:" + configFilePath);
            }
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
        }
        // 关闭 输入流
        bufIn.close();
        // 将内存中的流 写入 文件
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
    }

}
