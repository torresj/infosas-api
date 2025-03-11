package com.torresj.infosas.validators;

import com.torresj.infosas.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestValidator {
    public void validate(String surname, String dni){
        if(
            (StringUtils.isBlank(dni) && StringUtils.isBlank(surname)) ||
            (!StringUtils.isBlank(dni) && !StringUtils.isBlank(surname))
        ){
            throw new BadRequestException();
        }
    }
}
