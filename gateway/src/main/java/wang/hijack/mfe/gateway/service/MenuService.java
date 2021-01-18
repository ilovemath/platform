package wang.hijack.mfe.gateway.service;

import wang.hijack.mfe.gateway.model.dto.MenuDto;
import wang.hijack.mfe.gateway.model.entity.MenuEntity;
import wang.hijack.mfe.gateway.model.enums.MenuType;
import wang.hijack.mfe.gateway.model.exception.EntityNotExistException;
import wang.hijack.mfe.gateway.model.vo.MenuVo;
import wang.hijack.mfe.gateway.repository.AppRepository;
import wang.hijack.mfe.gateway.repository.MenuRepository;
import wang.hijack.mfe.gateway.util.BeanUtils;
import wang.hijack.mfe.gateway.util.UserHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Service
public class MenuService {
    static final String CHILDREN = "children";
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private AppRepository appRepository;

    public MenuVo upsert(MenuDto dto) {
        if (dto.getId() == null) {
            return insert(dto);
        } else {
            if (menuRepository.existsById(dto.getId())) {
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

    public MenuVo insert(MenuDto dto) {
        MenuEntity entity = dto.toEntity();
        return save(entity, dto);
    }

    public MenuVo update(MenuDto dto) throws EntityNotExistException {
        MenuEntity entity = menuRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotExistException("Menu", dto.getId()));
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        return save(entity, dto);
    }

    private void inflateApp(MenuEntity entity, MenuDto dto) {
        Long appId = dto.getAppId();
        MenuType type = entity.getType();
        if (appId != null && type == MenuType.MENU) {
            appRepository.findById(appId).ifPresent(entity::setApp);
        }
    }

    private MenuVo save(MenuEntity entity, MenuDto dto) {
        inflateApp(entity, dto);
        return new MenuVo(menuRepository.save(entity));
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    public List<MenuVo> list() {
        return UserHolder.menus().stream().map(MenuVo::new).collect(Collectors.toList());
    }

    public List<MenuVo> listAll() {
        return menuRepository.findAll().stream().map(MenuVo::new).collect(Collectors.toList());
    }

    private MenuType getType(Long parentId, boolean hasChild) {
        if (parentId == null) {
            return MenuType.TOP;
        } else if (hasChild) {
            return MenuType.DIR;
        } else {
            return MenuType.MENU;
        }
    }

    private MenuDto makeDto(JsonNode node, Long appId, Long menuId, Long parentId, int sort) {
        MenuType type = getType(parentId, node.has(CHILDREN));
        return MenuDto.builder()
                .icon(node.get("icon").asText())
                .path(node.get("path").asText())
                .title(node.get("title").asText())
                .id(menuId).parentId(parentId)
                .appId(appId).type(type).sort(sort).build();
    }

    private Long parseAppId(JsonNode node) {
        try {
            String path = node.get("path").asText();
            path = path.split("/")[1];
            return appRepository.findByPath(path).getAppId();
        } catch (Exception ignore) {
            return null;
        }
    }

    public List<MenuDto> readJsonTree(String jsonTree) {
        Queue<JsonNode> queue = new LinkedList<>();
        List<MenuDto> menuDtoList = new ArrayList<>();
        try {
            long id = 0;
            Map<JsonNode, Long> idMap = new HashMap<>(100);
            queue.offer(mapper.readTree(jsonTree));
            while (!queue.isEmpty()) {
                JsonNode node = queue.poll();
                if (node != null && node.isArray()) {
                    int sort = 0;
                    Long parentId = idMap.get(node);
                    for (JsonNode item : node) {
                        Long appId = null;
                        Long menuId = ++id;
                        JsonNode children = item.get(CHILDREN);
                        if (children != null) {
                            queue.offer(children);
                            idMap.put(children, menuId);
                        } else {
                            appId = parseAppId(item);
                        }
                        MenuDto dto = makeDto(item, appId, menuId, parentId, ++sort);
                        menuDtoList.add(dto);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return menuDtoList;
    }

    public List<MenuVo> getMenuTree() {
        Set<MenuEntity> menus = UserHolder.menus();
        Map<Long, MenuVo> menuVoMap = menus.stream()
                .collect(Collectors.toMap(MenuEntity::getMenuId, MenuVo::new));
        List<MenuVo> topMenu = new ArrayList<>();
        for (MenuEntity menu : menus) {
            Long id = menu.getMenuId();
            Long pid = menu.getParentId();
            MenuType type = menu.getType();
            if (type == MenuType.TOP) {
                topMenu.add(menuVoMap.get(id));
            } else if (pid != null && type != MenuType.BUTTON) {
                menuVoMap.get(pid).addChild(menuVoMap.get(id));
            }
        }
        menuVoMap.forEach((id, menu) -> {
            Collections.sort(menu.getChildren());
        });
        return topMenu;
    }

}