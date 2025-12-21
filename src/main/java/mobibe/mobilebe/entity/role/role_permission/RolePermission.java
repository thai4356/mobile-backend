package mobibe.mobilebe.entity.role.role_permission;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.entity.BaseEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "role_permission")
public class RolePermission extends BaseEntity {
    @Column(name = "role_id")
    int roleId;
    @Column(name = "permission_id")
    int permissionId;
    @Column(name = "can_view")
    @JsonProperty("view")
    Boolean isView;
    @JsonProperty("write")
    @Column(name = "can_write")
    Boolean isWrite;
    @JsonProperty("approval")
    @Column(name = "can_approval")
    Boolean isApproval;
    @JsonProperty("decision")
    @Column(name = "can_decision")
    Boolean isDecision;

}
