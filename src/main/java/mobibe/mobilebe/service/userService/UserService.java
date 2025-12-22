package mobibe.mobilebe.service.userService;

import java.util.List;

import org.springframework.stereotype.Service;

import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.request.auth.ForgotPasswordReq;
import mobibe.mobilebe.dto.request.auth.UserLoginReq;
import mobibe.mobilebe.dto.request.auth.user.RegisterUser;
import mobibe.mobilebe.dto.request.user.ChangePasswordReq;
import mobibe.mobilebe.dto.request.user.EditMyProfileReq;
import mobibe.mobilebe.dto.request.user.UpdateUserReq;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.user.UserDetailRes;
import mobibe.mobilebe.dto.response.user.UserListRes;

@Service
public interface UserService {
    UserDetailRes login(UserLoginReq request);

    UserDetailRes register(RegisterUser request);

    BaseResponse<List<UserListRes>> getUsers(ActiveStatus status, String searchKeyword, int page);

    BaseResponse<UserDetailRes> getDetailMember(int accountId);

    UserDetailRes updateMember(Integer userId,UpdateUserReq request);

    UserDetailRes getMyProfile();

    void changeMyPassword(ChangePasswordReq request);

    String forgotPassword(ForgotPasswordReq request);

    UserDetailRes editMyProfile(EditMyProfileReq request);

}