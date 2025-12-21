package mobibe.mobilebe.entity.otp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.SendType;
import mobibe.mobilebe.dto.constant.VerifyStatus;
import mobibe.mobilebe.entity.BaseEntity;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "otp")
@Entity
public class Otp extends BaseEntity {
    String otp;
    String phone;
    String email;

    @Column(name = "attempt_count")
    int attemptCount = 3;

    @Column(name = "send_type", columnDefinition = "INT")
    SendType sendType;

    @Column(name = "status", columnDefinition = "INT")
    VerifyStatus status;

    boolean deleted;
}
