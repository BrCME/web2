package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.RoleId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleIdConverter implements AttributeConverter<RoleId, String> {
	@Override
	public String convertToDatabaseColumn(RoleId attribute) {
		return attribute.value();
	}

	@Override
	public RoleId convertToEntityAttribute(String dbData) {
		return new RoleId(dbData);
	}
}
