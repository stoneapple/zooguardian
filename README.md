# zooguardian是什么

​       zooguardian是为apache zookeeper量身定制的管理平台，解决了zookeeper无图形化数据展示、垃圾节点清理的困扰。

​       zooguardian项目采用SpringBoot开发模式，推荐JDK1.8

## zooguardian功能特性

- 动态输入Zookeeper集群地址，管理集群数据
- 预设值zookeeper集群配置
- zookeeper集群连接数、watch数目等信息展示
- 树形结构可视化ZK节点层次，动态展开和关闭
- 显示ZNode节点数据及节点属性
- 删除、级联删除ZNode节点
- 管理员用户自定义配置
## 打包安装方法
- mvn clean package
- 找到target目录下生成的zooguardians-0.0.1-SNAPSHOT.jar 执行  java -jar zooguardians-0.0.1-SNAPSHOT.jar 即可启动

## 界面截图如下：

![image](https://github.com/stoneapple/zooguardian/blob/master/screenshots/1.png)
![image](https://github.com/stoneapple/zooguardian/blob/master/screenshots/2.png)
![image](https://github.com/stoneapple/zooguardian/blob/master/screenshots/3.png)
