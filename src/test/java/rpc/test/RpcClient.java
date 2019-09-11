package rpc.test;

import com.aaak.service.ServiceTest;

import rpc.aaak.client.ProxyInterface;
import rpc.aaak.client.zk.Observer;

public class RpcClient {

	public static void main(String[] args) {
		//订阅
		new Observer("127.0.0.1:2181");
		//rpc
		ServiceTest proxy = ProxyInterface.getProxy(ServiceTest.class);
		String message = proxy.getMessage();
		System.out.println(message);


	}

}
