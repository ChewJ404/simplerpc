package rpc.aaak.common.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static rpc.aaak.common.CommonContext.ZK_TIMEOUT;

/**
 * @author aaak
 */
public class ZkUtil {
    private ZooKeeper zk;

    /**
     * Zookeeper客户端和服务端会话的建立是一个异步的过程
     * 也就是说在程序中，构造方法会在处理完客户端初始化工作后立即返回
     * 在大多数情况下此时并没有真正建立好一个可用的会话，此时在会话的生命周期中处于“CONNECTING”的状态
     */

    public ZkUtil(String address) {

        final CountDownLatch latch = new CountDownLatch(1);
        try {

            zk = new ZooKeeper(address, ZK_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            //等待Watcher通知SyncConnected
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建临时节点
     */
    public void create(String path, byte[] datas) {
        try {
            zk.create(path, datas, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 如果不存在创建节点
	 * 创建临时节点
	 */
    public void CreateIfNotExist(String path) {
        try {
            Stat s = zk.exists(path, false);
            if (s == null) {
                zk.create(path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 获取节点数据
	 */
    public byte[] getData(String path) throws Exception {
        return zk.getData(path, false, null);
    }
	/**
	 * 获取并监听子节点的数据改变
	 */
    public List<String> getInofAndWatcher(final String path, final InfoCallBack callBack) throws Exception {
        List<String> nodeList = zk.getChildren(path, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                //监听借点变化
                if (event.getType() == Event.EventType.NodeChildrenChanged) {
                    try {
                        List<String> nodeList = zk.getChildren(path, false);
                        callBack.getLastList(nodeList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        return nodeList;

    }

}
