package com.web2.safia.services;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
	AuditService() {}
	
	@EventListener()
	void updateHistory() {

	}
}
