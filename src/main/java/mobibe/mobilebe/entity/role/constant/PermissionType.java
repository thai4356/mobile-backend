package mobibe.mobilebe.entity.role.constant;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import mobibe.mobilebe.dto.constant.BaseEnum;

public enum PermissionType implements BaseEnum<String> {

    DASHBOARD,
    ACCOUNT,
    ROLE,

    PROVIDER_CONTENT,
    PROVIDER_DEVICE,
    MERCHANT,
    MERCHANT_BRANCH,
    DEVICE,
    SONG
    ;


    @JsonCreator
    public static PermissionType fromValue(String value) {
        for (PermissionType column : values()) {
            if (column.toValue().equals(value)) {
                return column;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toValue() {
        return name();
    }
}
