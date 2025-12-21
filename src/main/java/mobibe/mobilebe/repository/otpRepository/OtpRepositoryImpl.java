package mobibe.mobilebe.repository.otpRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import mobibe.mobilebe.dto.constant.SendType;
import mobibe.mobilebe.dto.constant.VerifyStatus;
import mobibe.mobilebe.dto.request.otp.SendOtpReq;
import mobibe.mobilebe.entity.otp.QOtp;
import mobibe.mobilebe.entity.otp.Otp;
import mobibe.mobilebe.repository.BaseRepository;

@Repository
public class OtpRepositoryImpl extends BaseRepository implements OtpRepositoryCustom {
    private final QOtp qOtp = QOtp.otp1;

    @Override
    @Transactional
    public void updateStatusVerify(SendOtpReq request) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qOtp.status.eq(VerifyStatus.VERIFY_PENDING));
        if (request.getEmail() != null) {
            builder.and(qOtp.email.eq(request.getEmail()));
        }
        if (request.getPhone() != null) {
            builder.and(qOtp.phone.eq(request.getPhone()));
        }

        query().update(qOtp)
                .where(builder)
                .set(qOtp.status, VerifyStatus.OTHER)
                .execute();

    }

    @Override
    public Otp findSessionAuth(String phone, String email, String otp) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOtp.status.eq(VerifyStatus.VERIFY_PENDING));
        builder.and(qOtp.deleted.eq(false));
        builder.and(qOtp.phone.eq(phone));
        builder.and(qOtp.otp.eq(otp));
        builder.and(qOtp.email.eq(email));

        return query().from(qOtp)
                .where(builder)
                .select(qOtp)
                .fetchOne();
    }

    public int findLatestOtpId(String phone) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOtp.deleted.eq(false));

        if (phone != null) {
            builder.and(qOtp.phone.eq(phone));
        }

        Integer id = query()
                .from(qOtp)
                .where(builder)
                .orderBy(qOtp.createdAt.desc())
                .select(qOtp.id)
                .fetchFirst();

        return id;
    }
}
