package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.TaskId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TaskIdConverter implements AttributeConverter<TaskId, String> {
	@Override
	public String convertToDatabaseColumn(TaskId attribute) {
		return attribute.value();
	}

	@Override
	public TaskId convertToEntityAttribute(String dbData) {
		return new TaskId(dbData);
	}
}
