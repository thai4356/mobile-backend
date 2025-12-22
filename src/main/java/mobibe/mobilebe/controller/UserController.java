package mobibe.mobilebe.controller;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.request.auth.ForgotPasswordReq;
import mobibe.mobilebe.dto.request.user.ChangePasswordReq;
import mobibe.mobilebe.dto.request.user.EditMyProfileReq;
import mobibe.mobilebe.dto.request.user.UpdateUserReq;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.user.UserDetailRes;
import mobibe.mobilebe.service.userService.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;

        @GetMapping("/me")
        // @PreAuthorize("hasAnyRole('USER','ADMIN')")
        public ResponseEntity<BaseResponse<UserDetailRes>> getMyProfile() {
                return ResponseEntity.ok(
                                new BaseResponse<>(userService.getMyProfile()));
        }

        @PutMapping("/me")
        // @PreAuthorize("hasAnyRole('USER','ADMIN')")
        public ResponseEntity<BaseResponse<UserDetailRes>> editMyProfile(
                        @RequestBody EditMyProfileReq request) {

                return ResponseEntity.ok(
                                new BaseResponse<>(userService.editMyProfile(request)));
        }

        @PostMapping("/forgot-password")
        public ResponseEntity<BaseResponse<String>> forgotPassword(
                        @RequestBody ForgotPasswordReq request) {

                return ResponseEntity.ok(
                                new BaseResponse<>(userService.forgotPassword(request)));
        }

        @PutMapping("/me/password")
        // @PreAuthorize("hasAnyRole('USER','ADMIN')")
        public ResponseEntity<BaseResponse<Void>> changeMyPassword(
                        @RequestBody ChangePasswordReq request) {

                userService.changeMyPassword(request);
                return ResponseEntity.ok(
                                new BaseResponse<>(null, "Password changed successfully"));
        }

        @PutMapping("/{userId}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<BaseResponse<UserDetailRes>> updateUserRole(
                        @PathVariable Integer userId,
                        @RequestBody UpdateUserReq request) {

                return ResponseEntity.ok(
                                new BaseResponse<>(userService.updateMember(userId, request)));
        }

}
