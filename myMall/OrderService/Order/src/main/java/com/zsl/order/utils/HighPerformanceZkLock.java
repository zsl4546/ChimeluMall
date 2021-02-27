package com.zsl.order.utils;

import com.zsl.order.constant.OrderConstant;
import org.I0Itec.zkclient.IZkDataListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 11:27
 * Description: zookeeper 分布式锁
 */
public class HighPerformanceZkLock extends AbstractZookeeperLock {
    private String path;
    // 当前节点路径
    private String currentPath;
    // 前一个节点的路径
    private String beforePath;

    private CountDownLatch countDownLatch = null;

    public HighPerformanceZkLock(Integer commodityId) {
        this.path = OrderConstant.LOCK_KEY_PREFIX+"/"+commodityId;
        // 如果不存在这个节点，则创建持久节点
        if (!zkClient.exists(path)) {
            zkClient.createPersistent(path);
        }
    }

    @Override
    public void releaseLock() {
        if (null != zkClient) {
            zkClient.delete(currentPath);
            zkClient.close();
        }

    }

    @Override
    public boolean tryLock() {
        // 如果currentPath为空则为第一次尝试加锁，第一次加锁赋值currentPath
        if (null == currentPath || "".equals(currentPath)) {
            // 在path下创建一个临时的顺序节点
            currentPath = zkClient.createEphemeralSequential(path + "/", "lock");
        }
        // 获取所有的临时节点，并排序
        List<String> childrens = zkClient.getChildren(path);
        Collections.sort(childrens);
        if (currentPath.equals(path + "/" + childrens.get(0))) {
            return true;
        } else {
            // 如果当前节点不是排名第一，则获取它前面的节点名称，并赋值给beforePath
            int pathLength = path.length();
            int wz = Collections.binarySearch(childrens, currentPath.substring(pathLength + 1));
            beforePath = path + "/" + childrens.get(wz - 1);
        }
        return false;
    }

    @Override
    public void waitLock() {
        IZkDataListener lIZkDataListener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if (null != countDownLatch) {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }
        };
        // 监听前一个节点的变化
        zkClient.subscribeDataChanges(beforePath, lIZkDataListener);
        if (zkClient.exists(beforePath)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(beforePath, lIZkDataListener);
    }
}
