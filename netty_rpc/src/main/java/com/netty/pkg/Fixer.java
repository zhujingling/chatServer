package com.netty.pkg;

import java.nio.ByteBuffer;

import com.netty.command.Command;

public final class Fixer {

    /*
     * 重新计算统一通讯协议格式消息长度
     */
    public static byte[] fixucpb(ByteBuffer buf) {

        int pos = buf.position();
        int length = pos - Command.LENGTH_HEAD - Command.LENGTH_LENGTH - Command.LENGTH_TAIL;
        buf.position(Command.LENGTH_HEAD);
        buf.putInt(length);
        buf.position(pos);
        buf.flip();

        byte[] bt = new byte[buf.limit()];
        buf.get(bt);

        return bt;
    }


    /*
     * 重新计算长度
     */
    public static byte[] fix(ByteBuffer buf) {
        buf.flip();

        byte[] bt = new byte[buf.limit()];
        buf.get(bt);

        return bt;
    }

    /*
     * byte[] 数据转换成Integer
     */
    public static int getInt(byte[] buf) {
        try {
            ByteBuffer bb = ByteBuffer.wrap(buf);
            bb.order(Command.BYTE_ORDER);

            return bb.getInt();
        }
        catch (Exception e) {

        }

        return 0;
    }

    /*
     * byte[] 数据转换成Long
     */
    public static long getLong(byte[] buf) {
        try {
            ByteBuffer bb = ByteBuffer.wrap(buf);
            bb.order(Command.BYTE_ORDER);

            return bb.getLong();
        }
        catch (Exception e) {

        }

        return 0L;
    }

    /*
     * byte[] 数据转换成Short
     */
    public static short getShort(byte[] buf) {
        try {
            ByteBuffer bb = ByteBuffer.wrap(buf);
            bb.order(Command.BYTE_ORDER);

            return bb.getShort();
        }
        catch (Exception e) {

        }

        return 0;
    }

    /*
     * byte[] 数据转换成Byte
     */
    public static byte getByte(byte[] buf) {
        try {
            ByteBuffer bb = ByteBuffer.wrap(buf);
            bb.order(Command.BYTE_ORDER);

            return bb.get();
        }
        catch (Exception e) {
        }

        return 0x00;
    }
}