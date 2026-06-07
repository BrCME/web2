package com.web2.safia.shared.util;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomIdUtils {
	private static final String SEPARATOR = "$";
	
	private static String customName;
	private static Integer customInstance;
	private static String customVersion;

	@Value("${custom.id-generator.name:Custom-Name}")
	private void setCustomName(String customName) {
		this.customName = customName;
	}

	@Value("${custom.id-generator.instance:0}")
	private void setCustomInstance(Integer customInstance) {
		this.customInstance = customInstance;
	}

	@Value("${custom.id-generator.version:0.0}")
	private void setCustomVersion(String customVersion) {
		this.customVersion = customVersion;
	}

	public static String createId(String value) {
		var instant = Long.valueOf(Instant.now().toEpochMilli());
		
		return customName.concat(SEPARATOR)
				.concat(value).concat(SEPARATOR)
				.concat(instant.toString()).concat(SEPARATOR)
				.concat(customInstance.toString()).concat(SEPARATOR)
				.concat(customVersion);
	}
}
