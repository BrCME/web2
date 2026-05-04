package com.web2.safia.work.api.dto;

import java.time.Duration;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponse;
import com.web2.safia.shared.entity.Work;
import com.web2.safia.task.api.dto.BriefTaskResponse;

public record BriefWorkResponse(
	UUID id,
	String description,
	BriefEmployeeResponse employee,
	BriefTaskResponse task,
	Duration duration
) {

	public BriefWorkResponse(Work work) {
		this(
			work.getId(),
			work.getDescription(),
			new BriefEmployeeResponse(work.getEmployee()),
			new BriefTaskResponse(work.getTask()),
			Duration.between(work.getStartedAt(), work.getEndedAt())
		);
	}
}