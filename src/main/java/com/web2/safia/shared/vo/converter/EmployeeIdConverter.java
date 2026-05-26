package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.EmployeeId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmployeeIdConverter implements AttributeConverter<EmployeeId, String> {
	@Override
	public String convertToDatabaseColumn(EmployeeId attribute) {
		return attribute.value();
	}

	@Override
	public EmployeeId convertToEntityAttribute(String dbData) {
		return new EmployeeId(dbData);
	}
}
