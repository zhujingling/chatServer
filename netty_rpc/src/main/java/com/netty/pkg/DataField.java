/**    
 * 文件名：DataField.java    
 *    
 * 版本信息：    
 * 日期：2014年11月5日    
 * Copyright LGQ 
 * Corporation 2014     
 * 版权所有    
 *    
 */
package com.netty.pkg;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * 项目名称：yh_pkg 类名称：DataField 类描述：DataField 创建人：LGQ 创建时间：2014年11月5日 修改人：LGQ
 * 修改时间：2014年11月5日 修改备注：
 * 
 * @version：1.0
 * 
 */
public class DataField implements Serializable {
    /*
     * 数据域
     */

    // byte 字节数据
    public final static int DATATYPE_BYTE      = 1;

    // 短整型数据
    public final static int DATATYPE_SHORT     = 2;

    // 整形数据
    public final static int DATATYPE_INTEGER   = 3;

    // byte[] 数据
    public final static int DATATYPE_BYTEARRAY = 4;

    // 字符串数据
    public final static int DATATYPE_STRING    = 5;

    // 长整形数据
    public final static int DATATYPE_LONG      = 6;

    /*
     * 数据长
     */
    public short            length;

    /*
     * 数据类型
     */
    public int              dataType;

    /*
     * 数据
     */
    public byte[]           data;

    public DataField() {

    }

    public DataField(int dataType, byte[] data) {
        this.dataType = dataType;
        this.data = data;
        this.length = (short) (data != null ? data.length : 0);
    }

    public DataField(int dataType, String data) {
        this.dataType = dataType;
        this.data = (data != null ? data.getBytes(Charset.forName("utf-8")) : null);
        this.length = (short) (this.data != null ? this.data.length : 0);
    }

    public void length(short length) {
        this.length = length;
    }

    public short length() {
        return this.length;
    }

    public void data(byte[] data) {
        this.data = data;
    }

    public Object data() {
        return this.data;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public byte byteVal() {
        return Fixer.getByte(data);
    }

    public short shortVal() {
        return Fixer.getShort(data);
    }

    public int intVal() {
        return Fixer.getInt(data);
    }

    public long longVal() {
        return Fixer.getLong(data);
    }

    public byte[] val() {
        return data;
    }

    public String str() {
        return new String(data, Charset.forName("utf-8")).trim();
    }

}
