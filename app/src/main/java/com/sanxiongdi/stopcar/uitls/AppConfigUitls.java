package com.sanxiongdi.stopcar.uitls;

/**
 *
 *  app 配置常规参数
 *
 * Created by wuaomall@gmail.com on 2017/3/27.
 */

public class AppConfigUitls {


     public static    String AppKey="";
     public static    String  BrightBeacon_AppKey="e15a34ddca0440718d29f2bc21fe6c30";


     /**
      *   16进制的byte 字节转 String
      * @param byteArray
      * @return
      */
     public static String byteArrayToHexStr(byte[] byteArray) {
          if (byteArray == null){
               return null;
          }
          char[] hexArray = "0123456789ABCDEF".toCharArray();
          char[] hexChars = new char[byteArray.length * 2];
          for (int j = 0; j < byteArray.length; j++) {
               int v = byteArray[j] & 0xFF;
               hexChars[j * 2] = hexArray[v >>> 4];
               hexChars[j * 2 + 1] = hexArray[v & 0x0F];
          }
          return new String(hexChars);
     }



}
