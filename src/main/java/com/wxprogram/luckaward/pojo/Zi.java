package com.wxprogram.luckaward.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 13:46
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Zi implements Serializable {
  //  private String openid;
    private Integer remain;
    private Integer t1;
    private Integer t2;
    private Integer t3;
    private Integer t4;
    private Integer t5;
    private Integer t6;
    private Integer t7;
    private Integer t8;
    private Integer t9;
    private Integer t10;
    private Integer total;
}
