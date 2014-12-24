package com.baosight.scc.ec.utils;

import com.baosight.scc.ec.model.EcProvider;
import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.model.OrderLine;
import com.baosight.scc.ec.service.EcProviderService;
import com.baosight.scc.ec.service.OrderItemService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.tiles.request.servlet.ServletApplicationContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.context.ServletContextAware;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContainerInitializer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Charles on 2014/9/25.
 */
public class MailSender{
    private static String url;
    private static String mailSenderHost;
    private static int mailSenderPort;
    private static String mailSenderUsername;
    private static String mailSenderPassword;

    @Value("${security-postHandler}")
    public void setUrl(String url) {
        MailSender.url = url;
    }
    @Value("${mailSender-host}")
    public void setMailSenderHost(String mailSenderHost){
        MailSender.mailSenderHost = mailSenderHost;
    }
    @Value("${mailSender-port}")
    public void setMailSenderPort(int mailSenderPort){
        MailSender.mailSenderPort = mailSenderPort;
    }
    @Value("${mailSender-username}")
    public void setMailSenderUsername(String mailSenderUsername){
        MailSender.mailSenderUsername = mailSenderUsername;
    }
    @Value("${mailSender-password}")
    public void setMailSenderPassword(String mailSenderPassword){
        MailSender.mailSenderPassword = mailSenderPassword;
    }

    @Autowired
    private OrderItemService orderItemService;
    // Spring的邮件工具类，实现了MailSender和JavaMailSender接口
    private JavaMailSenderImpl mailSender;

    public JavaMailSenderImpl javaMailSender() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderHost);
        mailSender.setPort(mailSenderPort);
        mailSender.setUsername(mailSenderUsername);
        mailSender.setPassword(mailSenderPassword);
        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        mailSender.setJavaMailProperties(props);
        return  mailSender;
    }

    public boolean sendOrderMail(String mailTo, String subject, String content){
        try {
            mailSender = javaMailSender();
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
            helper.setFrom(mailSenderUsername);
            helper.setTo(mailTo);
            helper.setSubject(subject);
            mail.setContent(content, "text/html;charset=utf8");
            mailSender.send(mail);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getUserInfo(String param) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        System.err.println("建立请求url==》" + url);
        postMethod.setRequestBody(param);
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(50000);// 设置超时
        try {
            int status = httpClient.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                result = postMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
