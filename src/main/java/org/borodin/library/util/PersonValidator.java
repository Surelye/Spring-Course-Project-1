package org.borodin.library.util;

import org.borodin.library.dao.PersonDAO;
import org.borodin.library.models.Person;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(@NonNull Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        Person person = (Person) o;
        if (personDAO.show(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Person already exists");
        }
    }
}
