package com.netty.util;

public class Utils {
    public static int Strstr(byte[] src, int srclen, byte[] dest, int destlen,
            int fromindex) {
        int index = -1;
        if (null != src && null != dest && fromindex >= 0 && srclen > 0
                && destlen > 0) {
            for (int ii = fromindex; ii <= srclen - destlen; ii++) {
                if (src[ii] == dest[0]) {
                    boolean bFlag = true;
                    for (int jj = 1; jj < destlen; jj++) {
                        if (src[ii + jj] != dest[jj]) {
                            bFlag = false;
                            break;
                        }
                    }
                    if (bFlag) {
                        index = ii;
                        break;
                    }
                }
            }
        }
        return index;
    }

    public static long parseLong(String s, long dft) {
        long nret = dft;
        try {
            nret = Long.parseLong(s);
        }
        catch (Exception e) {
            nret = dft;
        }

        return nret;
    }

    public static int parseInt(String s, int dft) {
        int nret = dft;
        try {
            nret = Integer.parseInt(s);
        }
        catch (Exception e) {
            nret = dft;
        }

        return nret;
    }

    public static short parseShort(String s, short dft) {
        short nret = dft;
        try {
            nret = Short.parseShort(s);
        }
        catch (Exception e) {
            nret = dft;
        }

        return nret;
    }

    public static byte parseByte(String s, byte dft) {
        byte nret = dft;
        try {
            nret = Byte.parseByte(s);
        }
        catch (Exception e) {
            nret = dft;
        }

        return nret;
    }

    public static boolean parseBoolean(String s, boolean dft) {
        boolean bret = dft;
        try {
            bret = Boolean.parseBoolean(s);
        }
        catch (Exception e) {
            bret = dft;
        }

        return bret;
    }

    public static float parseFloat(String s, float dft) {
        float fret = dft;
        try {
            fret = Float.parseFloat(s);
        }
        catch (Exception e) {
            fret = dft;
        }

        return fret;
    }

    public static double parseDouble(String s, double dft) {
        double dret = dft;
        try {
            dret = Double.parseDouble(s);
        }
        catch (Exception e) {
            dret = dft;
        }

        return dret;
    }

    public static String toHexString(byte bval) {
        StringBuilder result = new StringBuilder();
        int c = bval;
        if (c < 0) {
            c += 256;
        }
        int hex1 = c & 0xF;
        int hex2 = c >> 4;
        result.append(HEX_DIGIT[hex2]);
        result.append(HEX_DIGIT[hex1]);
        return result.toString();
    }

    public static String toHexString(byte[] coded) {
        return toHexString(coded, 0, coded != null ? coded.length : 0);
    }

    public static String toHexString(byte[] coded, int offset, int length) {
        if (coded == null) {
            return "";
        }
        StringBuilder result = new StringBuilder(length * 3);
        for (int i = 0; i < length; i++) {
            int c = coded[i + offset];
            if (c < 0) {
                c += 256;
            }
            int hex1 = c & 0xF;
            int hex2 = c >> 4;
            result.append(HEX_DIGIT[hex2]);
            result.append(HEX_DIGIT[hex1]);
            result.append(' ');
        }
        return result.toString();
    }

    public static String byte2string(byte[] data, int offset, int length, String charsetName) {
        if (null == data) {
            return "";
        }

        try {
            String str = new String(data, offset, length, charsetName);
            return str;
        }
        catch (Exception e) {
        }

        return "";
    }

    public static String byte2string(byte[] data) {

        return byte2string(data, 0, data != null ? data.length : 0, "gb2312");
    }

    private static final char[] HEX_DIGIT = new char[] { '0', '1', '2', '3',
                                          '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
}
