package com.wxprogram.luckaward.enumlist;

import lombok.Getter;

/*  列举奖品名
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/3/1 10:46
 * @Month:03
 *
 */
public enum Prizename {
    t1("t1","Aj球鞋"),t2("t2","口红"),t3("t3","王者皮肤"),
    t4("t4","精美挂件"),t5("t5","Usb台灯"),
    t6("t6","免学费学车"),t7("t7","1299元学车"),t8("t8","优惠200元学车");

    @Getter private String id;
    @Getter private String name;

    Prizename(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Prizename getPrizeName(String id){
        Prizename[] values = Prizename.values();
        for(Prizename na:values){
            if(na.getId()==id){
                return na;
            }
        }
        return null;
    }
}
