package wang.hijack.mfe.gateway.validator;

import wang.hijack.mfe.gateway.model.dto.MenuDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Jack
 */
public class MenuValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MenuDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
