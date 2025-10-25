package com.web2.safia.models.builders;

import java.time.LocalDate;

import com.web2.safia.models.Employee;

public class EmployeeBuilder extends BaseBuilder<Employee> {
	public EmployeeBuilder withName(String name) {
		instance.setName(name);
		return this;
	}

	public EmployeeBuilder withEmail(String email) {
		instance.setEmail(email);
		return this;

	}

	public EmployeeBuilder withPassword(String password) {
		instance.setPassword(password);
		return this;

	}

	public EmployeeBuilder withBirthDate(LocalDate birthDate) {
		instance.setBirthDate(birthDate);
		return this;
	}

	@Override
	public EmployeeBuilder builder() {
		instance = new Employee();
		return this;
	}

	@Override
	public Employee build() {
		var builded = instance;
		reset();
		return builded;
	}
}
