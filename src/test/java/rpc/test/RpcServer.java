package rpc.test;

import java.util.ArrayList;
import java.util.List;

import com.aaak.service.ServiceTest;
import com.aaak.service.ServiceTestImpl;

import org.apache.log4j.PropertyConfigurator;
import rpc.aaak.common.ServiceInfo;
import rpc.aaak.server.Invoker;
import rpc.aaak.server.Server;
import rpc.aaak.server.zk.Publisher;

public class RpcServer {

	public static void main(String[] args) throws InterruptedException {
		PropertyConfigurator.configure("src/log4j.properties");

		ServiceTest test= new ServiceTestImpl();

		Invoker.put(test);
		List<String> list = new ArrayList<>(Invoker.getServices());
		ServiceInfo info = new ServiceInfo("127.0.0.1", 6161, list);

		//发布
		Publisher pub = new Publisher("127.0.0.1:2181");
		pub.addService(info);
		
		
		//启动server
		Server.bind(6161);

	}

}
