package com.web2.safia.validations;

import java.util.Optional;

public interface Validator<T> {
	boolean validate(Optional<T> t);
}
