
# simple rpc  example

简单的rpc 例子 
知识点        **netty**、**protostuff**、**zk**、 **Proxy**、**CountDownLatch**

------

![rpc](https://s2.ax1x.com/2019/09/09/nYgMOe.png)



- server  注册 zk
- client 订阅 zk  获取并监听服务列表
- client  Proxy    动态代理 请求 
-  protostuff  序列化/反序列化  

客户端使用

```java
		new Observer("127.0.0.1:2181");
		ServiceTest proxy = ProxyInterface.getProxy(ServiceTest.class);
		String message = proxy.getMessage();
		System.out.println(message);
```
服务端使用
```java
		ServiceTest test= new TestImpl();	
		Invoker.put(test);
		List<String> list = new ArrayList<>(Invoker.getServices());
		ServiceInfo info = new ServiceTestImpl("127.0.0.1", 8080, list);
		Publisher pub = new Publisher("127.0.0.1:2181");
		pub.addService(info);
		Server.bind(8080);
```
>>>>>>> d565f2c... rpc demo
