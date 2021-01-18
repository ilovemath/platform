package wang.hijack.mfe.gateway.service;

import wang.hijack.mfe.gateway.model.dto.DeptDto;
import wang.hijack.mfe.gateway.model.entity.DeptEntity;
import wang.hijack.mfe.gateway.model.exception.EntityNotExistException;
import wang.hijack.mfe.gateway.model.vo.DeptVo;
import wang.hijack.mfe.gateway.repository.DeptRepository;
import wang.hijack.mfe.gateway.util.BeanUtils;
import wang.hijack.mfe.gateway.util.FileUtils;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Service
public class DeptService {
    @Autowired
    private DeptRepository deptRepository;

    private List<DeptVo> deptList() {
        return Arrays.asList(
                new DeptVo(DeptDto.builder().id(0L).orgNo("0").orgName("总行机构").build().toEntity()),
                new DeptVo(DeptDto.builder().id(1L).orgNo("1").orgName("一级分行").build().toEntity()),
                new DeptVo(DeptDto.builder().id(2L).orgNo("2").orgName("二级分行").build().toEntity()),
                new DeptVo(DeptDto.builder().id(3L).orgNo("3").orgName("支行").build().toEntity()),
                new DeptVo(DeptDto.builder().id(4L).orgNo("4").orgName("网点").build().toEntity())
        );
    }

    public DeptVo upsert(DeptDto dto) {
        if (dto.getId() == null) {
            return insert(dto);
        } else {
            if (deptRepository.existsById(dto.getId())) {
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

    public DeptVo insert(DeptDto dto) {
        DeptEntity entity = dto.toEntity();
        return save(entity, dto);
    }

    public DeptVo update(DeptDto dto) throws EntityNotExistException {
        DeptEntity entity = deptRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotExistException("Dept", dto.getId()));
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        return save(entity, dto);
    }

    public void delete(Long id) {
        deptRepository.deleteById(id);
    }

    public void deleteAll(List<Long> ids) {
        deptRepository.deleteAll(ids.stream().map(DeptEntity::new).collect(Collectors.toList()));
    }

    public List<DeptVo> listTree() {
        var deptList = deptList();
        for (var dept : deptRepository.findAll()) {
            deptList.get(dept.getLevel()).addChild(new DeptVo(dept));
        }
        return deptList;
    }

    public List<DeptVo> list() {
        return deptRepository.findAll().stream().map(DeptVo::new).collect(Collectors.toList());
    }

    public Map<String, String> listMap() {
        Map<String, String> map = new HashMap<>();
        for (DeptVo deptVo : list()) {
            map.put(deptVo.getOrgNo(), deptVo.getName());
        }
        return map;
    }

    public List<DeptVo> listByName(String name) {
        var deptList = deptList();
        name = "%" + name + "%";
        for (var dept : deptRepository.findByOrgNameLike(name)) {
            deptList.get(dept.getLevel()).addChild(new DeptVo(dept));
        }
        return deptList;
    }

    public void download(HttpServletResponse response) {
        FileUtils.download(deptRepository.findAll(), response);
    }

    private DeptVo save(DeptEntity entity, DeptDto dto) {
        return new DeptVo(deptRepository.save(entity));
    }
}
