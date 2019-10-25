## 第11章 多线程技术

### 1. 概念

- Process与Thread

   ![img](https://img-blog.csdnimg.cn/20191024153605541.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)
   
- 核心概念

   1. 线程就是独立的执行路径。
   2. 在程序运行时，即使没有自己创建线程，后台也会存在多个线程，如gc线程、主线程。
   3. main()称之为主线程，为系统的入口点，用于执行整个程序。
   4. 在一个进程中，如果开辟了多个线程，线程的运行由调度器安排调度，调度器是与操作系统紧密相关的，先后顺序是不能认为干预的。
   5. 对同一份资源操作时，会存在资源抢夺的问题，需要加入并发控制。
   6. 线程会带来额外的开销，如cpu调度时间，并发控制开销。
   7. 每个线程在自己的工作内存交互，加载和存储主内存控制不当会造成数据不一致。