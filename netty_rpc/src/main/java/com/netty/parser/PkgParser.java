package com.netty.parser;

import java.nio.ByteBuffer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.netty.command.Command;
import com.netty.constan.FieldName;
import com.netty.pkg.DataField;
import com.netty.pkg.DataFields;
import com.netty.pkg.Fixer;
import com.netty.pkg.Pkg;
import com.netty.util.CodeUtils;

public class PkgParser {

	public static Pkg parsers(byte[] ucpb) {
		if (null == ucpb || ucpb.length <= 0) {
			return null;
		}

		try {
			ByteBuffer buf = ByteBuffer.wrap(ucpb);
			buf.order(Command.BYTE_ORDER);
			Pkg pkg = new Pkg();
			byte[] head = new byte[Command.HEAD.length];
			buf.get(head);
			pkg.head(head);
			pkg.length(buf.getInt());
			pkg.cmd(buf.getInt());
			pkg.flag(buf.getShort());
			pkg.srcId(buf.getInt());
			pkg.dstId(buf.getInt());
			pkg.fieldCnt(buf.getShort());
			if (pkg.fieldCnt > 0) {
				DataFields datas = new DataFields();
				for (short s = 0; s < pkg.fieldCnt; s++) {

					DataField ucpb_field = new DataField();
					ucpb_field.length(buf.getShort());
					ucpb_field.dataType = DataField.DATATYPE_BYTEARRAY;

					// get the length
					int length = ucpb_field.length & 0xFFFF;

					// check the length
					byte[] df = new byte[length];
					if (length > 0) {
						buf.get(df);
					}
					ucpb_field.data(df);

					// add to the list
					datas.putField(ucpb_field);
				}

				pkg.fields(datas);
			}
			if (buf.position() < ucpb.length - Command.LENGTH_TAIL) {
				int len = ucpb.length - buf.position() - Command.LENGTH_TAIL; // 校验码位置
				if (len == 1) {
					pkg.checksum(buf.get());
				} else if (len == 2) {
					pkg.checksum(buf.getShort());
				} else if (len == 4) {
					pkg.checksum(buf.getInt());
				} else {
					byte[] c = new byte[len];
					buf.get(c);
					pkg.checksum(c);
				}
			} else {
				pkg.checksum(0);
			}
			byte[] tail = new byte[Command.TAIL.length];
			buf.get(tail);
			pkg.tail(tail);

			pkg.SRCCODE = ucpb;

			return pkg;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] parsers(Pkg pkg) {
		if (null == pkg) {
			return null;
		}
		try {
			final int FIX_LENGTH = Command.LENGTH_HEAD + Command.LENGTH_LENGTH
					+ Command.LENGTH_CMD + Command.LENGTH_FLAG
					+ Command.LENGTH_SRC_ID + Command.LENGTH_DST_ID
					+ Command.LENGTH_TAIL;

			int length = FIX_LENGTH;
			if (pkg.fieldCnt > 0) {

				for (DataField df : pkg.fields().fields()) {
					length += Command.LENGTH_DATA_FIELD;
					length += df.length & 0xFFFF;
				}
			}
			length += 64; // 额外多申请一些空间
			ByteBuffer buf = ByteBuffer.allocate(length);
			buf.order(Command.BYTE_ORDER);
			// HEAD
			buf.put(pkg.head);
			// LENGTH
			buf.putInt(0);
			// CMD
			buf.putInt(pkg.cmd);

			// FLAG
			buf.putShort(pkg.flag);
			// SRC ID
			buf.putInt(pkg.srcId);
			// DST ID
			buf.putInt(pkg.dstId);
			// FIELD Count
			buf.putShort(pkg.fieldCnt);
			// FIELD
			for (short i = 0; i < pkg.fieldCnt; i++) {
				DataField df = pkg.fields().getField(i);
				buf.putShort(df.length);
				int len = df.length & 0xFFFF;
				if (len > 0) {
					if (df.dataType == DataField.DATATYPE_BYTE) {
						buf.put(df.byteVal());
					} else if (df.dataType == DataField.DATATYPE_SHORT) {
						buf.putShort(df.shortVal());
					} else if (df.dataType == DataField.DATATYPE_INTEGER) {
						buf.putInt(df.intVal());
					} else if (df.dataType == DataField.DATATYPE_LONG) {
						buf.putLong(df.longVal());
					} else if (df.dataType == DataField.DATATYPE_BYTEARRAY) {
						buf.put((byte[]) df.data);
					}
				}
			}
			// CHECKSUM
			if (pkg.checksum != null && pkg.checksum != (Integer) 0) {
				Object checksum = pkg.checksum;
				if (checksum instanceof Byte) {
					buf.put((Byte) checksum);
				} else if (checksum instanceof Short) {
					buf.putShort((Short) checksum);
				} else if (checksum instanceof Integer) {
					buf.putInt((Integer) checksum);
				} else if (checksum instanceof byte[]) {
					buf.put((byte[]) checksum);
				} else {

				}
			}
			// TAIL
			buf.put(pkg.tail);

			return Fixer.fixucpb(buf);
		} catch (Exception e) {

		}

		return null;
	}

	public static JSONObject parser(Pkg pkg) {
		JSONObject json = new JSONObject();
		json.put(FieldName.HEAD_NAME, CodeUtils.encodeHexStr(pkg.head));
		json.put(FieldName.LENGTH_NAEM, pkg.length);
		json.put(FieldName.CMD_NAME, pkg.cmd);
		json.put(FieldName.FLAG_NAME, pkg.flag);
		json.put(FieldName.SRCID_NAME, pkg.srcId);
		json.put(FieldName.DSTID_NAME, pkg.dstId);
		json.put(FieldName.TIME_NAME, System.currentTimeMillis());
		json.put(FieldName.FIELD_DATAS_CNT_NAME, pkg.fieldCnt);
		if (pkg.fieldCnt > 0) {
			JSONArray datas = new JSONArray();
			for (int i = 0; i < pkg.fieldCnt; i++) {
				JSONObject jdata = new JSONObject();
				DataField df = pkg.getField(i);
				jdata.put(FieldName.FIELD_DATAS_LENGTH_NAME, df.length);
				jdata.put(FieldName.FIELD_DATAS_TYPE_NAME, df.dataType);
				if (df.dataType == DataField.DATATYPE_BYTE) {
					jdata.put(FieldName.FIELD_DATAS_DATA_NAME,
							CodeUtils.encodeHexStr(df.byteVal()));
				} else if (df.dataType == DataField.DATATYPE_SHORT) {
					jdata.put(FieldName.FIELD_DATAS_DATA_NAME, (df.shortVal()));
				} else if (df.dataType == DataField.DATATYPE_INTEGER) {
					jdata.put(FieldName.FIELD_DATAS_DATA_NAME, (df.intVal()));
				} else if (df.dataType == DataField.DATATYPE_LONG) {
					jdata.put(FieldName.FIELD_DATAS_DATA_NAME, (df.longVal()));
				} else if (df.dataType == DataField.DATATYPE_BYTEARRAY) {
					jdata.put(FieldName.FIELD_DATAS_DATA_NAME,
							CodeUtils.encodeHexStr(df.data));
				}

				datas.put(jdata);
			}

			json.put(FieldName.FIELD_DATAS_NAME, datas);
		}
		json.put(FieldName.CHECKSUM_NAME, pkg.checksum);
		json.put(FieldName.TAIL_NAME, CodeUtils.encodeHexStr(pkg.tail));
		json.put(FieldName.SCODE_NAME, CodeUtils.encodeHexStr(pkg.SRCCODE));

		return json;
	}

	public static Pkg parser(JSONObject json) {
		if (null == json) {
			return null;
		}

		try {
			Pkg pkg = new Pkg();

			pkg.head(CodeUtils.decodeHex(json.getString(FieldName.HEAD_NAME)));
			pkg.length(json.getInt(FieldName.LENGTH_NAEM));
			pkg.cmd(json.getInt(FieldName.CMD_NAME));
			pkg.flag((short) json.getInt(FieldName.FLAG_NAME));
			pkg.srcId(json.getInt(FieldName.SRCID_NAME));
			pkg.dstId(json.getInt(FieldName.DSTID_NAME));
			pkg.fieldCnt((short) json.getInt(FieldName.FIELD_DATAS_CNT_NAME));
			if (pkg.fieldCnt > 0) {
				JSONArray datas = json.getJSONArray(FieldName.FIELD_DATAS_NAME);
				DataFields dfs = new DataFields();
				for (short s = 0; s < pkg.fieldCnt; s++) {

					JSONObject jdata = datas.getJSONObject(s);

					int dataType = jdata
							.getInt(FieldName.FIELD_DATAS_TYPE_NAME);
					if (dataType == DataField.DATATYPE_BYTE) {
						dfs.put(CodeUtils.decodeHex(jdata
								.getString(FieldName.FIELD_DATAS_DATA_NAME)));
					} else if (dataType == DataField.DATATYPE_SHORT) {
						dfs.putShort((short) jdata
								.getInt(FieldName.FIELD_DATAS_DATA_NAME));
					} else if (dataType == DataField.DATATYPE_INTEGER) {
						dfs.putInt(jdata
								.getInt(FieldName.FIELD_DATAS_DATA_NAME));
					} else if (dataType == DataField.DATATYPE_LONG) {
						dfs.putLong(jdata
								.getLong(FieldName.FIELD_DATAS_DATA_NAME));
					} else if (dataType == DataField.DATATYPE_BYTEARRAY) {
						dfs.put(CodeUtils.decodeHex(jdata
								.getString(FieldName.FIELD_DATAS_DATA_NAME)));
					}
				}

				pkg.fields(dfs);
			}
			pkg.checksum(json.get(FieldName.DSTID_NAME));
			pkg.tail(CodeUtils.decodeHex(json.getString(FieldName.TAIL_NAME)));
			pkg.SRCCODE = CodeUtils.decodeHex(json
					.getString(FieldName.SCODE_NAME));

			return pkg;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}