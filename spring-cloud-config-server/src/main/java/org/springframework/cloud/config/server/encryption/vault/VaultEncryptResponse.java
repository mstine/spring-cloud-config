package org.springframework.cloud.config.server.encryption.vault;

/**
 * @author Matt Stine
 */
public class VaultEncryptResponse {

    private VaultEncryptData data;

    public VaultEncryptData getData() {
        return data;
    }

    public void setData(VaultEncryptData data) {
        this.data = data;
    }

    public class VaultEncryptData {
        private String ciphertext;

        public String getCiphertext() {
            return ciphertext;
        }

        public void setCiphertext(String ciphertext) {
            this.ciphertext = ciphertext;
        }
    }
}
