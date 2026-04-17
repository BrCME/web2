package com.web2.safia.shared.validation;

import java.util.Optional;

public interface Validator<T> {
	boolean validate(Optional<T> t);
}
