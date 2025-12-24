package mobibe.mobilebe.repository.userRepository;

import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.response.role.RoleDetail;
import mobibe.mobilebe.dto.response.user.UserDetailRes;
import mobibe.mobilebe.dto.response.user.UserListRes;
import mobibe.mobilebe.entity.role.QRole;
import mobibe.mobilebe.entity.role.Role;
import mobibe.mobilebe.entity.user.QUser;
import mobibe.mobilebe.entity.user.User;
import mobibe.mobilebe.repository.BaseRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mobibe.mobilebe.repository.BaseRepository;

import static mobibe.mobilebe.util.Constants.PAGE_SIZE;

@Slf4j
@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryCustom {
    private final QUser qUser = QUser.user;
    private final QRole qRole = QRole.role;

    @Override
    public User loginByEmail(String email) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.email.eq(email));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .where(builder)
                .select(qUser)
                .fetchOne();
    }

    @Override
    public User getUserByPhone(String phone) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.phone.eq(phone));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .where(builder)
                .select(qUser)
                .fetchOne();
    }

    @Override
    public boolean existsByCode(String code) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qUser.code.eq(code));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .where(builder)
                .select(qUser.id)
                .fetchFirst() != null;
    }

    @Override
    public long countUser(ActiveStatus status, String searchKeyword, Role role) {

        BooleanBuilder builder = new BooleanBuilder();
        // builder.and(qRole.objectId.eq(role.getObjectId()));
        // builder.and(qRole.type.eq(role.getType()));
        if (status != null) {
            builder.and(qUser.status.eq(status));
        }
        builder.and(qUser.deleted.eq(false));
        if (searchKeyword != null) {
            builder.andAnyOf(
                    qUser.name.contains(searchKeyword),
                    qUser.email.contains(searchKeyword));
        }
        Long count = query().from(qUser)
                .where(builder)
                .select(qUser.id.count())
                .fetchOne();
                
        return count == null ? 0 : count;
    }

    @Override
    public List<UserListRes> getUsers(ActiveStatus status, String searchKeyword, int page, Role role) {

        BooleanBuilder builder = new BooleanBuilder();
        // builder.and(qRole.objectId.eq(role.getObjectId()));
        // builder.and(qRole.type.eq(role.getType()));
        if (status != null) {
            builder.and(qUser.status.eq(status));
        }
        builder.and(qUser.deleted.eq(false));
        if (searchKeyword != null) {
            builder.andAnyOf(
                    qUser.name.contains(searchKeyword),
                    qUser.email.contains(searchKeyword));
        }
        return query().from(qUser)
                .innerJoin(qRole).on(qRole.role.eq(qUser.role))
                .where(builder)
                .select(Projections.fields(UserListRes.class,
                        qUser.id, qUser.code, qUser.name, qUser.email,
                        qUser.address, qUser.phone, qUser.status,
                        qUser.birthday, qUser,
                        Projections.fields(RoleDetail.class,
                                        qRole.role.as("role"),
                                        qRole.objectId,
                                        qRole.type.as("roleType"),
                                        qRole.name.as("roleName"))
                                .as("role"))
                )
                .offset(page * PAGE_SIZE).limit(PAGE_SIZE)
                .orderBy(qUser.id.desc())
                .fetch();
    }

    @Override
    public UserDetailRes getProfileUser(int accountId, Role role) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.id.eq(accountId));
        // builder.and(qRole.objectId.eq(role.getObjectId()));
        // builder.and(qRole.type.eq(role.getType()));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .innerJoin(qRole).on(qRole.role.eq(qUser.role))
                .where(builder)
                .select(Projections.fields(UserDetailRes.class,
                        qUser.id, qUser.code, qUser.phone, qUser.name,
                        qUser.email, qUser.address, qUser.birthday, qUser,
                        qUser.status,
                        Projections.fields(RoleDetail.class,
                                        qRole.role.as("role"),
                                        qRole.objectId,
                                        qRole.type.as("roleType"),
                                        qRole.name.as("roleName"))
                                .as("role"))
                )
                .fetchOne();
    }

    @Override
    public List<Integer> getAllIdToCheckExist(List<Integer> userIds, Role role) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.id.in(userIds));
        // builder.and(qRole.objectId.eq(role.getObjectId()));
        // builder.and(qRole.type.eq(role.getType()));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .innerJoin(qRole).on(qRole.role.eq(qUser.role))
                .where(builder)
                .select(qUser.id)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteUsers(List<Integer> userIds) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.id.in(userIds));
        builder.and(qUser.deleted.eq(false));

        query().update(qUser)
                .set(qUser.deleted, true)
                .where(builder)
                .execute();
    }

    @Override
    public User getUserToUpdate(int userId, Role role) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.id.eq(userId));
        // builder.and(qRole.objectId.eq(role.getObjectId()));
        // builder.and(qRole.type.eq(role.getType()));
        builder.and(qUser.deleted.eq(false));
        return query().from(qUser)
                .innerJoin(qRole).on(qRole.role.eq(qUser.role))
                .where(builder)
                .select(qUser)
                .fetchOne();
    }

}
