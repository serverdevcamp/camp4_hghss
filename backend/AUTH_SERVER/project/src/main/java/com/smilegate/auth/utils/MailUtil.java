package com.smilegate.auth.utils;

import com.smilegate.auth.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String fromEmail; //발신자의 이메일 아이디

    @Value("${spring.mail.password}")
    private String fromEmailpassword;//발신자의 이메일 패스워드

    private Session session;

    public MailUtil() {
        //mail configuration
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.starttls.enable", "true");

        this.session = Session.getDefaultInstance(prop, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(fromEmail, fromEmailpassword);//발신자의 이메 일아이디와 패스워드
            }
        });
    }

    public boolean sendSignupMail(String key, User user){
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("[합격하소서] 인증코드 전송");

            message.setContent("<h1>[이메일 인증]</h1> <p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p> " +
//                    "<a href='http://localhost:8000/users/signup/confirm?key="
                    "<a href='http://10.99.13.27:8000/users/signup/confirm?key="
                    +key +"' target='_blenk'>이메일 인증 확인</a>","text/html;charset=euc-kr");

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean sendPasswordMail(String key, String email) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // Subject
            message.setSubject("[합격하소서] 비밀번호 찾기 전송"); //메일 제목을 입력

            message.setContent("<h1>[비밀번호 변경]</h1> <p>아래 링크를 클릭하시면 비밀번호를 변경하실 수 있습니다.</p> " +
                    "<a href='http://localhost:8080/password/change?key="
                    +key +"' target='_blenk'>비밀번호 변경하기</a>","text/html;charset=euc-kr");

            // send the message
            Transport.send(message); ////전송

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
