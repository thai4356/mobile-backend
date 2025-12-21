package mobibe.mobilebe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mobibe.mobilebe.converter.Translator;
import mobibe.mobilebe.dto.request.auth.UserLoginReq;
import mobibe.mobilebe.dto.request.auth.user.RegisterUser;
import mobibe.mobilebe.dto.request.otp.SendOtpReq;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.otp.SendOtp;
import mobibe.mobilebe.dto.response.user.UserDetailRes;
import mobibe.mobilebe.service.otpService.OtpService;
import mobibe.mobilebe.service.userService.UserService;

@Log4j2
@RestController
@RequestMapping("/api/v1")
public class AuthController {
        @Autowired
        private UserService userService;
        @Autowired
        private OtpService otpService;
        @Autowired
        private Translator translator;

        @Operation(summary = "Login")
        @PostMapping("/auth/login")
        public ResponseEntity<BaseResponse<UserDetailRes>> loginUser(
                        @RequestBody @Valid UserLoginReq request) {

                UserDetailRes data = userService.login(request);
                return ResponseEntity.ok(
                                new BaseResponse<>(
                                                data,
                                                translator.toLocale("label_success"),200));
        }

        @Operation(summary = "Register")
        @PostMapping("/auth/register")
        public ResponseEntity<BaseResponse<UserDetailRes>> registerUser(
                        @RequestBody @Valid RegisterUser request) {

                UserDetailRes data = userService.register(request);
                return ResponseEntity.ok(
                                new BaseResponse<>(
                                                data,
                                                translator.toLocale("label_success")));
        }

        // @Operation(summary = "Forgot password")
        // @PostMapping("v1/auth/forgot-password")
        // public ResponseEntity<BaseResponse<String>> forgotPassword(@RequestBody
        // @Valid ForgotPasswordReq request) {
        // return ResponseEntity.ok(new
        // BaseResponse<>(userService.forgotPassword(request)));
        // }

        @Operation(summary = "Send OTP forgot password")
        @PostMapping("/auth/send-otp")
        public ResponseEntity<BaseResponse<SendOtp>> sendOtpUser(
                        @RequestBody @Valid SendOtpReq request) {

                SendOtp data = otpService.sendOtpUser(request);
                return ResponseEntity.ok(
                                new BaseResponse<>(
                                                data,
                                                translator.toLocale("otp_sent")));
        }
}
