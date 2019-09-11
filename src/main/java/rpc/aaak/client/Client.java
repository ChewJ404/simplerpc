package rpc.aaak.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import rpc.aaak.common.RequestEncoder;
import rpc.aaak.common.ResponseDecoder;

/**
 * @author aaak
 */
public class Client {
    private Channel channel;
    private String id;

	/**
	 * Reactor 单线程模型
	 */
    public void connect(String ip, int port) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new RequestEncoder());
                pipeline.addLast(new ResponseDecoder());
                pipeline.addLast(new ResponseHandler());
            }

        });

        ChannelFuture sync = b.connect(ip, port).sync();
        channel = sync.channel();
        id = ip + ":" + port;

    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Client other = (Client) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }


    public void write(Object obj) {
        channel.writeAndFlush(obj);
    }
}
