package org.springframework.cloud.config.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.encryption.TextEncryptorLocator;
import org.springframework.cloud.config.server.encryption.vault.VaultTextEncryptorLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigServerApplication.class).properties(
				"spring.config.name=configserver", "debug=true").run(args);
	}

	@Bean
	public TextEncryptorLocator vaultTextEncryptorLocator() {
		return new VaultTextEncryptorLocator();
	}

}
