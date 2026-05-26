package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.Username;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UsernameConverter implements AttributeConverter<Username, String> {
	@Override
	public String convertToDatabaseColumn(Username attribute) {
		return attribute.value();
	}

	@Override
	public Username convertToEntityAttribute(String dbData) {
		return new Username(dbData);
	}
}
