// src/main/java/com/example/odds_receiver/DTO/BindingDTO.java

package com.example.odds_receiver.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BindingDTO {
    private String league1Name;
    private String league2Name;
    private String homeTeam1Name;
    private String homeTeam2Name;
    private String awayTeam1Name;
    private String awayTeam2Name;

    // 新增的数据来源字段
    private Integer dataSource1;
    private Integer dataSource2;
}
