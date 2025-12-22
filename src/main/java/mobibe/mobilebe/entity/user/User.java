package mobibe.mobilebe.entity.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.entity.BaseEntity;
import mobibe.mobilebe.entity.role.Role;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
public class User extends BaseEntity {
    String code;
    String phone;
    String email;
    String name;
    String address;
    @JsonIgnore
    String password;
    Date birthday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", nullable = false)
    Role role;

    @Column(name = "status", columnDefinition = "INT")
    ActiveStatus status;
    boolean deleted;
}
