package com.smilegate.auth.controller;

import com.smilegate.auth.domain.User;
import com.smilegate.auth.dto.ResultResponse;
import com.smilegate.auth.dto.request.UpdateRoleRequestDto;
import com.smilegate.auth.exceptions.UnauthorizedException;
import com.smilegate.auth.service.AdminService;
import com.smilegate.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @GetMapping("/users")
    public ResponseEntity<ResultResponse> list(HttpServletRequest request) {

        String token = jwtUtil.getToken(request);
        if(!jwtUtil.isAccessToken(token)) throw new UnauthorizedException();

        List<User> users = adminService.getUsers();

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("유저 리스트입니다.")
                        .data(users)
                        .build()
        );
    }

    @PutMapping("/users/update/role")
    public ResponseEntity<ResultResponse> updateRole(
            @RequestBody UpdateRoleRequestDto updateRoleRequestDto,
            HttpServletRequest request
    ) {

        String token = jwtUtil.getToken(request);
        if(!jwtUtil.isAccessToken(token)) throw new UnauthorizedException();

        int id = updateRoleRequestDto.getId();
        String role = updateRoleRequestDto.getRole();

        adminService.updateUserRole(id, role);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("회원 정보가 변경되었습니다.")
                        .build()
        );

    }

}
