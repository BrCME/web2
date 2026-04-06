package com.web2.safia.employee.events;

import com.web2.safia.employee.internal.Employee;

public record GetManagerByIdResponseEvent(Employee manager) {
}
