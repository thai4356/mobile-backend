package mobibe.mobilebe.service.userService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.v3.oas.annotations.servers.Server;
import mobibe.mobilebe.converter.Translator;
import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.constant.VerifyStatus;
import mobibe.mobilebe.dto.request.auth.UserLoginReq;
import mobibe.mobilebe.dto.request.auth.user.RegisterUser;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.role.RoleDetail;
import mobibe.mobilebe.dto.response.user.UserDetailRes;
import mobibe.mobilebe.dto.response.user.UserListRes;
import mobibe.mobilebe.entity.otp.Otp;
import mobibe.mobilebe.entity.role.Role;
import mobibe.mobilebe.entity.role.constant.PermissionKey;
import mobibe.mobilebe.entity.role.constant.PermissionType;
import mobibe.mobilebe.entity.role.permission.Permission;
import mobibe.mobilebe.entity.role.role_permission.RolePermission;
import mobibe.mobilebe.entity.user.User;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.otpRepository.OtpRepository;
import mobibe.mobilebe.repository.permission.PermissionRepository;
import mobibe.mobilebe.repository.roleRepository.RolePermissionRepository;
import mobibe.mobilebe.repository.roleRepository.RoleRepository;
import mobibe.mobilebe.repository.userRepository.UserRepository;
import mobibe.mobilebe.security.JwtTokenProvider;
import mobibe.mobilebe.service.BaseService;
import mobibe.mobilebe.util.Util;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final OtpRepository otpRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
     
    @Value("${app.jwtAdminExpirationInMs}")
    private int jwtExpirationInMs;

    private final int OTP_EXPIRY_IN_MINUTES = 5;

    public UserServiceImpl(JwtTokenProvider jwtTokenProvider, OtpRepository otpRepository, PasswordEncoder passwordEncoder, PermissionRepository permissionRepository, RolePermissionRepository rolePermissionRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailRes login(UserLoginReq request) {
        User user = userRepository.loginByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(Translator.toLocale("login_fail"), HttpStatus.UNAUTHORIZED);
        }
        if (user.getStatus() != ActiveStatus.ACTIVE) {
            throw new BusinessException(Translator.toLocale("account_not_activated"));
        }
        UserDetailRes userLoginRes = getUserRes(user);
        userLoginRes.setAccessToken(jwtTokenProvider.generateTokenRs256(String.valueOf(user.getId()), jwtExpirationInMs));
        return userLoginRes;
    }

    @Transactional
    @Override
    public UserDetailRes register(RegisterUser request) {
        Otp sessionAuth = otpRepository.findById(otpRepository.findLatestOtpId(request.getAccountPhone())).orElse(null);
        if (sessionAuth == null || sessionAuth.getStatus() == VerifyStatus.VERIFIED) {
            throw new BusinessException(Translator.toLocale("register_fail"));
        }
        try {
            if (sessionAuth.getOtp().equals(request.getCode()) && Util.getDuration(new Date(), sessionAuth.getCreatedAt(), TimeUnit.MINUTES) >= OTP_EXPIRY_IN_MINUTES) {
                sessionAuth.setStatus(VerifyStatus.EXPIRED);
                throw new BusinessException(Translator.toLocale("otp_expired"));
            }
            if (sessionAuth.getAttemptCount() <= 0) {
                sessionAuth.setStatus(VerifyStatus.FAILED);
                throw new BusinessException(Translator.toLocale("otp_over"));
            }
            sessionAuth.setAttemptCount(sessionAuth.getAttemptCount() - 1);
            if (!sessionAuth.getPhone().equals(request.getAccountPhone())) {
                sessionAuth.setStatus(VerifyStatus.FAILED);
                throw new BusinessException(Translator.toLocale("register_fail"));
            }
            if (!sessionAuth.getOtp().equals(request.getCode())) {
                throw new BusinessException(Translator.toLocale("otp_wrong"));
            }
            sessionAuth.setStatus(VerifyStatus.VERIFIED);
        } finally {
            otpRepository.save(sessionAuth);
        }

        String code = generateCode(8);

        Role role = new Role();
        role.setStatus(ActiveStatus.ACTIVE);
        role.setName(request.getAccountName());
        roleRepository.save(role);

        List<Permission> permissions = permissionRepository.getPermissions(request.getBusinessRole());
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Permission permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permission.getId());
            rolePermission.setIsView(permission.getIsView());
            rolePermission.setIsApproval(permission.getIsApproval());
            rolePermission.setIsWrite(permission.getIsWrite());
            rolePermission.setIsDecision(permission.getIsDecision());
            rolePermissions.add(rolePermission);
        }
        rolePermissionRepository.saveAll(rolePermissions);

        User user = new User();
        user.setCode(code);
        user.setPhone(request.getAccountPhone());
        user.setName(request.getAccountName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(ActiveStatus.ACTIVE);
        user.setRoleId(role.getId());
        user.setRole(role);
        userRepository.save(user);

        UserDetailRes userLoginRes = getUserRes(user);
        userLoginRes.setAccessToken(jwtTokenProvider.generateTokenRs256(String.valueOf(user.getId()), jwtExpirationInMs));
        return userLoginRes;

    }

    @Override
    public BaseResponse<List<UserListRes>> getUsers(ActiveStatus status, String searchKeyword, int page) {
        User user = getUser(PermissionKey.READ, PermissionType.ACCOUNT);

        long count = userRepository.countUser(status, searchKeyword, user.getRole());
        List<UserListRes> users = userRepository.getUsers(status, searchKeyword, page, user.getRole());
        return new BaseResponse<>(users, count, page);
    }

    @Override
    public BaseResponse<UserDetailRes> getDetailMember(int accountId) {
        User user = getUser(PermissionKey.READ, PermissionType.ACCOUNT);

        UserDetailRes userRes = userRepository.getProfileUser(accountId, user.getRole());
        if (userRes == null) {
            throw new BusinessException(Translator.toLocale("id_not_exist"), HttpStatus.NOT_FOUND);
        }
        return new BaseResponse<>(userRes);
    }

    @Override
    public UserDetailRes getMyProfile() {
        User user = getUser();

        return getUserRes(user);
    }
    
    private UserDetailRes getUserRes(User user) {
        RoleDetail roleDetail;
        if (user.getRole() == null) {
            roleDetail = roleRepository.getRoleById(user.getRoleId());
        } else {
            roleDetail = new RoleDetail();
            roleDetail.setRoleId(user.getRole().getId());
            roleDetail.setRoleType(user.getRole().getType());
            roleDetail.setObjectId(user.getRole().getObjectId());
            roleDetail.setRoleName(user.getRole().getName());
        }
        return UserDetailRes.builder()
                .id(user.getId())
                .code(user.getCode())
                .name(user.getName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(roleDetail)
                .birthday(user.getBirthday())
                .permissions(roleRepository.getPermissions(user.getRoleId(), user.getRole()))
                .build();
    }

    private String generateCode(int count) {
        String code;
        boolean exists;

        do {
            code = Util.randomString(count);
            exists = userRepository.existsByCode(code);
        } while (exists);
        return code;
    }
}
