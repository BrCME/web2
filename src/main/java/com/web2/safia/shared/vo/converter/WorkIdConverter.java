package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.WorkId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class WorkIdConverter implements AttributeConverter<WorkId, String> {
	@Override
	public String convertToDatabaseColumn(WorkId attribute) {
		return attribute.value();
	}

	@Override
	public WorkId convertToEntityAttribute(String dbData) {
		return new WorkId(dbData);
	}
}
