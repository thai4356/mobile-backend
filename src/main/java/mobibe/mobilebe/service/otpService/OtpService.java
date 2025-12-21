package mobibe.mobilebe.service.otpService;

import org.springframework.stereotype.Service;

import mobibe.mobilebe.dto.request.otp.SendOtpReq;
import mobibe.mobilebe.dto.response.otp.SendOtp;

@Service
public interface  OtpService {

    SendOtp sendOtpUser (SendOtpReq request);
    
}
