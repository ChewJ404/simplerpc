package rpc.aaak.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;

import rpc.aaak.client.zk.Observer;
import rpc.aaak.common.RpcRequest;

/**
 * 动态代理,反射
 * @author aaak
 */
public class ProxyInterface {
	public static <T> T getProxy(final Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				RpcRequest request = new RpcRequest();
				request.setMethodName(method.getName());
				request.setClassName(clazz.getName());
				Class<?>[] parameterArg  = method.getParameterTypes();
				request.setArgs(args);
				String id = UUID.randomUUID().toString();
				request.setId(id);
				SynchronousQueue queue = new SynchronousQueue();
				ResultInfo.putSunchronousQuee(id, queue);

				//调用server
				Observer.send(request, clazz.getName());
				//阻塞等等server返回服务
				return queue.take();
			}
		});
	}
}
