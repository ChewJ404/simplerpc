package rpc.aaak.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/** 将 ByteBuf 转成 RpcRequest
 * @author aaak
 */
public class RequestDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		if (in.readableBytes() < 4) {
			return;
		}
		int index = in.readerIndex();
		int readInt = in.readInt();
		if (in.readableBytes() < readInt) {
			in.readerIndex(index);
			return;
		}
		byte[] bytes = new byte[readInt];
		in.readBytes(bytes);
		RpcRequest request = SerializationUtil.deserializeFromByte(bytes, RpcRequest.class);
		out.add(request);

	}

}
