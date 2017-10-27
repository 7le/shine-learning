## remote

### 流程

* 1.首先客户端的FileReadActor从文本文件中读取文件.
* 2.然后通过ClientActor发送给远端的服务端.
* 3.服务端通过MapReduceActor接受客户端发送的消息,并发消息放入优先级MailBox
* 4.MapReduceActor把接收到的文本内容分发给MapActor做Map计算
* 5.Map计算把结果都发送给ReduceActor,做汇总reduce计算.
* 6.最后AggregateActor把计算的结果显示出来.