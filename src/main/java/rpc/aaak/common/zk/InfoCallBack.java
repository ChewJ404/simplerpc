package rpc.aaak.common.zk;

import java.util.List;

/**
 * @author aaak
 */
public interface InfoCallBack {


	/**
	 * 获取最后的zk 子节点的数据
	 * @param lists
	 */
    void getLastList(List<String> lists);

}
