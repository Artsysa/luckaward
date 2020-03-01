package com.wxprogram.luckaward.enumlist;

import lombok.Getter;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/3/1 10:55
 * @Month:03
 *

 */
public enum Ziname {
    t1("t1","学"),t2("t2","车"),t3("t3","选"),
    t4("t4","驾"),t5("t5","校"),
    t6("t6","就"),t7("t7","选"),t8("t8","乔"),
    t9("t9","士"),t10("t10","达");

    @Getter private String id;
    @Getter private String zi;

    Ziname(String id, String zi) {
        this.id = id;
        this.zi = zi;
    }

    public static Ziname getziName(String id){
        Ziname[] values = Ziname.values();
        for(Ziname i:values){
            if(id==i.getId()){
                return i;
            }
        }
        return null;
    }
}
