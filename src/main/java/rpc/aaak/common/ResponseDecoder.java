package rpc.aaak.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 *  将ByteBuf转成其他类型的  (RpcResponse) Message
 * 须理解 ByteBuf
 * @author aaak
 */
public class ResponseDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int length = 4;
        //查看有多少可读字节。不符合直接return
        if (in.readableBytes() < length) {
            return;
        }
		int index =in.readerIndex();

        int readInt = in.readInt();

        if (in.readableBytes() < readInt) {
            in.readerIndex(index);
            return;
        }
        byte[] bytes = new byte[readInt];
        in.readBytes(bytes);
        RpcResponse response = SerializationUtil.deserializeFromByte(bytes, RpcResponse.class);
        out.add(response);
    }

}
