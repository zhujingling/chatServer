package com.netty.pkg;

import java.io.Serializable;

import com.netty.command.Command;
import com.netty.util.Utils;

public class Pkg implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * 帧头
	 */
	public byte[] head;

	/*
	 * 报文长
	 */
	public int length;

	/*
	 * 命令
	 */
	public int cmd;

	/*
	 * 标志信息
	 */
	public short flag;

	/*
	 * 源ID
	 */
	public int srcId;

	/*
	 * 目标ID
	 */
	public int dstId;

	/*
	 * 时间
	 */
	public long time;

	/*
	 * 数据域个数
	 */
	public short fieldCnt;

	/*
	 * 数据域
	 */
	public DataFields fields = new DataFields();

	/*
	 * 校验码
	 */
	public Object checksum;

	/*
	 * 帧尾
	 */
	public byte[] tail;

	/*
	 * 源码字节
	 */
	public byte[] SRCCODE;

	public byte[] head() {
		return head;
	}

	public void head(byte[] head) {
		this.head = head;
	}

	public int length() {
		return length;
	}

	public void length(int length) {
		this.length = length;
	}

	public int cmd() {
		return cmd;
	}

	public void cmd(int cmd) {
		this.cmd = cmd;
	}

	public short flag() {
		return flag;
	}

	public void flag(short flag) {
		this.flag = flag;
	}

	public int srcId() {
		return srcId;
	}

	public void srcId(int srcId) {
		this.srcId = srcId;
	}

	public int dstId() {
		return dstId;
	}

	public void dstId(int dstId) {
		this.dstId = dstId;
	}

	public long time() {
		return time;
	}

	public void time(long time) {
		this.time = time;
	}

	public short fieldCnt() {
		return fieldCnt;
	}

	public void fieldCnt(short fieldCnt) {
		this.fieldCnt = fieldCnt;
	}

	public DataFields fields() {
		return fields;
	}

	public void fields(DataFields fields) {
		this.fields = fields;
	}

	public Object checksum() {
		return checksum;
	}

	public void checksum(Object checksum) {
		this.checksum = checksum;
	}

	public byte[] tail() {
		return tail;
	}

	public void tail(byte[] tail) {
		this.tail = tail;
	}

	/**
	 * 
	 * 下面的函数都是些辅助函数
	 */

	public void putField(DataField df) {
		this.fieldCnt++;
		this.fields.putField(df);
	}

	//
	public void putInt(int val) {
		this.fieldCnt++;
		this.fields.putInt(val);
	}

	public void putLong(long val) {
		this.fieldCnt++;
		this.fields.putLong(val);
	}

	public void putShort(short val) {
		this.fieldCnt++;
		this.fields.putShort(val);
	}

	public void put(byte val) {
		this.fieldCnt++;
		this.fields.put(val);
	}

	public void put(byte[] val) {
		this.fieldCnt++;
		this.fields.put(val);
	}

	public void put(byte[] val, int offset, int datalen) {
		this.fieldCnt++;
		this.fields.put(val, offset, datalen);
	}

	public void put(byte[] val1, byte[] val2) {
		this.fieldCnt++;
		this.fields.put(val1, val2);
	}

	public void put(String val) {
		this.fieldCnt++;
		this.fields.put(val);
	}

	public DataField getField(int index) {

		return this.fields.getField(index);
	}

	public DataField getField(int index, DataField dft) {

		return this.fields.getField(index, dft);
	}

	public DataField getField() {
		return this.fields.getField();
	}

	public byte get(int index) {
		return this.fields.get(index);
	}

	public byte get(int index, byte dft) {
		return this.fields.get(index, dft);
	}

	public byte get() {
		return this.fields.get();
	}

	public short getShort(int index) {
		return this.fields.getShort(index);
	}

	public short getShort(int index, short dft) {
		return this.fields.getShort(index, dft);
	}

	public short getShort() {
		return this.fields.getShort();
	}

	public int getInt(int index) {
		return this.fields.getInt(index);
	}

	public int getInt(int index, int dft) {
		return this.fields.getInt(index, dft);
	}

	public int getInt() {
		return this.fields.getInt();
	}

	public long getLong(int index) {
		return this.fields.getLong(index);
	}

	public long getLong(int index, long dft) {
		return this.fields.getLong(index, dft);
	}

	public long getLong() {
		return this.fields.getLong();
	}

	public byte[] getBytes(int index) {
		return this.fields.getBytes(index);
	}

	public byte[] getBytes(int index, byte[] dft) {
		return this.fields.getBytes(index, dft);
	}

	public byte[] getBytes() {
		return this.fields.getBytes();
	}

	public String getStr(int index) {
		return this.fields.getStr(index);
	}

	public String getStr(int index, String dft) {
		return this.fields.getStr(index, dft);
	}

	public String getStr() {
		return this.fields.getStr();
	}

	public void reset() {
		this.head = Command.HEAD;
		this.length = 0;
		this.cmd = 0;
		this.flag = (short) 0;
		this.srcId = 0xFFFFFFFF;
		this.dstId = 0;
		this.fieldCnt = 0;
		this.checksum = 0;
		this.tail = Command.TAIL;
		this.fields.clear();
	}

	public void clearFields() {
		this.fields.clear();
		this.fields = null;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("帧头:" + Utils.toHexString(head) + "\n");
		sb.append("包长:" + length + "\n");
		sb.append("命令:" + cmd + "\n");
		sb.append("标志:" + flag + "\n");
		sb.append("源ID:" + srcId + "\n");
		sb.append("目标ID:" + dstId + "\n");
		sb.append("数据域:" + fieldCnt + "\n");
		if (fieldCnt > 0) {
			int index = 0;
			for (DataField df : fields.fields()) {
				sb.append("数据长-" + (index) + ":" + df.length + "\n");
				if (df.dataType == DataField.DATATYPE_BYTE) {
					byte[] data = new byte[1];
					data[0] = df.byteVal();
					sb.append("数据-" + (index) + ":" + Utils.toHexString(data)
							+ "\n");
				} else if (df.dataType == DataField.DATATYPE_SHORT) {
					sb.append("数据-" + (index) + ":" + (df.shortVal()) + "\n");
				} else if (df.dataType == DataField.DATATYPE_INTEGER) {
					sb.append("数据-" + (index) + ":" + (df.intVal()) + "\n");
				} else if (df.dataType == DataField.DATATYPE_LONG) {
					sb.append("数据-" + (index) + ":" + (df.longVal()) + "\n");
				} else if (df.dataType == DataField.DATATYPE_BYTEARRAY) {
					sb.append("数据-" + (index) + ":"
							+ Utils.toHexString(df.data) + "\n");
				}
				index++;
			}
		}
		sb.append("校验码:" + checksum + "\n");
		sb.append("帧尾:" + Utils.toHexString(tail) + "\n");
		sb.append("源码:" + Utils.toHexString(SRCCODE));

		return sb.toString();
	}

	public void show() {
		System.out.println(toString());
	}

	public static Pkg rawPkg() {
		Pkg pkg = new Pkg();
		pkg.head = Command.HEAD;
		pkg.length = 0;
		pkg.cmd = 0;
		pkg.flag = (short) 0;
		pkg.srcId = 0xFFFFFFFF;
		pkg.dstId = 0;
		pkg.fieldCnt = 0;
		pkg.checksum = 0;
		pkg.tail = Command.TAIL;

		return pkg;
	}

	public static Pkg toSrvPkg(int srcId) {
		Pkg pkg = rawPkg();
		pkg.dstId = 0xFFFFFFFF;
		pkg.srcId = srcId;

		return pkg;
	}

	public static Pkg toPhonePkg(int dstId) {
		Pkg pkg = rawPkg();
		pkg.dstId = dstId;
		// pkg.srcId = 0xFFFFFFFF;
		pkg.srcId = pkg.dstId;

		return pkg;
	}
}
