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
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @version：1.0
 * 
 */
public class DataFields implements Serializable {
    private List<DataField> fields = new ArrayList<DataField>();

    private int             count  = 0;

    private int             index  = -1;

    public int size() {
        return fields.size();
    }

    public void fields(List<DataField> dfs) {
        fields.clear();

        fields.addAll(dfs);
    }

    public List<DataField> fields() {
        return fields;
    }

    public void clear() {
        count = 0;
        resetIndex();
        fields.clear();
    }

    public void resetIndex() {
        index = -1;
    }

    public void putField(DataField df) {
        if (null == df) {
            return;
        }

        this.count++;
        this.fields.add(df);
    }

    //
    public void putInt(int val) {
        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_INTEGER;
        df.length = 4;
        df.data = FormatTransfer.toHH(val);
        this.fields.add(df);
    }

    public void putLong(long val) {
        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_LONG;
        df.length = 8;
        df.data = FormatTransfer.toHH(val);
        this.fields.add(df);
    }

    public void putShort(short val) {
        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_SHORT;
        df.length = 2;
        df.data = FormatTransfer.toHH(val);
        this.fields.add(df);
    }

    public void put(byte val) {

        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_BYTE;
        df.length = 1;
        df.data = FormatTransfer.byte2bytes(val);
        this.fields.add(df);
    }

    public void put(byte[] val) {
        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_BYTEARRAY;
        if (null == val) {
            df.length = (short) 0;
            df.data = new byte[0];
        }
        else {
            df.length = (short) val.length;
            df.data = val;
        }
        this.fields.add(df);
    }

    public void put(byte[] val, int offset, int datalen) {
        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_BYTEARRAY;
        if (null == val || offset < 0 || datalen > val.length || offset + datalen > val.length) {
            df.length = (short) 0;
            df.data = new byte[0];
        }
        else {
            df.length = (short) datalen;
            df.data = new byte[df.length];
            System.arraycopy(val, offset, df.data, 0, datalen);
        }
        this.fields.add(df);
    }

    public void put(byte[] val1, byte[] val2) {

        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_BYTEARRAY;

        if (null == val1 || null == val2) {
            df.length = (short) ((val1 != null ? val1.length : 0) + (val2 != null ? val2.length : 0));
            df.data = new byte[df.length];
            if (val1 != null) {
                System.arraycopy(val1, 0, df.data, 0, val1.length);
            }
            if (val2 != null) {
                System.arraycopy(val2, 0, df.data, (val1 != null ? val1.length : 0), val2.length);
            }
        }
        else {
            df.length = (short) (val1.length + val2.length);
            df.data = new byte[val1.length + val2.length];
            System.arraycopy(val1, 0, df.data, 0, val1.length);
            System.arraycopy(val2, 0, df.data, val1.length, val2.length);
        }
        this.fields.add(df);
    }

    public void put(String val) {

        this.count++;

        DataField df = new DataField();
        df.dataType = DataField.DATATYPE_BYTEARRAY;

        if (null == val || val.trim().equals("")) {

            df.length = (short) 0;
            df.data = new byte[0];
        }
        else {
            df.data = val.getBytes(Charset.forName("utf-8"));
            df.length = (short) df.data.length;
        }
        this.fields.add(df);
    }

    public DataField getField(int index) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index);
        }
        else {
            return null;
        }
    }

    public DataField getField(int index, DataField dft) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index);
        }
        else {
            return dft;
        }
    }

    public DataField getField() {
        if (index < this.count - 1) {
            return this.fields.get(++index);
        }
        else {
            return null;
        }
    }

    public byte get(int index) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).byteVal();
        }
        else {
            return 0;
        }
    }

    public byte get(int index, byte dft) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).byteVal();
        }
        else {
            return dft;
        }
    }

    public byte get() {
        if (index < this.count - 1) {
            return this.fields.get(++index).byteVal();
        }
        else {
            return 0;
        }
    }

    public short getShort(int index) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).shortVal();
        }
        else {
            return 0;
        }
    }

    public short getShort(int index, short dft) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).shortVal();
        }
        else {
            return dft;
        }
    }

    public short getShort() {
        if (index < this.count - 1) {
            return this.fields.get(++index).shortVal();
        }
        else {
            return 0;
        }
    }

    public int getInt(int index) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).intVal();
        }
        else {
            return 0;
        }
    }

    public int getInt(int index, int dft) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).intVal();
        }
        else {
            return dft;
        }
    }

    public int getInt() {
        if (index < this.count - 1) {
            return this.fields.get(++index).intVal();
        }
        else {
            return 0;
        }
    }

    public long getLong(int index) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).longVal();
        }
        else {
            return 0L;
        }
    }

    public long getLong(int index, long dft) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).longVal();
        }
        else {
            return dft;
        }
    }

    public long getLong() {
        if (index < this.count - 1) {
            return this.fields.get(++index).longVal();
        }
        else {
            return 0L;
        }
    }

    public byte[] getBytes(int index) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).val();
        }
        else {
            return new byte[0];
        }
    }

    public byte[] getBytes(int index, byte[] dft) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).val();
        }
        else {
            return dft;
        }
    }

    public byte[] getBytes() {
        if (index < this.count - 1) {
            return this.fields.get(++index).val();
        }
        else {
            return new byte[0];
        }
    }

    public String getStr(int index) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).str();
        }
        else {
            return "";
        }
    }

    public String getStr(int index, String dft) {
        if (index < this.count && index >= 0) {
            return this.fields.get(index).str();
        }
        else {
            return dft;
        }
    }

    public String getStr() {
        if (index < this.count - 1) {
            return this.fields.get(++index).str();
        }
        else {
            return "";
        }
    }
}
