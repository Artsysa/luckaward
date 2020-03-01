package com.wxprogram.luckaward.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/18 7:45
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserluckSimplify implements Serializable {
    private String nickname;
    private String headimgurl;
}
