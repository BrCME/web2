package com.web2.safia.shared.util;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;

import com.web2.safia.shared.entity.CommitId;
import com.web2.safia.shared.entity.EmployeeId;
import com.web2.safia.shared.entity.ProjectId;
import com.web2.safia.shared.entity.RoleId;
import com.web2.safia.shared.entity.TaskId;
import com.web2.safia.shared.entity.TeamId;
import com.web2.safia.shared.entity.WorkId;

public class CustomIdUtils {
	@Value("${custom.id-generator.name:Custom-Name}")
	private String customName;

	@Value("${custom.id-generator.instance:0}")
	private Integer customInstance;

	@Value("${custom.id-generator.version:0.0}")
	private String customVersion;

	private static CustomIdUtils instance;

	private CustomIdUtils() {
	}

	public static synchronized CustomIdUtils getInstance() {
		if (instance == null) {
			instance = new CustomIdUtils();
		}

		return instance;
	}

	private String createId(String value) {
		return customName.concat(":")
				.concat(value).concat(":")
				.concat(Instant.now().toString()).concat("-")
				.concat(customInstance.toString()).concat(":")
				.concat(customVersion);
	}

	public CommitId createCommitId() {
		return new CommitId(createId("Commit"));
	}

	public CommitId createCommitId(String value) {
		return new CommitId(value);
	}

	public EmployeeId createEmployeeId() {
		return new EmployeeId(createId("Employee"));
	}

	public EmployeeId createEmployeeId(String value) {
		return new EmployeeId(value);
	}

	public ProjectId createProjectId() {
		return new ProjectId(createId("Project"));
	}

	public ProjectId createProjectId(String value) {
		return new ProjectId(value);
	}

	public RoleId createRoleId() {
		return new RoleId(createId("Role"));
	}

	public RoleId createRoleId(String value) {
		return new RoleId(value);
	}

	public TaskId createTaskId() {
		return new TaskId(createId("Task"));
	}

	public TaskId createTaskId(String value) {
		return new TaskId(value);
	}

	public TeamId createTeamId() {
		return new TeamId(createId("Team"));
	}

	public TeamId createTeamId(String value) {
		return new TeamId(value);
	}


	public WorkId createWorkId() {
		return new WorkId(createId("Work"));
	}

	public WorkId createWorkId(String value) {
		return new WorkId(value);
	}
}
