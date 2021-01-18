package wang.hijack.mfe.gateway.service;

import wang.hijack.mfe.gateway.model.dto.AppDto;
import wang.hijack.mfe.gateway.model.entity.AppEntity;
import wang.hijack.mfe.gateway.model.exception.EntityNotExistException;
import wang.hijack.mfe.gateway.model.vo.AppVo;
import wang.hijack.mfe.gateway.repository.AppRepository;
import wang.hijack.mfe.gateway.util.BeanUtils;
import wang.hijack.mfe.gateway.util.FileUtils;
import wang.hijack.mfe.gateway.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Service
public class AppService {
    @Autowired
    AppRepository appRepository;

    public AppVo upsert(AppDto dto) {
        if (dto.getId() == null) {
            return insert(dto);
        } else {
            if (appRepository.existsById(dto.getId())) {
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

    public AppVo insert(AppDto dto) {
        AppEntity entity = dto.toEntity();
        return save(entity, dto);
    }

    public AppVo update(AppDto dto) throws EntityNotExistException {
        AppEntity entity = appRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotExistException("App", dto.getId()));
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        return save(entity, dto);
    }

    public void delete(Long id) {
        appRepository.deleteById(id);
    }

    public List<AppVo> list() {
        return UserHolder.apps().stream().map(AppVo::new).collect(Collectors.toList());
    }

    public List<AppVo> listAll() {
        return appRepository.findAll().stream().map(AppVo::new).collect(Collectors.toList());
    }

    public void download(HttpServletResponse response) {
        FileUtils.download(appRepository.findAll(), response);
    }

    private AppVo save(AppEntity entity, AppDto dto) {
        return new AppVo(appRepository.save(entity));
    }
}
