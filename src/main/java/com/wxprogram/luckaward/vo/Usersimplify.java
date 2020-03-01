package com.wxprogram.luckaward.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/18 7:42
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usersimplify implements Serializable {
   // private String openid;
    private String nickname;
    private int sex;
    private String headimgurl;
    private int qualified;
}
