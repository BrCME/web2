package com.web2.safia.employee.api.event;

import com.web2.safia.employee.internal.Employee;

public record EmployeeCreatedEvent(Employee manager) {
}
