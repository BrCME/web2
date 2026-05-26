package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.ProjectId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProjectIdConverter implements AttributeConverter<ProjectId, String> {
	@Override
	public String convertToDatabaseColumn(ProjectId attribute) {
		return attribute.value();
	}

	@Override
	public ProjectId convertToEntityAttribute(String dbData) {
		return new ProjectId(dbData);
	}
}
