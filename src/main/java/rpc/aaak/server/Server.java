package rpc.aaak.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import rpc.aaak.common.RequestDecoder;
import rpc.aaak.common.ResponseEncoder;

public class Server {
	public static void bind(int port) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		ServerBootstrap server = new ServerBootstrap();
		server.group(boss, worker);
		server.channel(NioServerSocketChannel.class);
		server.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(new RequestDecoder());
				pipeline.addLast(new ResponseEncoder());
				pipeline.addLast(new RequestHandler());
				//超时处理
				//pipeline.addLast(new IdleStateHandler(8, 0, 0, TimeUnit.SECONDS));

			}

		});
		ChannelFuture sync = server.bind(port).sync();
		sync.channel().closeFuture().sync();
	}


}
