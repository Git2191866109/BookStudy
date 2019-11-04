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

- 少用继承多用实现，因为java里面只能单继承

- 线程Thread的使用方式

   1. 继承Thread，重写run()方法，通过start()方法去启动线程
   2. 实现Runnable接口，重写run()方法，通过new一个Thead对象调start()方法。

- start方法不保证立即运行，由cpu调用

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: ThreadStudy01.java
    * @time: 2019/10/25 12:37
    * @desc: 进程学习1
    */
   
   public class StartThread1 extends Thread{
   
       public void run(){
           for (int i = 0; i < 20; i++) {
               System.out.println("一边听歌一边敲代码。");
           }
       }
   
       public static void main(String[] args) throws InterruptedException {
           // 创建子类对象
           StartThread1 st = new StartThread1();
           // 启动
           st.start();
           // run是普通方法的调用
   //        st.run();
           for (int i = 0; i < 20; i++) {
               System.out.println("coding。");
               Thread.sleep(1);
           }
       }
   }
   ```

- 创建线程方式1：利用线程下载图片案例

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: TDownloader.java
    * @time: 2019/10/28 15:58
    * @desc: 进程学习2：下载图片
    */
   
   public class TDownloader extends Thread{
       // 远程路径
       private String url;
       // 存储名字
       private String name;
   
       public TDownloader(String url, String name) {
           this.url = url;
           this.name = name;
       }
   
       @Override
       public void run() {
           WebDownloader wd = new WebDownloader();
           wd.download(url, name);
           System.out.println(name);
       }
       
       public static void main(String[] args){
           TDownloader td1 = new TDownloader("https://img-blog.csdnimg.cn/20181107085145510.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "lstm.png");
           TDownloader td2 = new TDownloader("https://img-blog.csdnimg.cn/20181107095455442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "peephole_connection.png");
           TDownloader td3 = new TDownloader("https://img-blog.csdnimg.cn/20181107101049389.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "gru.png");
   
           // 启动三个线程
           td1.start();
           td2.start();
           td3.start();
       }
   }
   ```

- 利用线程方式2：（推荐使用这种方式）

   - 避免单继承的局限性，优先使用接口
   - 方便共享资源

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: ThreadStudy01.java
    * @time: 2019/10/25 12:37
    * @desc: 进程学习3
    */
   
   public class StartRun1 implements Runnable {
   
       public void run() {
           for (int i = 0; i < 20; i++) {
               System.out.println("一边听歌一边敲代码。");
           }
       }
   
       public static void main(String[] args) throws InterruptedException {
           /*
           // 创建实现类对象
           StartRun1 sr = new StartRun1();
           // 创建代理类对象
           Thread t = new Thread(sr);
           // 启动
           t.start();
           // run是普通方法的调用
   //        st.run();
           */
   
           // 利用匿名对象
           new Thread(new StartRun1()).start();
   
           for (int i = 0; i < 20; i++) {
               System.out.println("coding。");
               Thread.sleep(1);
           }
       }
   }
   ```

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: TDownloader.java
    * @time: 2019/10/28 15:58
    * @desc: 进程学习2：下载图片
    */
   
   public class IDownloader implements Runnable {
       // 远程路径
       private String url;
       // 存储名字
       private String name;
   
       public IDownloader(String url, String name) {
           this.url = url;
           this.name = name;
       }
   
       @Override
       public void run() {
           WebDownloader wd = new WebDownloader();
           wd.download(url, name);
           System.out.println(name);
       }
   
       public static void main(String[] args) {
           IDownloader td1 = new IDownloader("https://img-blog.csdnimg.cn/20181107085145510.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "lstm.png");
           IDownloader td2 = new IDownloader("https://img-blog.csdnimg.cn/20181107095455442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "peephole_connection.png");
           IDownloader td3 = new IDownloader("https://img-blog.csdnimg.cn/20181107101049389.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "gru.png");
   
           // 启动三个线程
           new Thread(td1).start();
           new Thread(td2).start();
           new Thread(td3).start();
       }
   }
   ```

