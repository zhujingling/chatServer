package com.netty.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class Decoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf buf,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		   if (buf.readableBytes()<buf.getInt(buf.readerIndex())){
	            return;
	        }
		   out.add(buf.readBytes(buf.readableBytes()));
	}

}
