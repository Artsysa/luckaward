package com.wxprogram.luckaward.util;

import lombok.ToString;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/6 13:04
 * @Month:02
 */
@ToString
public class RandomUtil {
   int t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0,t10=0;

    public static final String getRandom(){
        String str="null";

        double random = Math.random();
        if(random>=0&&random<0.1){
            str="t1";
        }else if(random>=0.1&&random<0.2){
            str="t2";
        }else if(random>=0.2&&random<0.3){
            str="t3";
        }else if(random>=0.3&&random<0.4){
            str="t4";
        }else if(random>=0.4&&random<0.5){
            str="t5";
        }else if(random>=0.5&&random<0.6){
            str="t6";
        }else if(random>=0.6&&random<0.79){
            str="t7";
        }else if(random>=0.79&&random<0.86){
            str="t8";
        }else if(random>=0.86&&random<0.93){
            str="t9";
        }else if(random>=0.93&&random<1.0){
            str="t10";
        }

        return str;
    }
    public static final String getRandoms(){
        String str="null";

        double random = Math.random();
        if(random>=0&&random<0.001){
            str="t1";
        }else if(random>=0.001&&random<0.05){
            str="t2";
        }else if(random>=0.05&&random<0.15){
            str="t3";
        }else if(random>=0.15&&random<0.35){
            str="t4";
        }else if(random>=0.35&&random<0.65){
            str="t5";
        }else if(random>=0.65&&random<0.67){
            str="t6";
        }else if(random>=0.67&&random<0.7){
            str="t7";
        }else if(random>=0.7&&random<1.0){
            str="t8";
        }

        return str;
    }

    public void nu(){


        for (int i = 0; i < 30; i++) {
            String random = getRandom();
            if(random.equals("t1")){
                t1++;
            }else  if(random.equals("t2")){
                t2++;
            }else  if(random.equals("t3")){
                t3++;
            }else  if(random.equals("t4")){
                t4++;
            }else  if(random.equals("t5")){
                t5++;
            }else  if(random.equals("t6")){
                t6++;
            }else  if(random.equals("t7")){
                t7++;
            }else  if(random.equals("t8")){
                t8++;
            }else  if(random.equals("t9")){
                t9++;
            }else  if(random.equals("t10")){
                t10++;
            }
        }

    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            RandomUtil randomUtil = new RandomUtil();
            randomUtil.nu();
            System.out.println(randomUtil.toString());
        }

    }

}
