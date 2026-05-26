package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.UserId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserIdConverter implements AttributeConverter<UserId, String> {
	@Override
	public String convertToDatabaseColumn(UserId attribute) {
		return attribute.value();
	}

	@Override
	public UserId convertToEntityAttribute(String dbData) {
		return new UserId(dbData);
	}
}
