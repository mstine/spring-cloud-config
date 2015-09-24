package org.springframework.cloud.config.server.encryption.vault;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matt Stine
 */
public class VaultKeyResponse {
    private String auth;
    private VaultKeyData data;
    private int leaseDuration;
    private String leaseId;
    private boolean renewable;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public VaultKeyData getData() {
        return data;
    }

    public void setData(VaultKeyData data) {
        this.data = data;
    }

    @JsonProperty("lease_duration")
    public int getLeaseDuration() {
        return leaseDuration;
    }

    public void setLeaseDuration(int leaseDuration) {
        this.leaseDuration = leaseDuration;
    }

    @JsonProperty("lease_id")
    public String getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public class VaultKeyData {
        private String cipherMode;
        private String key;
        private String name;

        @JsonProperty("cipher_mode")
        public String getCipherMode() {
            return cipherMode;
        }

        public void setCipherMode(String cipherMode) {
            this.cipherMode = cipherMode;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
