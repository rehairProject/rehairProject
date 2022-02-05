package com.rehair.rehair.validator;

import com.rehair.rehair.domain.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class EventValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Event event = (Event) obj;
        if (StringUtils.isEmpty(event.getTitle())) {
            errors.rejectValue("title", "key","제목을 입력하세요.");
        }
        if (StringUtils.isEmpty(event.getContent())) {
            errors.rejectValue("content", "key","내용을 입력하세요.");
        }
    }
}
