/**    
 * 文件名：CodeUtils_.java    
 *    
 * 版本信息：    
 * 日期：2014-9-18    
 * Copyright LGQ 
 * Corporation 2014     
 * 版权所有    
 *    
 */
package com.netty.util;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * 项目名称：yh_pkg 类名称：CodeUtils_ 类描述：CodeUtils_ 创建人：LGQ 创建时间：2014-9-18 修改人：LGQ
 * 修改时间：2014-9-18 修改备注：
 * 
 * @version：1.0
 * 
 */
public class CodeUtils {

    private static final char[] TO_DIGITS = { '0', '1', '2', '3', '4', '5',
                                          '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

    // //////////////////////////////////////byte&&string///////////////////////////////////////

    public static String encodeHexStr(byte data) {
        char[] out = new char[2];
        out[0] = TO_DIGITS[(0xF0 & data) >>> 4];
        out[1] = TO_DIGITS[0x0F & data];
        return new String(out);
    }

    public static String encodeHexStr(byte[] data) {
        if (null == data) {
            return "";
        }
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = TO_DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = TO_DIGITS[0x0F & data[i]];
        }
        return new String(out);
    }

    public static byte[] decodeHex(String str) {
        if (null == str) {
            return null;
        }
        char[] data = str.toCharArray();
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    public static byte[] decodeHex(byte[] data) {
        if (null != data) {
            try {
                BigInteger big = new BigInteger(new String(data), 16);
                byte[] bufs = big.toByteArray();
                return bufs;
            }
            catch (Exception e) {

            }
        }
        return null;
    }

    // //////////////////////////////////////int&&string///////////////////////////////////////

    public static int convertInt2HexInt(int n) {
        return Integer.valueOf(String.valueOf(n), 16);
    }

    public static int convertHexInt2Int(int n) {
        return Integer.valueOf(String.valueOf(n), 10);
    }

    public static String convertInt2HexString(int n) {
        String str = Integer.toHexString(n);
        if (str.length() == 1) {
            str = "0" + str;
        }
        else if (str.length() == 0) {
            str = "00";
        }
        return str;
    }

    public static int convertHexString2Int(String str) {
        return Integer.valueOf(str, 10);
    }

    // //////////////////////////////////////int&&byte///////////////////////////////////////

    public static byte[] int2Bytes(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static int bytes2Int(byte[] res) {
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////

    public static byte[] Byte2HexByte(byte[] data, int off, int changeLen) {
        byte[] dataDest = new byte[changeLen + data.length];
        if (null != data && changeLen > 0) {
            try {
                byte[] dataTemp = new byte[changeLen];
                System.arraycopy(data, off, dataTemp, 0, dataTemp.length);
                BigInteger big = new BigInteger(dataTemp);
                String str_big = big.toString(16);
                while (str_big.length() < changeLen * 2) {
                    str_big = "0" + str_big;
                }
                byte[] bufs = str_big.getBytes();
                if (off > 0) {
                    System.arraycopy(data, 0, dataDest, 0, off);
                }
                System.arraycopy(bufs, 0, dataDest, off, bufs.length);
                System.arraycopy(data, dataTemp.length + off, dataDest,
                        bufs.length + off, data.length - off - dataTemp.length);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataDest;
    }

    public static byte[] HexByte2Byte(byte[] data, int off, int changeLen,
            int len) {// changeLen必须不变
        byte[] dataDest = new byte[len - changeLen];
        Arrays.fill(dataDest, (byte) 0);
        if (null != data && changeLen > 0) {
            try {
                byte[] dataTemp = new byte[changeLen * 2];
                System.arraycopy(data, off, dataTemp, 0, dataTemp.length);
                String str_big = new String(dataTemp);
                BigInteger big = new BigInteger(str_big, 16);
                byte[] bufs = big.toByteArray();
                if (off > 0) {
                    System.arraycopy(data, 0, dataDest, 0, off);
                }
                System.arraycopy(bufs, 0, dataDest, off + changeLen
                        - bufs.length, bufs.length);
                System.arraycopy(data, dataTemp.length + off, dataDest,
                        changeLen + off, len - off - dataTemp.length);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataDest;
    }

    // //////////////////////////////////////高地位互换///////////////////////////////////////

    public static int BigEndianAndLittleEndian(int value) {
        byte byte4 = (byte) (value & 0xff);
        byte byte3 = (byte) ((value & 0xff00) >> 8);
        byte byte2 = (byte) ((value & 0xff0000) >> 16);
        byte byte1 = (byte) ((value & 0xff000000) >> 24);
        return ((byte1 & 0xff) << 0 | (byte2 & 0xff) << 8
                | (byte3 & 0xff) << 16 | (byte4 & 0xff) << 24);
    }

}
