package rpc.aaak.client;

import java.util.concurrent.SynchronousQueue;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.aaak.common.RpcResponse;

/**
 * 类型匹配以及用完之后释放指向保存该消息的 ByteBuf 的内存引用。
 * @author aaak
 */
public class ResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
		String id = msg.getId();
		SynchronousQueue synchronousQueue = ResultInfo.getSynchronousQueue(id);
		synchronousQueue.put(msg.getResult());
		ResultInfo.removeById(id);

		System.out.println(msg.toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("connection");
		super.channelActive(ctx);
	}
}
