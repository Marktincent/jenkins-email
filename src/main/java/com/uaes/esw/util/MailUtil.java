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
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * @author xu wen feng 10/22/2018 14:55
 */
@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String deliver;

    @Value("${receiver}")
    private String receiver;

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String s) {
        String subject = s;
        StringBuilder sb = new StringBuilder();
        sb.append("<p>Dear all:</p>");
        sb.append("<hr>");
        sb.append("<p>" + s + " released in  \\\\file-sh\\tbdata\\swb_tools\\02_EN13_Knowledge\\system\\nextcu\\5_Tool\\Xsrl\\Core\\jenkins\\" + s + " ,</p>");

        sb.append("<p>ReleaseTool implement issues as follows:</p>");
        StringBuilder sb1 = handleJson("D:\\result.txt");
        StringBuilder sbBug = handleJson("D:\\result_bug.txt");
        sb.append(sb1);
        sb.append("<p>In this release, there is some known issues as follows:</p>");
        sb.append(sbBug);
        sb.append("<p>NE1 Release Tool Team</p>");
        sb.append("<p>" + localDateTime() + "</p>");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver.split(","));
            messageHelper.setSubject(subject);
            messageHelper.setText(sb.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendHtmlEmailNew(String sarg) {
        // java -Dfile.encoding=utf-8 -jar jenkins-email.jar HandleConfig@%dist_path%@MergeVersion@%target_path%\srlmain.exe
        String target_path_srlmain = sarg.split("@")[3];
        String s = sarg.split("@")[2];
        String subject = s;
        StringBuilder sb = new StringBuilder();
        sb.append("<p>Dear all:</p>");
        sb.append("<hr>");
        sb.append("<p>" + s + " released in " + target_path_srlmain + " </p>");
        sb.append("<p>ReleaseTool Successfully</p>");
        sb.append("<p>In this release, there is some known issues as follows:</p>");
        sb.append("<p>NE1 Release Tool Team</p>");
        sb.append("<p>" + localDateTime() + "</p>");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver.split(","));
            messageHelper.setSubject(subject);
            messageHelper.setText(sb.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private StringBuilder handleJson(String path) {
        File file = new File(path);
        String msg = ReadTxtUtil.txt2String(file);
        if (!StringUtils.hasLength(msg)) {
            return null;
        }
        JsonObject jsonObject = new JsonParser().parse(msg).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("issues");
        if (jsonArray == null || jsonArray.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<table width=\"1000\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n" +
                "<th>Jira ISSUE ID</th>\n" +
                "<th>ISSUE TYPE</th>\n" +
                "<th>Summary</th>\n" +
                "</tr>\n");

        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JsonElement e = (JsonElement) it.next();
            JsonObject jsonObject1 = e.getAsJsonObject();
            String key = jsonObject1.get("key").toString();
            String fields = jsonObject1.get("fields").toString();
            JsonObject jsonObjectFields = new JsonParser().parse(fields).getAsJsonObject();
            String summary = jsonObjectFields.get("summary").toString();
            String issuetype = jsonObjectFields.get("issuetype").toString();
            JsonObject jsonObjectIssueType = new JsonParser().parse(issuetype).getAsJsonObject();
            String name = jsonObjectIssueType.get("name").toString();
            key = key.replace("\"", "");
            sb.append("<tr><td><a href='http://174.34.53.220:8080/browse/" + key + "'>" + key + "</a></td>");
            sb.append("<td>" + name + "</td>");
            sb.append("<td>" + summary + "</td></tr>");
        }
        sb.append("</table>");
        sb.append("<hr>");
        return sb;
    }

    private String localDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public void sendEmailContent() {
        String subject = "Test";
        StringBuilder sb = new StringBuilder();
        sb.append("<p>Dear all:</p>");
        sb.append("<hr>");
        sb.append("<p> released in </p>");
        sb.append("<p>ReleaseTool Successfully</p>");
        sb.append("<p>In this release, there is some known issues as follows:</p>");
        sb.append("<p>NE1 Release Tool Team</p>");
        sb.append("<p>" + localDateTime() + "</p>");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver.split(","));
            messageHelper.setSubject(subject);
            messageHelper.setText(sb.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
