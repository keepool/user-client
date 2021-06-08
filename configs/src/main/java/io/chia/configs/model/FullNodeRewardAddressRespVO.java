package io.chia.configs.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FullNodeRewardAddressRespVO implements Serializable {
    private String pooTargetAddress;
    private String farmerTargetAddress;
    private List<FullNoteConfigVO> farmerFullNodeHost;
}
