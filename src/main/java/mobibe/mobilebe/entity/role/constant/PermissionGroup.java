package mobibe.mobilebe.entity.role.constant;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import mobibe.mobilebe.dto.constant.BaseEnum;

public enum PermissionGroup implements BaseEnum<String> {
    STATISTIC,
    CONFIG;

    @JsonCreator
    public static PermissionGroup fromValue(String value) {
        for (PermissionGroup column : values()) {
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
