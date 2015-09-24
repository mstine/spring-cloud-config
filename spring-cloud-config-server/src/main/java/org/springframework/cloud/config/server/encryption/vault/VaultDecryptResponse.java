package org.springframework.cloud.config.server.encryption.vault;

/**
 * @author Matt Stine
 */
public class VaultDecryptResponse {

    private VaultDecryptData data;

    public VaultDecryptData getData() {
        return data;
    }

    public void setData(VaultDecryptData data) {
        this.data = data;
    }

    public class VaultDecryptData {
        private String plaintext;

        public String getPlaintext() {
            return plaintext;
        }

        public void setPlaintext(String plaintext) {
            this.plaintext = plaintext;
        }
    }
}
