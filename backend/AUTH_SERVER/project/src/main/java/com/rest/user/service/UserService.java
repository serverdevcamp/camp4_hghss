package com.rest.user.service;

import com.rest.user.dto.SimpleResponse;
import com.rest.user.dto.request.InsertUserRequestDTO;
import com.rest.user.mapper.UserMapper;
import com.rest.user.model.User;
import com.rest.user.util.SHA256;
import com.rest.user.util.TempKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private jwtService jwtService;

    //회원가입

    public ResponseEntity signUp(InsertUserRequestDTO insertUserRequestDTO) {

        try{

            User userMapperUserByEmail = userMapper.getUserByEmail(insertUserRequestDTO.getEmail());

            if (userMapperUserByEmail == null) {
                //signup ok

                User tmpUser = new User();
                //salt 생성(check하기)
                SecureRandom secRan = SecureRandom.getInstance("SHA1PRNG");
                int numLength = 16;
                String salt = "";
                for (int i = 0; i < numLength; ++i) {
                    salt += secRan.nextInt(10);
                }

                String newPasswd = salt + insertUserRequestDTO.getPassword();
                String hassedPasswd = (SHA256.getInstance()).encodeSHA256(newPasswd);

                tmpUser.setHashedPassword(hassedPasswd);
                tmpUser.setSalt(salt);
                tmpUser.setEmail(insertUserRequestDTO.getEmail());
                //email 중복체크하기!!!!!


                //key = email.uuid
                String key = new TempKey().getKey(insertUserRequestDTO.getEmail());

                if (sendMail(tmpUser,key)) {
                    //emailKey
                    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(User.class));

                    ValueOperations<String,Object> vop = redisTemplate.opsForValue();

                    //timeout 설정 10분 이메일 인증 캐시 설정
                    vop.set(key,tmpUser,10, TimeUnit.MINUTES);

                    return SimpleResponse.ok(tmpUser);

                } else {
                    //fail
                    return SimpleResponse.badRequest();
                }
//예외상황 response 체크
            } else {
                return SimpleResponse.badRequest();

            }

        } catch(Exception e) {
            return SimpleResponse.badRequest();
        }

    }


    public ResponseEntity confirm(String emailKey) {

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(User.class));

        ValueOperations<String,Object> vop = redisTemplate.opsForValue();
        User redisGetEmailValue = (User) vop.get(emailKey);

        //cache emailKey 해독하여 얻은 email과 emailKey의 email일치하는지 확인??체크
        if (redisGetEmailValue == null) {
            return SimpleResponse.badRequest();
        }
        //db에 넣기( emailKey :hassedpassword,salt,email )
        return userMapper.insertUser(redisGetEmailValue) ? SimpleResponse.ok(redisGetEmailValue)
                : SimpleResponse.badRequest();

    }


    public boolean sendMail(User user,String key){

        String FromEmail = "meme91322367@gmail.com"; //발신자의 이메일 아이디
        String FromEmailpassword = "212721wkd";//발신자의 이메일 패스워드

        try{
    //mail configuration
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", 465);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");


            Session session = Session.getDefaultInstance(prop,new javax.mail.Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(FromEmail,FromEmailpassword);//발신자의 이메 일아이디와 패스워드
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FromEmail));
                //수신자메일주소
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
                // Subject
                message.setSubject("[합격하소서] 인증코드 전송"); //메일 제목을 입력

                String mailConent = "<h1>[이메일 인증]</h1> <p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p> " +
                        "<a href='http://localhost:8080/users/confirm?emailKey="
                        +key +"' target='_blenk'>이메일 인증 확인</a>";

                message.setContent("<h1>[이메일 인증]</h1> <p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p> " +
                        "<a href='http://localhost:8080/users/confirm?emailKey="
                        +key +"' target='_blenk'>이메일 인증 확인</a>","text/html;charset=euc-kr");

                // send the message
                Transport.send(message); ////전송
                System.out.println("message sent successfully...");

                return true;
            } catch (AddressException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }

        } catch(Exception e) {
            return false;
        }

    }

    public ResponseEntity signIn(User user) {
//request : email,password
        try {

            User myUser = userMapper.getUserByEmail(user.getEmail());

            if (myUser == null) {
                //예외상황처리 체크!!
                return SimpleResponse.badRequest();
            }

            String newHashPassword = SHA256.getInstance().encodeSHA256(myUser.getSalt() + user.getPassword());

            if (newHashPassword.equals(myUser.getHashedPassword())) {
                final jwtService.TokenResponse token
                        = new jwtService.TokenResponse(
                                jwtService.create(myUser.getId(),"USER","accessToken"),
                        jwtService.create(myUser.getId(),"USER","refreshToken")
                        );

                //토큰 형태 access token(만료기한 2시간) / refresh token(만료기간 일주일)
                //redis에 {userid:accessToken} 형태로 저장 (timeout 2시간 , 자동 삭제)

                //emailKey
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(String.class));
                ValueOperations<String,Object> vop = redisTemplate.opsForValue();
                vop.set(Integer.toString(myUser.getId()),token.getAccessToken(),120, TimeUnit.MINUTES);

                //User response token
                System.out.print("token test!!!!\n");
                System.out.println(token.getAccessToken());
                System.out.println(token.getRefreshToken());

                System.out.print("token test - 222!!!!\n");
                com.rest.user.service.jwtService.Token newtoken = jwtService.decode(token.getAccessToken());
                System.out.print(newtoken.getUserIdx());
                System.out.print(newtoken.getGrade());


                return SimpleResponse.ok(token);

            } else {
                return SimpleResponse.badRequest();
            }

        } catch(Exception e) {
            return SimpleResponse.badRequest();

        }
    }

}

