package mobibe.mobilebe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import mobibe.mobilebe.converter.Translator;
import mobibe.mobilebe.dto.constant.RoleType;
import mobibe.mobilebe.entity.role.constant.PermissionKey;
import mobibe.mobilebe.entity.role.constant.PermissionType;
import mobibe.mobilebe.entity.user.User;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.roleRepository.RoleRepository;
import mobibe.mobilebe.security.SecurityContexts;

@Log4j2
@Service
public class BaseService {
    @Autowired
    private RoleRepository roleRepository;

    protected User getUser() {
        try {
            return (User) SecurityContexts.getContext().getData();
        } catch (Exception e) {
            log.error("getUser", e);
        }
        throw new BusinessException(Translator.toLocale("login_required"), HttpStatus.UNAUTHORIZED);
    }

    protected User getUser(PermissionKey key, PermissionType... groups) {
        User user = getUser();
        if (key == null && (groups == null || groups.length == 0)) {
            return user;
        }
        if (roleRepository.existPermission(user.getRoleId(), groups, key, null)) {
            return user;
        }
        throw new BusinessException(Translator.toLocale("invalid_permission"));
    }

    protected User getUser(RoleType roleType, PermissionKey key, PermissionType... groups) {
        User user = getUser();
        if (roleType == null && key == null && (groups == null || groups.length == 0)) {
            return user;
        }
        if (roleRepository.existPermission(user.getRoleId(), groups, key, roleType)) {
            return user;
        }
        throw new BusinessException(Translator.toLocale("invalid_permission"));
    }

}
