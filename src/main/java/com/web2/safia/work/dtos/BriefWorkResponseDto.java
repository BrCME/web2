package com.web2.safia.work.dtos;

import java.time.Duration;
import java.util.UUID;

import com.web2.safia.employee.dtos.BriefEmployeeResponseDto;
import com.web2.safia.task.dtos.BriefTaskResponseDto;
import com.web2.safia.work.Work;

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