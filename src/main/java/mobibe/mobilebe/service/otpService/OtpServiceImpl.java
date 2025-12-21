package mobibe.mobilebe.service.otpService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mobibe.mobilebe.converter.Translator;
import mobibe.mobilebe.dto.constant.OtpSendPurpose;
import mobibe.mobilebe.dto.constant.VerifyStatus;
import mobibe.mobilebe.dto.request.otp.SendOtpReq;
import mobibe.mobilebe.dto.response.otp.SendOtp;
import mobibe.mobilebe.entity.email.EmailDetail;
import mobibe.mobilebe.entity.otp.Otp;
import mobibe.mobilebe.entity.user.User;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.otpRepository.OtpRepository;
import mobibe.mobilebe.repository.userRepository.UserRepository;
import mobibe.mobilebe.service.send_email.SendEmailServiceImpl;
import mobibe.mobilebe.util.Constants;
import mobibe.mobilebe.util.Util;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final ResourceLoader resourceLoader;
    private final SendEmailServiceImpl sendEmailService;

    public OtpServiceImpl(OtpRepository otpRepository, ResourceLoader resourceLoader, SendEmailServiceImpl sendEmailService, UserRepository userRepository) {
        this.otpRepository = otpRepository;
        this.resourceLoader = resourceLoader;
        this.sendEmailService = sendEmailService;
        this.userRepository = userRepository;
    }

   
    @Override
    @Transactional
    public SendOtp sendOtpUser(SendOtpReq request) {
        User user = userRepository.getUserByPhone(request.getPhone());
        if (request.getType() == null || request.getPurpose() == null) {
            throw new BusinessException(Translator.toLocale("invalid_request"), HttpStatus.BAD_REQUEST);
        }
        if (user != null && request.getPurpose().equals(OtpSendPurpose.REGISTRATION)) {
            throw new BusinessException(Translator.toLocale("phone_already_exists"), HttpStatus.BAD_REQUEST);
        }
        if (user == null && request.getPurpose().equals(OtpSendPurpose.PASSWORD_RESET)) {
            throw new BusinessException(Translator.toLocale("phone_no_exists"), HttpStatus.BAD_REQUEST);
        }
        return sendOtp(request, user);
    }

    private SendOtp sendOtp(SendOtpReq request, Object object) {

        otpRepository.updateStatusVerify(request);

        Otp otp = new Otp();
        otp.setPhone(request.getPhone());
        otp.setEmail(request.getEmail());
        otp.setSendType(request.getType());
        otp.setOtp(Util.randomString(6, Constants.DIGITS));
        otp.setStatus(VerifyStatus.VERIFY_PENDING);
        otp.setCreatedAt(new Date());
        otp.setUpdatedAt(new Date());
        otpRepository.save(otp);

        SendOtp result = new SendOtp();
        sendEmail(request, otp.getOtp(), request.getPurpose());
        result.setId(otp.getId());
        result.setEmail(otp.getEmail());
        // switch (request.getType()) {
        // case ZNS:
        // sendZns(request, otp.getOtp());
        // result.setId(otp.getId());
        // result.setPhone(otp.getPhone());
        // break;
        // case SMS:
        // // Call func send otp with SMS
        // // Phục vụ test
        // result.setId(otp.getId());
        // result.setOtp(otp.getOtp());
        // result.setPhone(otp.getPhone());
        // break;
        // case EMAIL:

        // break;
        // default:
        // throw new BusinessException(Translator.toLocale("invalid_request"),
        // HttpStatus.BAD_REQUEST);
        // }
        return result;
    }

    private void sendEmail(SendOtpReq request, String otp, OtpSendPurpose purpose) {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(request.getEmail());

        // Subject đơn giản (tuỳ mục đích)
        String subject = (purpose == OtpSendPurpose.REGISTRATION)
                ? "Your registration OTP"
                : "Your password reset OTP";
        emailDetail.setSubject(subject);

        // Nội dung chỉ chứa mã OTP (text thuần)
        // Có thể bổ sung thời hạn tuỳ ý (vd: 5 phút)
        String body = "OTP: " + otp + "\nThis code will expire in 5 minutes.";
        emailDetail.setMsgBody(body);

        // Gửi như bình thường
        sendEmailService.sendSimpleMail(emailDetail);
    }

    private String readTemplate(String templateName) {
        Resource resource = resourceLoader.getResource("classpath:templates/" + templateName);
        try (InputStream inputStream = resource.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}