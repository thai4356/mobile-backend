package mobibe.mobilebe.entity.role.permission;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import mobibe.mobilebe.entity.role.constant.PermissionGroup;
import mobibe.mobilebe.entity.role.constant.PermissionType;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Table(name = "permissions")
    public class Permission extends BaseEntity {
        String title;
        @Enumerated(value = EnumType.STRING)
        PermissionType permission;
        @Column(name = "parent_permission")
        @Enumerated(value = EnumType.STRING)
        PermissionGroup parentPermission;
        @Column(name = "can_view")
        @JsonProperty("view")
        Boolean isView;
        @Column(name = "can_write")
        @JsonProperty("write")
        Boolean isWrite;
        @Column(name = "can_approval")
        @JsonProperty("approval")
        Boolean isApproval;
        @Column(name = "can_decision")
        @JsonProperty("decision")
        Boolean isDecision;

        @Column(name = "type", columnDefinition = "INT")
        RoleType type;
        @Column(name="status", columnDefinition = "INT")
        ActiveStatus status;
    }
