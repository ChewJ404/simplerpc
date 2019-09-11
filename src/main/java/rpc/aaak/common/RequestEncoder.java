package rpc.aaak.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码 RpcRequest to ByteBuf
 * @author aaak
 */
public class RequestEncoder extends MessageToByteEncoder<RpcRequest> {
	@Override
	protected void encode(ChannelHandlerContext ctx, RpcRequest msg, ByteBuf out) {
		byte[] serializeToByte = SerializationUtil.serializeToByte(msg);

		out.writeInt(serializeToByte.length);
		out.writeBytes(serializeToByte);

	}

}
