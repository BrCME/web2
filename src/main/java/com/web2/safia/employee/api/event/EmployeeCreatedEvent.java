package com.web2.safia.employee.api.event;

import com.web2.safia.shared.entity.Employee;

public record EmployeeCreatedEvent(Employee manager) {
}
