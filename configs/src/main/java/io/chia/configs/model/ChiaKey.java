package io.chia.configs.model;

import lombok.Data;

@Data
public class ChiaKey {
    private String poolPublicKey;
    private String farmerPublicKey;
    private String address;
    private String fingerprint;
}
