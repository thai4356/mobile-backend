package mobibe.mobilebe.service.send_email;

import mobibe.mobilebe.entity.email.EmailDetail;

public interface SendEmailService {
    void sendSimpleMail(EmailDetail details);
}

