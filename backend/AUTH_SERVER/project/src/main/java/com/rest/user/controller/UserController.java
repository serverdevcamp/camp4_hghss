package com.rest.user.controller;

import com.rest.user.dto.SimpleResponse;
import com.rest.user.dto.request.InsertUserRequestDTO;
import com.rest.user.dto.response.InsertUserResponseDTO;
import com.rest.user.model.User;
import com.rest.user.service.UserService;
import com.rest.user.util.TempKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.ws.Response;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;


@RestController
@RequestMapping("/users")
public class UserController implements Serializable {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody InsertUserRequestDTO insertUserRequestDTO) {
        return userService.signUp(insertUserRequestDTO);
    }


    //email confirm
    @GetMapping("/confirm")
    public ResponseEntity confirm(@RequestParam(value = "emailKey") String emailKey){
        return userService.confirm(emailKey);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity signIn(@RequestBody User user) {
        return userService.signIn(user);
    }

}
