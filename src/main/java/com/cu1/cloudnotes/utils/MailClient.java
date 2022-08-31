import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@SuppressWarnings("all")
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;

    //指定服务器邮箱
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 实现发邮件的功能
     * @param to 要发给谁
     * @param subject 邮件的主题是什么
     * @param content 邮件内容是什么
     */
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from); //设置发件人
            helper.setTo(to); //设置收件人
            helper.setSubject(subject); //设置主题
            /*
             * 设置邮件内容
             * 第二参数表示支持发送 HTML 邮件
             * */
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败" + e.getMessage());
        }

    }
}