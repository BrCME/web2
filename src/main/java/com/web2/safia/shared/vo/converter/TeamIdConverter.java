package com.web2.safia.shared.vo.converter;

import com.web2.safia.shared.vo.TeamId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TeamIdConverter implements AttributeConverter<TeamId, String> {
	@Override
	public String convertToDatabaseColumn(TeamId attribute) {
		return attribute.value();
	}

	@Override
	public TeamId convertToEntityAttribute(String dbData) {
		return new TeamId(dbData);
	}
}
