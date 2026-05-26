package com.web2.safia.shared.util;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomIdUtils {
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
		return customName.concat(":")
				.concat(value).concat(":")
				.concat(Instant.now().toString()).concat("-")
				.concat(customInstance.toString()).concat(":")
				.concat(customVersion);
	}
}
