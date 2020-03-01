package com.wxprogram.luckaward.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 16:46
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Statistic implements Serializable {
    private Integer usercount;
    private Integer rewardcount;
    private Integer scancount;

}
