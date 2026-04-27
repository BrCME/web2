package com.web2.safia.work.api.dto;

import java.time.Duration;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponseDto;
import com.web2.safia.shared.entity.Work;
import com.web2.safia.task.api.dto.BriefTaskResponseDto;

public record BriefWorkResponseDto(
	UUID id,
	String description,
	BriefEmployeeResponseDto employee,
	BriefTaskResponseDto task,
	Duration duration
) {

	public BriefWorkResponseDto(Work work) {
		this(
			work.getId(),
			work.getDescription(),
			new BriefEmployeeResponseDto(work.getEmployee()),
			new BriefTaskResponseDto(work.getTask()),
			Duration.between(work.getStartedAt(), work.getEndedAt())
		);
	}
}