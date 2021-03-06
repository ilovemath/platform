package wang.hijack.mfe.db.service;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.hijack.mfe.db.model.dto.RoleDto;
import wang.hijack.mfe.db.model.entity.AppEntity;
import wang.hijack.mfe.db.model.entity.MenuEntity;
import wang.hijack.mfe.db.model.entity.RoleEntity;
import wang.hijack.mfe.db.model.exception.EntityNotExistException;
import wang.hijack.mfe.db.model.vo.RoleVo;
import wang.hijack.mfe.db.repository.AppRepository;
import wang.hijack.mfe.db.repository.MenuRepository;
import wang.hijack.mfe.db.repository.RoleRepository;
import wang.hijack.mfe.db.repository.UserRepository;
import wang.hijack.mfe.db.util.BeanUtils;
import wang.hijack.mfe.db.util.FileUtils;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Jack
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserRepository userRepository;

    public RoleEntity findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Set<AppEntity> getAppsByRoleId(Long id) {
        Set<AppEntity> apps = new HashSet<>();
        roleRepository.findById(id).ifPresent(role ->
                apps.addAll(role.getMenus().stream()
                        .filter(menu -> menu.getApp() != null)
                        .map(MenuEntity::getApp)
                        .collect(Collectors.toSet())));
        return apps;
    }

    public RoleVo upsert(RoleDto dto) {
        if (dto.getId() == null) {
            return insert(dto);
        } else {
            if (roleRepository.existsById(dto.getId())) {
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

    public RoleVo insert(RoleDto dto) {
        RoleEntity entity = dto.toEntity();
        return save(entity, dto);
    }

    public RoleVo update(RoleDto dto) throws EntityNotExistException {
        RoleEntity entity = roleRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotExistException("Role", dto.getId()));
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        return save(entity, dto);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    public void deleteAll(List<Long> ids) {
        roleRepository.deleteAll(ids.stream().map(RoleEntity::new).collect(Collectors.toList()));
    }

    public Page<RoleVo> list(String name, List<Long> createTime, Pageable pageable) {
        return roleRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.conjunction());
            if (name != null) {
                String pattern = "%" + name + "%";
                list.add(cb.like(root.get("name"), pattern));
            }
            if (createTime != null && createTime.size() == 2) {
                list.add(cb.ge(root.get("createTime"), createTime.get(0)));
                list.add(cb.le(root.get("createTime"), createTime.get(1)));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return cb.and(list.toArray(predicates));
        }, pageable).map(RoleVo::new);
    }

    public List<RoleVo> listAll() {
        return roleRepository.findAll().stream().map(RoleVo::new).collect(Collectors.toList());
    }

    public void download(HttpServletResponse response) {
        FileUtils.download(roleRepository.findAll(), response);
    }

    private RoleVo save(RoleEntity entity, RoleDto dto) {
        inflateMenus(entity, dto);
        roleRepository.save(entity);
        return new RoleVo(entity);
    }

    private void inflateMenus(RoleEntity entity, RoleDto dto) {
        if (dto.getMenuIds() != null) {
            var menus = menuRepository.findAllById(dto.getMenuIds());
            entity.setMenus(new HashSet<>(menus));
        }
    }
}
