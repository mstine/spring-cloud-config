package org.springframework.cloud.config.server.encryption.vault;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.rsa.crypto.RsaKeyHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matt Stine
 */
public class VaultTextEncryptor implements TextEncryptor, RsaKeyHolder {

    private String keyName;
    private String vaultHost;
    private String vaultToken;
    private String publicKey;
    private RestTemplate restTemplate;

    public VaultTextEncryptor(String keyName, String publicKey, String vaultHost, String vaultToken) {
        this.restTemplate = new RestTemplate();

        this.keyName = keyName;
        this.publicKey = publicKey;
        this.vaultHost = vaultHost;
        this.vaultToken = vaultToken;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String encrypt(String text) {
        String base64Encoded = Base64.getEncoder().encodeToString(text.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Vault-Token", vaultToken);

        Map<String, String> body = new HashMap<>();
        body.put("plaintext", base64Encoded);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<VaultEncryptResponse> exchange;
        try {
            exchange = restTemplate.exchange(vaultHost + "/v1/transit/encrypt/" + keyName, HttpMethod.POST, entity, VaultEncryptResponse.class);
        } catch (HttpStatusCodeException e) {
            return null;
        }

        return exchange.getBody().getData().getCiphertext();
    }

    @Override
    public String decrypt(String encryptedText) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Vault-Token", vaultToken);

        Map<String, String> body = new HashMap<>();
        body.put("ciphertext", encryptedText);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<VaultDecryptResponse> exchange;
        try {
            exchange = restTemplate.exchange(vaultHost + "/v1/transit/decrypt/" + keyName, HttpMethod.POST, entity, VaultDecryptResponse.class);
        } catch (HttpStatusCodeException e) {
            return null;
        }

        return new String(Base64.getDecoder().decode(exchange.getBody().getData().getPlaintext()));
    }
}
