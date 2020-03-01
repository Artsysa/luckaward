package com.wxprogram.luckaward.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 * @breif:抽字信息
 * @Author: ArDaBao
 * @Date: 2020/2/6 13:25
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LuckZi implements Serializable {
    private String msg;
}
