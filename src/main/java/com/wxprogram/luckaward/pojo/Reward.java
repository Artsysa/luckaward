package com.wxprogram.luckaward.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 14:20
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reward implements Serializable {
    private String openid;
    private String name;
    private String time;
}
