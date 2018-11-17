# shine-learning

> akka的学习

``actor 生命周期``

![actor-生命周期](https://github.com/7le/7le.github.io/raw/master/image/actor.png)

> actorOf -> preStart -> start -> receive -> stop -> postStop

Actor初始的时候路径是空的,通过actorOf方法获得Actor的实例,会返回一个ActorRef来表示Actor的引用.
它包含了一个UID和一个Path(唯一),重启操作Path和UID不会改变,因此重启前获取到的ActorRef继续有效.

在stop的时候,Hook会被调用, 处于监控状态的actor会收到通知.
此时新的ActorRef的路径和之前一样但是UID不同.所以在停止前获取到的ActorRef不再有效.

而ActorSelection只关心Path,可以通过ActorSelection的resolveOne的方法来获取Actor。
