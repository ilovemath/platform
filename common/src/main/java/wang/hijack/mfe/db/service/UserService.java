package wang.hijack.mfe.db.service;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wang.hijack.mfe.db.model.dto.UserDto;
import wang.hijack.mfe.db.model.entity.UserEntity;
import wang.hijack.mfe.db.model.exception.EntityNotExistException;
import wang.hijack.mfe.db.model.vo.UserVo;
import wang.hijack.mfe.db.repository.AppRepository;
import wang.hijack.mfe.db.repository.RoleRepository;
import wang.hijack.mfe.db.repository.UserRepository;
import wang.hijack.mfe.db.util.BeanUtils;
import wang.hijack.mfe.db.util.FileUtils;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AppRepository appRepository;

    public UserVo findByUsername(String username) {
        return new UserVo(userRepository.findByUsername(username));
    }

    public UserVo upsert(UserDto dto) {
        if (dto.getId() == null) {
            return insert(dto);
        } else {
            if (userRepository.existsById(dto.getId())) {
                try {
                    return update(dto);
                } catch (EntityNotExistException ignored) {
                    return null;
                }
            } else {
                return insert(dto);
            }
        }
    }

    public UserVo insert(UserDto dto) {
        UserEntity entity = dto.toEntity();
        return save(entity, dto);
    }

    public UserVo update(UserDto dto) throws EntityNotExistException {
        UserEntity entity = userRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotExistException("User", dto.getId()));
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        return save(entity, dto);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll(List<Long> ids) {
        userRepository.deleteAll(ids.stream().map(UserEntity::new).collect(Collectors.toList()));
    }

    public Page<UserVo> list(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserVo::new);
    }

    public Page<UserVo> list(String name, Boolean enabled, List<Long> createTime, Pageable pageable) {
        return userRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.conjunction());
            if (name != null) {
                String pattern = "%" + name + "%";
                list.add(cb.like(root.get("nickname"), pattern));
            }
            if (enabled != null) {
                list.add(cb.equal(root.get("enabled"), enabled));
            }
            if (createTime != null && createTime.size() == 2) {
                list.add(cb.ge(root.get("createTime"), createTime.get(0)));
                list.add(cb.le(root.get("createTime"), createTime.get(1)));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return cb.and(list.toArray(predicates));
        }, pageable).map(UserVo::new);
    }

    public void download(HttpServletResponse response) {
        FileUtils.download(userRepository.findAll(), response);
    }

    private UserVo save(UserEntity entity, UserDto dto) {
        inflateRoles(entity, dto);
        return new UserVo(userRepository.save(entity));
    }

    private void inflateRoles(UserEntity entity, UserDto dto) {
        if (dto.getRoleIds() != null) {
            var roleEntities = roleRepository.findAllById(dto.getRoleIds());
            entity.setRoles(new HashSet<>(roleEntities));
        }
    }
}