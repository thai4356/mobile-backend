package mobibe.mobilebe.entity.role;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.constant.RoleType;
import mobibe.mobilebe.entity.BaseEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends BaseEntity {
    int objectId;
    String name;
    String note;

    @Column(name = "type", columnDefinition = "INT")
    RoleType type;
    @Column(name = "status", columnDefinition = "INT")
    ActiveStatus status;

}
