package rpc.aaak.server.zk;

import com.alibaba.fastjson.JSONObject;

import rpc.aaak.common.CommonContext;
import rpc.aaak.common.ServiceInfo;
import rpc.aaak.common.zk.ZkUtil;

public class Publisher {

	private ZkUtil zkUtil;

	public Publisher(String address) {
		zkUtil = new ZkUtil(address);
		zkUtil.CreateIfNotExist(CommonContext.PATH);
	}


	/**
	 * 注册服务
	 * key     ip :port
	 * value   {"interfaces":["com.xp.service.xx"],"ip":"127.0.0.1","port":6161}
	 * @param info
	 */
	public void addService(ServiceInfo info) {
		zkUtil.create(CommonContext.PATH + "/" + info.toString(), JSONObject.toJSONString(info).getBytes());

	}

}
