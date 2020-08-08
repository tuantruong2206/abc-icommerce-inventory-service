package au.com.nab.icommerce.inventory.validation;

import au.com.nab.icommerce.base.enumeration.MessageCodeEnum;
import au.com.nab.icommerce.base.exception.NABException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ProductValidator {

    public static void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            handleError(bindingResult.getAllErrors().get(0));
        }
    }

    private static void handleError(ObjectError error) {
        ConstraintViolation violation = error.unwrap(ConstraintViolation.class);
        String defaultMessage = violation.getMessage();
        if (StringUtils.isEmpty(defaultMessage)) {
            throw new NABException(
                    MessageCodeEnum.COMMON_ERROR_017,
                    HttpStatus.BAD_REQUEST,
                    violation.getPropertyPath(),
                    violation.getInvalidValue()
            );
        } else {
            throw new NABException(
                    MessageCodeEnum.COMMON_ERROR_018,
                    HttpStatus.BAD_REQUEST,
                    violation.getPropertyPath(),
                    violation.getInvalidValue()
            );
        }
    }
}
