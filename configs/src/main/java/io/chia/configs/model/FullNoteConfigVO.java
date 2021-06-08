package io.chia.configs.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FullNoteConfigVO implements Serializable {

    /**
     * 自增编号
     */
    private Integer id;

    /**
     * 全节点ip
     */
    private String fullNodeIp;

    /**
     * 全节点端口
     */
    private Integer port;

    /**
     * 全节点地址
     */
    private String address;

    /**
     * 全节点链接的farmer数量
     */
    private Integer farmerCount;

    /**
     * 状态 1:上线 0:下线
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
