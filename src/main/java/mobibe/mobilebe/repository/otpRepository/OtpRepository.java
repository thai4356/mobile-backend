package mobibe.mobilebe.repository.otpRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import mobibe.mobilebe.entity.otp.Otp;

public interface OtpRepository extends JpaRepository<Otp, Integer>, OtpRepositoryCustom{
}
        