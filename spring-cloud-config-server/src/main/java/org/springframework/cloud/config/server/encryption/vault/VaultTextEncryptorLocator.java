package org.springframework.cloud.config.server.encryption.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.server.encryption.TextEncryptorLocator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Matt Stine
 */
public class VaultTextEncryptorLocator implements TextEncryptorLocator {

    @Value("${vault.host}")
    private String vaultHost;

    @Value("${vault.token}")
    private String vaultToken;

    private RestTemplate restTemplate;

    public VaultTextEncryptorLocator() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public TextEncryptor locate(Map<String, String> keys) {
        String name = keys.get("name");
        String profiles = keys.get("profiles");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Vault-Token", vaultToken);

        HttpEntity<VaultKeyResponse> entity = new HttpEntity<VaultKeyResponse>(headers);

        ResponseEntity<VaultKeyResponse> exchange;
        try {
            exchange = restTemplate.exchange(vaultHost + "/v1/transit/keys/" + name + "_" + profiles, HttpMethod.GET, entity, VaultKeyResponse.class);
        } catch (HttpStatusCodeException e) {
            return null;
        }

        VaultKeyResponse body = exchange.getBody();
        return new VaultTextEncryptor(name + "_" + profiles, body.getData().getKey(), vaultHost, vaultToken);
    }

}