- 共享资源：模拟买票

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: Web12306.java
    * @time: 2019/10/30 12:36
    * @desc: 共享资源：模拟买票
    */
   
   public class Web12306 implements Runnable {
       // 票数
       private int ticketNums = 99;
   
       @Override
       public void run() {
           while(true){
               if(ticketNums<0){
                   break;
               }
               try {
                   Thread.sleep(200);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(Thread.currentThread().getName() + "-->" + ticketNums--);
           }
       }
   
       public static void main(String[] args){
           // 一份资源
           Web12306 web = new Web12306();
           // 多个代理
           new Thread(web, "张三").start();
           new Thread(web, "李四").start();
           new Thread(web, "王五").start();
       }
   }
   ```

- 共享资源：模拟龟兔赛跑

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: Racer.java
    * @time: 2019/10/30 14:55
    * @desc: 共享资源：模拟龟兔赛跑
    */
   
   public class Racer implements Runnable {
       private String winner;       // 胜利者
   
       @Override
       public void run() {
           for (int steps = 1; steps <= 100; steps++) {
               // 模拟休息
               if(Thread.currentThread().getName().equals("rabit") && steps % 10 == 0){
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               System.out.println(Thread.currentThread().getName() + "-->" + steps);
               // 比赛是否结束
               boolean flag = gameOver(steps);
               if (flag) {
                   break;
               }
           }
       }
   
       private boolean gameOver(int steps) {
           if (winner != null) {
               // 存在胜利者
               return true;
           } else {
               if (steps == 100) {
                   winner = Thread.currentThread().getName();
                   System.out.println("winner==>" + winner);
                   return true;
               }
           }
           return false;
       }
   
       public static void main(String[] args) {
           Racer racer = new Racer();
           new Thread(racer, "tortoise").start();
           new Thread(racer, "rabbit").start();
       }
   }
   ```

- Callable：能抛出异常，有返回值（了解）

   ```java
   import com.sun.org.apache.xpath.internal.operations.Bool;
   import jdk.nashorn.internal.codegen.CompilerConstants;
   
   import java.util.concurrent.*;
   
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: TDownloader.java
    * @time: 2019/10/28 15:58
    * @desc: Callable了解学习
    */
   
   public class CDownloader implements Callable<Boolean> {
       // 远程路径
       private String url;
       // 存储名字
       private String name;
   
       public CDownloader(String url, String name) {
           this.url = url;
           this.name = name;
       }
   
       @Override
       public Boolean call() throws Exception {
           WebDownloader wd = new WebDownloader();
           wd.download(url, name);
           System.out.println(name);
           return true;
       }
   
       public static void main(String[] args) throws ExecutionException, InterruptedException {
           CDownloader cd1 = new CDownloader("https://img-blog.csdnimg.cn/20181107085145510.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "lstm.png");
           CDownloader cd2 = new CDownloader("https://img-blog.csdnimg.cn/20181107095455442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "peephole_connection.png");
           CDownloader cd3 = new CDownloader("https://img-blog.csdnimg.cn/20181107101049389.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hhcHB5Um9ja2luZw==,size_16,color_FFFFFF,t_70", "gru.png");
   
           // 创建执行服务
           ExecutorService ser = Executors.newFixedThreadPool(3);
           // 提交执行
           Future<Boolean> result1 = ser.submit(cd1);
           Future<Boolean> result2 = ser.submit(cd2);
           Future<Boolean> result3 = ser.submit(cd3);
           // 获取结果
           boolean r1 = result1.get();
           boolean r2 = result1.get();
           boolean r3 = result1.get();
           // 关闭服务
           ser.shutdownNow();
       }
   }
   ```

- **创建线程有几种方式**：常用的有两种，继承Thread类，重写Runnable接口。还有一种方式，JUC并发包下，实现Callable接口。

- 静态代理设计模式

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: StaticProxy.java
    * @time: 2019/10/30 15:29
    * @desc: 静态代理设计模式学习
    */
   
   public class StaticProxy {
       public static void main(String[] args) {
           new WeddingCompany(new You()).happyMarry();
       }
   }
   
   interface Marry {
       void happyMarry();
   }
   
   // 真实角色
   class You implements Marry {
       @Override
       public void happyMarry() {
           System.out.println("你和你的广寒仙子本月了...");
       }
   }
   
   //代理角色，婚庆公司
   class WeddingCompany implements Marry {
       // 真实角色
       private Marry target;
   
       public WeddingCompany(Marry target) {
           this.target = target;
       }
   
       @Override
       public void happyMarry() {
           ready();
           this.target.happyMarry();
           after();
       }
   
       private void ready() {
           System.out.println("布置猪窝...");
       }
   
       private void after() {
           System.out.println("闹玉兔...");
       }
   }
   ```

- Lambda表达式 简化线程（用一次）的使用

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: LambdaThread.java
    * @time: 2019/10/30 16:00
    * @desc: Lambda表达式 简化线程（用一次）的使用
    */
   
   public class LambdaThread {
       // 类中类：静态内部类
       static class Test implements Runnable {
           @Override
           public void run() {
               for (int i = 0; i < 10; i++) {
                   System.out.println("一边听歌");
               }
           }
       }
   
       public static void main(String[] args) {
           new Thread(new Test()).start();
   
           // 方法中类：局部内部类
           class Test2 implements Runnable {
               @Override
               public void run() {
                   for (int i = 0; i < 10; i++) {
                       System.out.println("一边听歌");
                   }
               }
           }
           new Thread(new Test2()).start();
   
           // 参数中类：匿名内部类
           new Thread(new Runnable() {
               @Override
               public void run() {
                   for (int i = 0; i < 20; i++) {
                       System.out.println("一边听歌");
                   }
               }
           }).start();
   
           // jdk8简化匿名内部类，lambda
           new Thread(
                   () -> {
                       for (int i = 0; i < 20; i++) {
                           System.out.println("一边听歌");
                       }
                   }
           ).start();
       }
   }
   ```

- lambda推导：**必须存在类型**

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: LambdaTest1.java
    * @time: 2019/10/31 15:18
    * @desc: lambda推导
    */
   
   public class LambdaTest1 {
       static class Like2 implements ILike {
           public void lambda() {
               System.out.println("2. 我喜欢你大爷！");
           }
       }
   
       public static void main(String[] args) {
           class Like3 implements ILike {
               public void lambda() {
                   System.out.println("3. 我喜欢你大爷！");
               }
           }
   
           // 外部类
           ILike like = new Like();
           like.lambda();
           // 静态内部类
           like = new Like2();
           like.lambda();
           // 方法内部类
           like = new Like3();
           like.lambda();
   
           // 匿名类
           like = new ILike() {
               @Override
               public void lambda() {
                   System.out.println("4. 我喜欢你大爷！");
               }
           };
           like.lambda();
   
           // lambda
           like = () -> {
               System.out.println("5. 我喜欢你大爷！");
           };
           like.lambda();
       }
   }
   
   interface ILike {
       void lambda();
   }
   
   class Like implements ILike {
       @Override
       public void lambda() {
           System.out.println("1. 我喜欢你大爷！");
       }
   }
   ```

- lambda推导 + 参数

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: LambdaTest1.java
    * @time: 2019/10/31 15:18
    * @desc: lambda推导 + 参数
    */
   
   public class LambdaTest2 {
       public static void main(String[] args) {
           ILove love = (int a) -> {
               System.out.println("偶买噶！-->" + a);
           };
           love.lambda(100);
   
           // 参数类型可以省略
           ILove love2 = s -> {
               System.out.println("偶买噶！-->" + s);
           };
           love2.lambda(10);
   
           // 花括号也可以省略
           ILove love3 = s -> System.out.println("偶买噶！-->" + s);
           love3.lambda(1);
       }
   }
   
   interface ILove {
       void lambda(int a);
   }
   
   class Love implements ILove {
       @Override
       public void lambda(int a) {
           System.out.println("偶买噶！-->" + a);
       }
   }
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: LambdaTest1.java
    * @time: 2019/10/31 15:18
    * @desc: lambda推导 + 参数
    */
   
   public class LambdaTest2 {
       public static void main(String[] args) {
           ILove love = (int a) -> {
               System.out.println("偶买噶！-->" + a);
           };
           love.lambda(100);
   
           // 参数类型可以省略
           ILove love2 = s -> {
               System.out.println("偶买噶！-->" + s);
           };
           love2.lambda(10);
   
           // 花括号也可以省略
           ILove love3 = s -> System.out.println("偶买噶！-->" + s);
           love3.lambda(1);
       }
   }
   
   interface ILove {
       void lambda(int a);
   }
   
   class Love implements ILove {
       @Override
       public void lambda(int a) {
           System.out.println("偶买噶！-->" + a);
       }
   }
   ```

- lambda推导 + 参数 + 返回值

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: LambdaTest1.java
    * @time: 2019/10/31 15:18
    * @desc: lambda推导 + 参数 + 返回值
    */
   
   public class LambdaTest3 {
       public static void main(String[] args) {
           IInterest in = (int q, int p) -> {
               System.out.println(q + p);
               return q + p;
           };
           in.lambda(100, 50);
   
           // 简化版本
           IInterest in2 = (q, p) -> q + p / 2;
           System.out.println(in2.lambda(10, 20));
       }
   }
   
   interface IInterest {
       int lambda(int a, int b);
   }
   
   // 参考，下面内容可以不要
   class Interest implements IInterest {
       @Override
       public int lambda(int aa, int bb) {
           System.out.println(aa + bb);
           return aa + bb;
       }
   }
   ```

- lambda推导实现线程

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: LambdaTest1.java
    * @time: 2019/10/31 15:18
    * @desc: lambda推导实现线程
    */
   
   public class LambdaTest4 {
       public static void main(String[] args) {
           new Thread(() -> {
               System.out.println("一边学习lambda");
           }).start();
   
           // 简化：花括号可以不要
           new Thread(() -> System.out.println("一边泪流满面")).start();
   
           // 如果是多个语句，就不能省略
           new Thread(() -> {
               for (int i = 0; i < 20; i++) {
                   System.out.println("我疯了，你呢？");
               }
           }).start();
       }
   }
   ```

### 2. 线程状态

-  一个线程对象在它的生命周期内，需要经历5个状态。 

   ![å¾11-4 çº¿ç¨çå½å¨æå¾.png](https://www.sxt.cn/360shop/Public/admin/UEditor/20170526/1495787690411518.png)

  1. **新生状态(New)**

     用new关键字建立一个线程对象后，该线程对象就处于新生状态。处于新生状态的线程有自己的内存空间，通过调用start方法进入就绪状态。

  2. **就绪状态(Runnable)**

     处于就绪状态的线程已经具备了运行条件，但是还没有被分配到CPU，处于“线程就绪队列”，等待系统为其分配CPU。就绪状态并不是执行状态，当系统选定一个等待执行的Thread对象后，它就会进入执行状态。一旦获得CPU，线程就进入运行状态并自动调用自己的run方法。有4中原因会导致线程进入就绪状态：

     1. 新建线程：调用start()方法，进入就绪状态;

     2. 阻塞线程：阻塞解除，进入就绪状态;

     3. 运行线程：调用yield()方法，直接进入就绪状态;

     4. 运行线程：JVM将CPU资源从本线程切换到其他线程。

  3. **运行状态(Running)**

     在运行状态的线程执行自己run方法中的代码，直到调用其他方法而终止或等待某资源而阻塞或完成任务而死亡。如果在给定的时间片内没有执行结束，就会被系统给换下来回到就绪状态。也可能由于某些“导致阻塞的事件”而进入阻塞状态。

  4. **阻塞状态(Blocked)**

     阻塞指的是暂停一个线程的执行以等待某个条件发生(如某资源就绪)。有4种原因会导致阻塞：

     1. 执行sleep(int millsecond)方法，使当前线程休眠，进入阻塞状态。当指定的时间到了后，线程进入就绪状态。

     2. 执行wait()方法，使当前线程进入阻塞状态。当使用nofity()方法唤醒这个线程后，它进入就绪状态。

     3. 线程运行时，某个操作进入阻塞状态，比如执行IO流操作(read()/write()方法本身就是阻塞的方法)。只有当引起该操作阻塞的原因消失后，线程进入就绪状态。

     4. join()线程联合: 当某个线程等待另一个线程执行结束后，才能继续执行时，使用join()方法。

  5. **死亡状态(Terminated)**

     死亡状态是线程生命周期中的最后一个阶段。线程死亡的原因有两个。一个是正常运行的线程完成了它run()方法内的全部工作; 另一个是线程被强制终止，如通过执行stop()或destroy()方法来终止一个线程(注：stop()/destroy()方法已经被JDK废弃，不推荐使用)。

     当一个线程进入死亡状态以后，就不能再回到其它状态了。

- 线程的终止

  1. 线程正常执行完毕-->次数
  2. 外部干涉–>加入标识

  不要使用stop和destroy

  ```java
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: TerminateThread.java
   * @time: 2019/11/1 14:32
   * @desc: 终止线程
   */
  
  public class TerminateThread implements Runnable {
      // 1. 设置标识，标记线程体是否可以运行
      private boolean flag = true;
      private String name;
  
      public TerminateThread(String name) {
          this.name = name;
      }
  
      @Override
      public void run() {
          int i = 0;
          // 2. 关联标识，true-->运行，False-->停止
          while (flag) {
              System.out.println(name + "-->" + i++);
          }
      }
  
      // 3. 对外提供方法改变标识
      public void terminate() {
          this.flag = false;
      }
  
      public static void main(String[] args) {
          TerminateThread tt = new TerminateThread("你大爷");
          new Thread(tt).start();
          for (int i = 0; i < 99; i++) {
              if (i == 88){
                  tt.terminate();     // 线程终止
                  System.out.println("tt game over!");
              }
              System.out.println("main-->" + i);
          }
      }
  }
  ```

- 线程的暂停-sleep： 可以让正在运行的线程进入阻塞状态，直到休眠时间满了，进入就绪状态。 

  ```java
  import java.text.SimpleDateFormat;
  import java.util.Date;
  
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: BlockedSleep1.java
   * @time: 2019/11/1 14:46
   * @desc: sleep模拟倒计时
   */
  
  public class BlockedSleep1 {
      public static void main(String[] args) throws InterruptedException {
          // 倒计时
          Date endTime = new Date(System.currentTimeMillis() + 1000 * 10);
          long end = endTime.getTime();
          while (true) {
              System.out.println(new SimpleDateFormat("mm:ss").format(endTime));
              Thread.sleep(1000);
              endTime = new Date(endTime.getTime()-1000);
              if(end-10000 > endTime.getTime()){
                  break;
              }
          }
      }
  
      public static void test() throws InterruptedException {
          // 倒数10个数，1秒一个
          int num = 10;
          while (true) {
              Thread.sleep(1000);
              System.out.println(num--);
          }
      }
  }
  ```

- 线程的暂停-yield： 可以让正在运行的线程直接进入就绪状态，让出CPU的使用权。 

  ```java
  import org.omg.PortableServer.THREAD_POLICY_ID;
  
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: YieldDemo1.java
   * @time: 2019/11/1 14:55
   * @desc: yield礼让线程，暂停线程，直接进入就绪状态不是阻塞状态
   */
  
  public class YieldDemo1 {
      public static void main(String[] args) {
          MyYield my = new MyYield();
          new Thread(my, "a").start();
          new Thread(my, "b").start();
  
          // lambda实现
          new Thread(() -> {
              for (int i = 0; i < 100; i++) {
                  System.out.println("lambda..." + i);
              }
          }).start();
          for (int i = 0; i < 100; i++) {
              if (i % 20 == 0) {
                  Thread.yield();     // main礼让
              }
              System.out.println("main..." + i);
          }
      }
  }
  
  class MyYield implements Runnable {
      @Override
      public void run() {
          System.out.println(Thread.currentThread().getName() + "-->start");
          Thread.yield();     // 礼让
          System.out.println(Thread.currentThread().getName() + "-->end");
      }
  }
  ```

- 线程的联合-join：合并线程，插队线程。

  ```java
  import sun.java2d.loops.TransformHelper;
  
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: BlockedJoin1.java
   * @time: 2019/11/1 15:05
   * @desc: 爸爸和儿子买烟的故事
   */
  
  public class BlockedJoin1 {
      public static void main(String[] args){
          new Father().start();
      }
  }
  
  class Father extends Thread{
      @Override
      public void run() {
          System.out.println("想抽烟，发现没了");
          System.out.println("让儿子去买中华");
          Thread t = new Son();
          t.start();
          try {
              t.join();       // father被阻塞
              System.out.println("老爸接过烟，把零钱给了儿子");
          } catch (InterruptedException e) {
              e.printStackTrace();
              System.out.println("孩子走丢了，老爸出去找孩子去了...");
          }
      }
  }
  
  class Son extends Thread{
      @Override
      public void run() {
          System.out.println("接过老爸的钱出去了...");
          System.out.println("路边有个游戏厅，玩了10秒");
          for (int i = 0; i < 10; i++) {
              System.out.println(i+"秒过去了...");
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
          System.out.println("赶紧买烟去...");
          System.out.println("手拿一包中华回家了...");
      }
  }
  ```

- 观察线程的各个状态

  ```java
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: AllState.java
   * @time: 2019/11/1 15:22
   * @desc: 观察线程的各个状态
   */
  
  public class AllState {
      public static void main(String[] args) {
          Thread t = new Thread(() -> {
              for (int i = 0; i < 5; i++) {
                  try {
                      Thread.sleep(100);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
              System.out.println("...");
          });
          // 观察状态
          Thread.State state = t.getState();
          System.out.println(state);  // NEW
          t.start();
          state = t.getState();
          System.out.println(state);  // RUNNABLE
  
          while (state != Thread.State.TERMINATED) {
              try {
                  Thread.sleep(200);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              state = t.getState();   // TIMED_WAITING
              System.out.println(state);
          }
          state = t.getState();   // TERMINATED
          System.out.println(state);
      }
  }
  ```

### 3. 线程的优先级

1. NORM_PRIORITY 5

2. MIN_PRIORITY 1

3. MAX_PRIORITY 10

   ```java
   /**
    * @author: Li Tian
    * @contact: litian_cup@163.com
    * @software: IntelliJ IDEA
    * @file: PriorityTest1.java
    * @time: 2019/11/4 12:38
    * @desc: 多线程优先级
    */
   
   public class PriorityTest1 {
       public static void main(String[] args) {
           MyPriority mp = new MyPriority();
           Thread t1 = new Thread(mp);
           Thread t2 = new Thread(mp);
           Thread t3 = new Thread(mp);
           Thread t4 = new Thread(mp);
           Thread t5 = new Thread(mp);
           Thread t6 = new Thread(mp);
   
           t1.setPriority(Thread.MAX_PRIORITY);
           t2.setPriority(Thread.MAX_PRIORITY);
           t3.setPriority(Thread.MAX_PRIORITY);
           t4.setPriority(Thread.MIN_PRIORITY);
           t5.setPriority(Thread.MIN_PRIORITY);
           t6.setPriority(Thread.MIN_PRIORITY);
   
           t1.start();
           t2.start();
           t3.start();
           t4.start();
           t5.start();
           t6.start();
       }
   }
   
   class MyPriority implements Runnable {
       @Override
       public void run() {
           System.out.println(Thread.currentThread().getName() + "-->" + Thread.currentThread().getPriority());
           Thread.yield();
       }
   }
   ```

### 4. 守护线程

- 是为用户线程服务的；JVM停止不用等待守护线程执行完毕

- 默认：用户线程，JVM等待用户线程执行完毕才会停止

  ```java
  import org.omg.PortableServer.THREAD_POLICY_ID;
  
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: DaemonTest.java
   * @time: 2019/11/4 13:35
   * @desc: 守护线程学习
   */
  
  public class DaemonTest {
      public static void main(String[] args) {
          Thread t1 = new Thread(new You1());
          t1.run();
          Thread t2 = new Thread(new God1());
          // 将用户线程调整为守护线程
          t2.setDaemon(true);
          t2.start();
      }
  }
  
  class You1 extends Thread {
      @Override
      public void run() {
          for (int i = 0; i < 365 * 100; i++) {
              System.out.println("happy life!");
          }
          System.out.println("ooo...");
      }
  }
  
  class God1 extends Thread {
      @Override
      public void run() {
          for (;true;) {
              System.out.println("bless you!");
          }
      }
  }
  ```

### 5. 获取线程基本信息的方法

- 常用方法

  ![è¡¨11-1çº¿ç¨çå¸¸ç¨æ¹æ³.png](https://www.sxt.cn/360shop/Public/admin/UEditor/20170526/1495789884517307.png) 

- 案例

  ```java
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: InfoTest.java
   * @time: 2019/11/4 13:46
   * @desc: 获取线程基本信息的方法
   */
  
  public class InfoTest {
      public static void main(String[] args) throws InterruptedException {
          // 线程是否活着
          System.out.println(Thread.currentThread().isAlive());
          // 设置名称：真是角色+代理角色
          MyInfo info = new MyInfo("战斗机");
          Thread t = new Thread(info);
          t.setName("公鸡");
          t.start();
          Thread.sleep(1000);
          System.out.println(t.isAlive());
      }
  }
  
  class MyInfo implements Runnable{
      private String name;
      public MyInfo(String name) {
          this.name = name;
      }
  
      @Override
      public void run() {
          System.out.println(Thread.currentThread().getName() + "-->" + name);
      }
  }
  ```

### 6. 并发控制

- 并发：同一个对象多个线程同时操作

- 线程不安全案例1

  ```java
  package com.sxt.thread;
  
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: UnsafeTest.java
   * @time: 2019/11/4 13:57
   * @desc: 线程同步
   */
  
  public class UnsafeTest {
      public static void main(String[] args) {
          // 账户
          Account account = new Account(100, "结婚礼金");
          Drawing you = new Drawing(account, 80, "可悲的你");
          Drawing wife = new Drawing(account, 90, "happy的她");
          you.start();
          wife.start();
      }
  }
  
  // 账户
  class Account {
      int money;
      String name;
  
      public Account(int money, String name) {
          this.money = money;
          this.name = name;
      }
  }
  
  // 模拟取款
  class Drawing extends Thread {
      // 取钱的账户
      Account accout;
      // 取多少钱
      int drawingMoney;
      // 口袋里的总数
      int packetTotal;
  
      public Drawing(Account accout, int drawingMoney, String name) {
          super(name);
          this.accout = accout;
          this.drawingMoney = drawingMoney;
      }
  
      @Override
      public void run() {
          if(accout.money - drawingMoney < 0){
              return;
          }
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          accout.money -= drawingMoney;
          packetTotal += drawingMoney;
          System.out.println(this.getName() + "-->账户余额为：" + accout.money);
          System.out.println(this.getName() + "-->口袋里的钱为：" + packetTotal);
      }
  }
  ```

- 线程不安全案例2

  ```java
  package com.sxt.thread;
  
  import java.util.ArrayList;
  import java.util.List;
  
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: UnsafeTest.java
   * @time: 2019/11/4 13:57
   * @desc: 线程同步
   */
  
  public class UnsafeTest2 {
      public static void main(String[] args) {
          List<String> list = new ArrayList<>();
          for (int i = 0; i < 10000; i++) {
              new Thread(()->{
                  list.add(Thread.currentThread().getName());
              }).start();
          }
          System.out.println(list.size());
      }
  }
  ```

- 锁机制

  - 为了保证数据在方法中被访问时的正确性，在访问时加入锁机制（synchronized），当一个线程获得对象的排它锁，独占资源，其他线程必须等待，使用后释放锁即可。存在以下问题：

    1. 一个线程持有锁会导致其它所有需要此锁的线程挂起；
    2. 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题；
    3. 如果一个优先级高的线程等待一个优先级低的线程释放锁会导致优先级倒置，引起性能问题。

  - 线程安全：在并发时保证数据的正确性、效率尽可能高（synchronized）

    - 同步方法
    - 同步块（java有四种块，普通块局部块，构造块，静态块，同步块）

  - 样例1：

    ```java
    package com.sxt.thread;
    
    /**
     * @author: Li Tian
     * @contact: litian_cup@163.com
     * @software: IntelliJ IDEA
     * @file: UnsafeTest.java
     * @time: 2019/11/4 13:57
     * @desc: 线程同步
     */
    
    public class SafeTest {
        public static void main(String[] args) {
            // 账户
            Account account = new Account(100, "结婚礼金");
            SafeDrawing you = new SafeDrawing(account, 80, "可悲的你");
            SafeDrawing wife = new SafeDrawing(account, 90, "happy的她");
            you.start();
            wife.start();
        }
    }
    
    // 模拟取款
    class SafeDrawing extends Thread {
        // 取钱的账户
        Account accout;
        // 取多少钱
        int drawingMoney;
        // 口袋里的总数
        int packetTotal;
    
        public SafeDrawing(Account accout, int drawingMoney, String name) {
            super(name);
            this.accout = accout;
            this.drawingMoney = drawingMoney;
        }
    
        @Override
        public void run() {
            test();
        }
    
        public void test() {
            if (accout.money <= 0) {
                return;
            }
            synchronized (accout) {
                if (accout.money - drawingMoney < 0) {
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accout.money -= drawingMoney;
                packetTotal += drawingMoney;
                System.out.println(this.getName() + "-->账户余额为：" + accout.money);
                System.out.println(this.getName() + "-->口袋里的钱为：" + packetTotal);
            }
        }
    }
    ```

  - 样例2

    ```java
    package com.sxt.thread;
    
    import java.util.ArrayList;
    import java.util.List;
    
    /**
     * @author: Li Tian
     * @contact: litian_cup@163.com
     * @software: IntelliJ IDEA
     * @file: UnsafeTest.java
     * @time: 2019/11/4 13:57
     * @desc: 线程同步
     */
    
    public class SafeTest2 {
        public static void main(String[] args) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                new Thread(() -> {
                    // 同步块
                    synchronized (list) {
                        list.add(Thread.currentThread().getName());
                    }
                }).start();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(list.size());
        }
    }
    ```

-  双重检测：考虑临界值的问题

  ```java
  package com.sxt.thread;
  
  /**
   * @author: Li Tian
   * @contact: litian_cup@163.com
   * @software: IntelliJ IDEA
   * @file: Web12306.java
   * @time: 2019/10/30 12:36
   * @desc: 线程安全买票
   */
  
  public class Safe12306 implements Runnable {
      // 票数
      private int ticketNums = 10;
      private boolean flag = true;
  
      @Override
      public void run() {
          while (flag) {
              try {
                  Thread.sleep(100);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              test();
          }
      }
  
      private void test() {
          if (ticketNums <= 0) {  // 考虑的是没有票的情况
              flag = false;
              return;
          }
          synchronized (this) {
              if (ticketNums <= 0) {  // 考虑的是最后一张票的情况
                  flag = false;
                  return;
              }
              try {
                  Thread.sleep(200);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              System.out.println(Thread.currentThread().getName() + "-->" + ticketNums--);
          }
      }
  
      public static void main(String[] args) {
          // 一份资源
          Safe12306 web = new Safe12306();
          // 多个代理
          new Thread(web, "张三").start();
          new Thread(web, "李四").start();
          new Thread(web, "王五").start();
      }
  }
  ```

- 

