package com.uaes.esw;

import com.uaes.esw.util.MailUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.uaes.esw.util.ModifyConfigUtil;

import java.io.IOException;


/**
 * @author xu wen feng 10/10/2018 14:24
 */

@SpringBootApplication
public class JenkinsEmailApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext contextTest = SpringApplication.run(JenkinsEmailApplication.class, args);
        MailUtil mailUtilTest = contextTest.getBean(MailUtil.class);
        mailUtilTest.sendEmailContent();
        String s = null;
        if (args.length > 0) {
            s = args[0];
        }
        //s = "HandleConfig@\\\\file-sh\\NE1\\NE1_DLP\\SS1\\003\\02_EN13_Knowledge\\System\\Tools\\02_ReleaseTool\\config_test\\config.xml@MergeVersion@\\\\Core\\V7.1.0_test\\srlmain.exe";
        System.out.println("args:----------" + s);
        // handle config.xml
        try {
            new ModifyConfigUtil().modifyConfig(s.split("@")[1], s.split("@")[2], s.split("@")[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (s.startsWith("TagHandleConfig@")) {
            //tag handle config.xml
            ConfigurableApplicationContext context = SpringApplication.run(JenkinsEmailApplication.class, args);
            MailUtil mailUtil = context.getBean(MailUtil.class);
            mailUtil.sendHtmlEmail(s.split("@")[2]);
        }

        if (s.startsWith("HandleConfig@")) {
            ConfigurableApplicationContext context = SpringApplication.run(JenkinsEmailApplication.class, args);
            MailUtil mailUtil = context.getBean(MailUtil.class);
            mailUtil.sendHtmlEmailNew(s);
        }
    }
}
