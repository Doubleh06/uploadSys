//package cn.sdjb.util;
//
//import cn.sdjb.entity.EmployeeMachine;
//import com.sun.mail.util.MailSSLSocketFactory;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//import javax.mail.internet.MimeMessage;
//import java.io.File;
//import java.security.GeneralSecurityException;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//@Component
//public class MailUtil {
//
//
////    private static String auth;
////    private static String host;
////    private static String protocol;
////    private static int port;
////    private static String authName;
////    private static String password;
////    private static boolean isSSL;
////    private static String charset ;
////    private static String timeout;
//    private static boolean flag = true;
//
////    @PostConstruct
////    public void initParam () {
////        auth = env.getProperty("mail.smtp.auth");
////        host = env.getProperty("mail.host");
////        protocol = env.getProperty("mail.transport.protocol");
////        port = env.getProperty("mail.smtp.port", Integer.class);
////        authName = env.getProperty("mail.auth.name");
////        password = env.getProperty("mail.auth.password");
////        charset = env.getProperty("mail.send.charset");
////        isSSL = env.getProperty("mail.is.ssl", Boolean.class);
////        timeout = env.getProperty("mail.smtp.timeout");
////
////    }
//
//    /**
//     * 发送邮件
//     * @param subject 主题
//     * @param toUsers 收件人
//     * @param ccUsers 抄送
//     * @param content 邮件内容
//     * @param attachfiles 附件列表  List<Map<String, String>> key: name && file
//     */
//    public static boolean sendEmail(EmployeeMachine email, String subject, String[] toUsers, String[] ccUsers, String content, List<Map<String, String>> attachfiles) {
//        String auth = email.getSmtpAuth();
//        String  host = email.getHost();
//        String protocol = email.getTransportProrocol();
//        String port = email.getSmtpPort();
//        String authName = email.getAuthName();
//        String password = email.getAuthPassword();
//        String charset = email.getSendCharset();
//        boolean isSSL = Boolean.parseBoolean(email.getIsSsl());
//        Integer timeout = email.getSmtpTimeout();
//
//        try {
//            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//            javaMailSender.setHost(host);
//            javaMailSender.setUsername(authName);
//            javaMailSender.setPassword(password);
//            javaMailSender.setDefaultEncoding(charset);
//            javaMailSender.setProtocol(protocol);
//            javaMailSender.setPort(Integer.parseInt(port));
//
//            Properties properties = new Properties();
//            properties.setProperty("mail.smtp.auth", auth);
//            properties.setProperty("mail.smtp.timeout", timeout.toString());
//            if(isSSL){
//                MailSSLSocketFactory sf = null;
//                try {
//                    sf = new MailSSLSocketFactory();
//                    sf.setTrustAllHosts(true);
//                    properties.put("mail.smtp.ssl.enable", "true");
//                    properties.put("mail.smtp.ssl.socketFactory", sf);
//                } catch (GeneralSecurityException e) {
//                    e.printStackTrace();
//                }
//            }
//            javaMailSender.setJavaMailProperties(properties);
//
//            MimeMessage mailMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
//            messageHelper.setTo(toUsers);
//            if (ccUsers != null && ccUsers.length > 0) {
//                messageHelper.setCc(ccUsers);
//            }
//            messageHelper.setFrom(authName);
//            messageHelper.setSubject(subject);
//            messageHelper.setText(content, true);
//
//            if (attachfiles != null && attachfiles.size() > 0) {
//                for (Map<String, String> attachfile : attachfiles) {
//                    String attachfileName = attachfile.get("name");
//                    File file = new File(attachfile.get("file"));
//                    messageHelper.addAttachment(attachfileName, file);
//                }
//            }
//            javaMailSender.send(mailMessage);
//
//        } catch (Exception e) {
//            System.out.println("发送邮件失败!"+e);
//            flag = false;
//        }
//        return flag;
//    }
//}
