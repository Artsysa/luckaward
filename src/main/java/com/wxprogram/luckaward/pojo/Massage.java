package com.wxprogram.luckaward.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 18:01
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Massage implements Serializable {
    private String code;
    private String msg;
    private String token;
    private Object obj;

    public Massage success(){
        this.code="200";
        this.msg="success";
        return this;
    }
    public Massage error(){
        this.code="500";
        this.msg="error";
        return this;
    }

}
