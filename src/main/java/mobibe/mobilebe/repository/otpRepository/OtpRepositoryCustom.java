package mobibe.mobilebe.repository.otpRepository;

import java.util.Optional;

import mobibe.mobilebe.dto.constant.SendType;
import mobibe.mobilebe.dto.constant.VerifyStatus;
import mobibe.mobilebe.dto.request.otp.SendOtpReq;
import mobibe.mobilebe.entity.otp.Otp;

public interface OtpRepositoryCustom {
    void updateStatusVerify(SendOtpReq request);

    Otp findSessionAuth(String phone, String email, String otp);

    int findLatestOtpId(String phone);
}
