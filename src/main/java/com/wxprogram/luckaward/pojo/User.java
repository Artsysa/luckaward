package com.wxprogram.luckaward.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/3 20:25
 * @Month:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User implements Serializable {

    private String openid;
    private String nickname;
    private int sex;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String sessionid;
    private int qualified;
    private String ip;
    private String registertime;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("openid='").append(openid).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", sex=").append(sex);
        sb.append(", city='").append(city).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", headimgurl='").append(headimgurl).append('\'');
        sb.append(", sessionid='").append(sessionid).append('\'');
        sb.append(", qualified=").append(qualified);
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", registertime='").append(registertime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
