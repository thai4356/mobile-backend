package mobibe.mobilebe.dto.request.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class EditMyProfileReq {

    private String name;
    private String phone;
    private String address;
    private Date birthday;
}