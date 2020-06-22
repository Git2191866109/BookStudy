## 7. Cookie

- 会话与状态管理

  - 提出问题：
    - **HTTP协议是一种无状态的协议 **，WEB服务器本身不能识别出哪些请求时同一个浏览器发出的，浏览器的每一次请求都是完全孤立的。
    - 即使HTTP1.1支持持续连接，但当用户有一段时间没有提交请求，连接也会关闭。
    - 怎么才能实现网上商店中的购物车呢：某个用户从网站的登陆页面登陆后，再进入购物页面购物时，负责处理购物请求的服务器程序必须知道处理上一次请求的程序所得到的的用户信息。
    - **作为web服务器，必须能够采用一种机制来唯一地标识一个用户，同时记录该用户的状态。**
  - 会话与会话状态
    - WEB应用中的会话是指一个客户端浏览器与WEB服务器之间连续发生的一系列请求和响应过程。
    - WEB应用的会话状态是指WEB服务器与浏览器在会话过程中产生的状态信息，**借助会话状态，WEB服务器能够把属于同一会话中的一系列的请求和响应过程关联起来。**
  - 如何实现有状态的会话
    - WEB服务器端程序要能从大量的请求消息中区分出哪些请求消息属于同一个会话，即能识别出来自同一个浏览器的访问请求，这**需要浏览器对其发出的每个请求消息都进行标识**：属于同一个会话中的请求消息都附带同样的标识号，而属于不同会话的请求消息总是附带不同的标识号，这个标识号就称为会话ID（**SessionID**）。
    - 在Servlet规范中，常用一下两种机制完成会话跟踪
      - Cookie
      - Session

- Cookie机制

  - cookie机制采用的是在**客户端保持HTTP状态信息的方案**。

  - Cookie是在浏览器访问WEB服务器的某个资源时，**由WEB服务器在HTTP响应消息头中附带传送给浏览器的一个小文本文件。**

  - 一旦WEB浏览器保存了某个Cookie，那么它在以后每次访问该WEB服务器时，**都会在HTTP请求头中将这个Cookie回传给WEB服务器。**

  - 底层的实现原理：WEB服务器通过在HTTP响应消息中**增加Set-Cookie响应头**字段将Cookie信息发送给浏览器，浏览器则通过在HTTP请求消息中**增加Cookie请求头**字段将Cookie回传给WEB服务器。

  - 一个Cookie只能标识一种信息，它至少含有一个标识该信息的名称（NAME）和设置值（VALUE）

  - 一个WEB站点可以给一个WEB浏览器发送多个Cookie，一个WEB浏览器也可以存储多个WEB站点提供的Cookie。

  - 浏览器一般只允许存放300个Cookie，每个站点最多存放20个Cookie，每个Cookie的大小限制为4KB。

  - Cookie的传送过程示意图

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200502131748436.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

- 查看cookie内容

  - cookie.jsp

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/5/2
      Time: 13:22
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" session="false" language="java" %>
    <html>
    <head>
        <title>Title</title>
    </head>
    <body>
    <%
        // 在javaweb规范中使用Cookie类代表cookie
        // 1. 获取Cookie
        Cookie[] cs = request.getCookies();
        if (cs != null && cs.length > 0) {
            for (Cookie cc : cs) {
                // 2. 获取cookie的名字和值
                out.print(cc.getName() + ": " + cc.getValue());
                out.print("<br>");
            }
        } else {
            // 若没有cookie，则创建一个返回
            out.print("没有一个cookie，正在创建并返回...");
            // 1. 创建一个Cookie对象
            Cookie cookie = new Cookie("name", "liyingjun");
            // 设置cookie的最大时效，以秒为单位，若为0，表示立即删除该cookie
            // 若为负数，表示不存储该cookie，若为正数，表示该cookie的存储时间。
            cookie.setMaxAge(30);
    
            // 2. 调用response的一个方法，把cookie传给客户端
            response.addCookie(cookie);
        }
    %>
    
    </body>
    </html>
    ```

  - 第一次请求请求内容中没有cookie请求头，响应中有set-cookie

    ![image-20200502133820227](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200502133820227.png)

  - 再次访问后，请求内容中就有了cookie请求头

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020050213390791.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)
    
  - Servlet API中提供了一个javax.servlet.http.Cookie类来封装Cookie信息，它包含有生成Cookie信息和提取Cookie信息的各个属性的方法。

    - 构造方法：`public Cookie(String name, String value)`
    - getName方法
    - setValue与getValue方法
    - setMaxAge与getMaxAge方法
    - setPath和getPath方法

  - HttpServletResponse接口中定义了一个addCookie方法，它用于在发送给浏览器的HTTp响应消息中增加一个Set-Cookie响应头字段。

  - HttpServletRequest接口中定义了一个getCookies方法，它用于从HTTP请求消息的Cookie请求头字段中读取所有的Cookie项。

- Cookie的发送

  1. 创建Cookie对象
  2. 设置最大时效
  3. 将Cookie放入到HTTP响应报头
     - 如果创建了一个Cookie，并将它发送到浏览器，默认情况下它是一个会话级别的cookie，存储在浏览器的内存中，用户推出浏览器之后被删除。若希望浏览器将该cooke存储在磁盘上，则需要使用maxAge，并给出一个以秒为单位的时间。**将最大时效设为0则是命令浏览器删除该cookie。**
     - 发送cookie需要使用HttpServletResponse的addCookie方法，将cookie插入到一个set-cookie的HTTP响应报头中。由于这个方法并不修改任何之前指定的set-cookie报头，而是创建新的报头，因此将这个方法称之为addCookie而不是setCookie。

- 会话coookie和持久cookie的区别

  - 如果不设置过期时间，则表示这个cookie声明周期为浏览器会话期间，只要关闭浏览器窗口，cookie就消失了。这种生命周期为浏览器会话期的cookie被称为会话cookie。会话cookie一般不保存在硬盘上而是保存在内存里。
  - 如果设置了过期时间，浏览器就会把cookie保存到硬盘上，关闭后再次打开浏览器，这些cookie依然有效直到超过设定的过期时间。
  - 存储在硬盘上的cookie可以在不同的浏览器进程间共享，比如两个IE窗口。而对于保存在内存的cookie，不同的浏览器有不同的处理方式。

- Cookie的读取

  - 调用request.getCookies方法获取浏览器发送来的cookie，需要调用HttpServletRequest的getCookie方法，这个调用返回Cookie对象的数组，对应由Http请求中Cookie报头输入的值。
  - 对数组进行循环，调用每个cookie 的getName方法，直到找到感兴趣的cookie为止

### 7.1 实现自动登录

- 不需要填写用户名和密码等信息，可以自动登录到系统

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617173641903.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

- 代码

  - login.jsp

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/17
      Time: 17:37
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>注册</title>
    </head>
    <body>
        <form action="index.jsp" method="post">
            name: <input type="text" name="name"/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
    </html>
    ```

  - index.jsp

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/5/2
      Time: 13:20
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>$Title$</title>
    </head>
    <body>
    <%
        // 若可以获取到请求参数name，则打印出欢迎信息。把登录信息存储到Cookie中，并设置Cookie的最大时效为30s
        String name = request.getParameter("name");
        if (name != null && !name.trim().equals("")) {
            Cookie cookie = new Cookie("name", name);
            cookie.setMaxAge(30);
            response.addCookie(cookie);
        } else {
            // 这里直接刷新就没有参数name，所以去寻找cookie的信息了
            // 从Cookie中读取用户信息，若存在则打印欢迎信息
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    String cookiename = cookie.getName();
                    if ("name".equals(cookiename)) {
                        String val = cookie.getValue();
                        name = val;
                    }
                }
            }
        }
    
        // 这里看cookie和获取的name是否有一个方法能搞到信息
        if (name != null && !name.trim().equals("")) {
            out.print("Hello: " + name);
        }else{
            // 若既没有请求参数，也没有Cookie，则重定向到login.jsp
            response.sendRedirect("login.jsp");
        }
    
    %>
    </body>
    </html>
    ```

### 7.2 利用Cookie显示最近浏览的商品

- 显示最近浏览的5本书的title

  - books.jsp

    显示最近浏览的5本书

    1. 获取所有的Cookie
    2. 从中筛选出Book的Cookie：如果cookieName为ATGUIGU_BOOK_开头即符合条件
    3. 显示cookieValue

    ```jsp
  <%--
      Created by IntelliJ IDEA.
    User: Administrator
      Date: 2020/6/18
    Time: 14:51
      To change this template use File | Settings | File Templates.
  --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>书名</title>
    </head>
    <body>
    <h4>Books Page</h4>
    <a href="book.jsp?book=JavaWeb">Java Web</a><br><br>
    <a href="book.jsp?book=Java">Java</a><br><br>
    <a href="book.jsp?book=Oracle">Oracle</a><br><br>
    <a href="book.jsp?book=Ajax">Ajax</a><br><br>
    <a href="book.jsp?book=JavaScript">JavaScript</a><br><br>
    <a href="book.jsp?book=Android">Android</a><br><br>
    <a href="book.jsp?book=Jbpm">Jbpm</a><br><br>
    <a href="book.jsp?book=Struts">Struts</a><br><br>
    <a href="book.jsp?book=Hibernate">Hibernate</a><br><br>
    <a href="book.jsp?book=Spring">Spring</a><br><br>
    
    <br><br>
    
    <%
        // 显示最近浏览的5本书
        // 1. 获取所有的Cookie
        Cookie[] cookies = request.getCookies();
        // 2. 从中筛选出Book的Cookie：如果cookieName为ATGUIGU_BOOK_开头即符合条件
        // 3. 显示cookieValue
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if (cookieName.startsWith("LT_")) {
                   out.println(c.getValue());
                   out.print("<br>");
                }
            }
        }
    %>
    
    
    </body>
    </html>
    ```
  
  - book.jsp
  
    把书的信息以cookie方式传回给浏览器，删除一个Cookie
  
    1. 确定要被删除的Cookie：ATGUIHU_BOOK_开头的Cookiue数量大于或等于5，若从books.jsp页面传入的book不在ATGUIGU_BOOK_的Cookie中，则删除较早的Cookie（数组的第一个Cookie），若在其中，则删除该Cookie
  2. 把从books.jsp传入的book作为一个Cookie返回
  
  ```jsp
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="java.util.List" %><%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/18
      Time: 14:54
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>书哦</title>
    </head>
    <body>
    <h4>Book Detail Page</h4>
    Book: <%= request.getParameter("book") %>
    <br><br>
    
    <a href="books.jsp">返回</a>
    
    <%
        String book = request.getParameter("book");
    
        // 把书的信息以cookie方式传回给浏览器，删除一个Cookie
    
        // 1. 确定要被删除的Cookie：
        // 前提：ATGUIHU_BOOK_开头的Cookiue数量大于或等于5，
        Cookie[] cookies = request.getCookies();
        // 保存所有的LT_开头的Cookie
        List<Cookie> bookCookies = new ArrayList<Cookie>();
        // 用来保存和books.jsp传入的book匹配的那个Cookie，
        //   传入的book在cookies里面了，就给tmp，这样后面好直接删
        //   如果不在里面，则删除第一个位置的（长度够了的话），长度不够，就不管
        Cookie tmpCookie = null;
    
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if (cookieName.startsWith("LT_")) {
                    bookCookies.add(c);
    
                    if (c.getValue().equals(book)) {
                        tmpCookie = c;
                    }
    
                }
            }
        }
        // 1.1 若从books.jsp页面传入的book不在ATGUIGU_BOOK_的Cookie中，则删除较早的Cookie（数组的第一个Cookie）
        if (bookCookies.size() >= 5 && tmpCookie == null) {
            tmpCookie = bookCookies.get(0);
        }
    
        // 1.2 若在其中，则删除该Cookie，这里删除通过设置该cookie的maxage为0
        if (tmpCookie != null) {
            tmpCookie.setMaxAge(0);
            ;
            response.addCookie(tmpCookie);
        }
    
        // 2. 把从books.jsp传入的book作为一个Cookie返回
        Cookie cookie = new Cookie("LT_" + book, book);
        response.addCookie(cookie);
    %>
    
    
    </body>
    </html>
  ```

### 7.3 设置Cookie的作用路径

- 通过设置Cookie的作用域可以访问作用域下的cookie，否则 
  Cookie的作用范围：可以作用当前目录和当前目录的子目录，但不能作用于当前目录的上一级目录。

- 代码示例

  - cookie2.jsp：如果能读到cookie则返回cookie的值，否则输出没有指定的cookie

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/18
      Time: 16:06
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>设置Cookie的作用路径</title>
    </head>
    <body>
    <%-- 读取一个name为cookiePath的Cookie --%>
    <%
        String cookieValue = null;
    
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("cookiePath".equals(cookie.getName())){
                    cookieValue = cookie.getValue();
                }
            }
        }
        if(cookieValue != null){
            out.print(cookieValue);
        }else{
            out.print("没有指定的cookie！");
        }
    %>
    </body>
    </html>
    ```

  - writeCookie.jsp：向客户端写入一个cookie

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/18
      Time: 16:10
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>写入Cookie</title>
    </head>
    <body>
    <%-- 向客户端浏览器写入一个Cookie: cookiePath, cookiePathValue --%>
    <%
        Cookie cookie = new Cookie("cookiePath", "CookiePathValue");
        // 设置Cookie的作用范围，然后就可读到上一目录的cookie，
        // 下一行代码是指，将cookie的作用范围设置为web应用的根目录
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    
        // Cookie的作用范围：可以作用当前目录和当前目录的子目录，
        // 但不能作用于当前目录的上一级目录。
        // 可以通过setPath的方式来设置Cookie的作用范围，其中/代表站点的根目录
    %>
    
    <a href="../cookie2.jsp">To Read Cookie</a>
    <br><br>
    <%= request.getContextPath() %>
    
    </body>
    </html>
    ```

## 8. HttpSession

- session在不同环境下的不同含义

  - session，中文经常翻译为会话，其本来的含义是指有始有终的一系列动作/消息，比如打电话是从拿起电话拨号到挂断电话这中间的一系列过程可以称之为一个session。
  - session在Web开发环境下的语义又有了新的扩展，它的含义是指 **一类用来在客户端与服务器端之间保持状态的解决方案。有时候Session也用来指这种解决方案的存储结构。**

- Session机制

  - session机制采用的是在**服务器端**保持http状态信息的方案。

  - 服务器使用一种类似于散列表的结构（也可能就是使用散列表）来保存信息。

  - 当程序需要为某个客户端的请求创建一个session时，服务器首先检查这个 **客户端的请求里**是否包含了一个session标识（即sessoinId），如果已经包含一个sessionId则说明以前已经为此客户端创建过session，服务器就按照sessionid把这个session检索出来使用（如果检索不到，可能会新建一个，这种情况可能出现在服务器已经删除了改用户对应的session对象，但用户认为的在请求的URl后面附加上了一个Jsession的参数）。如果客户请求不包含sessionId，则为此客户创建一个session并且生成一个与此session相关联的sessionId， **这个session id 将在本次响应中返回给客户端保存**。

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200619180801568.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

- 保存session id的几种方式

  - 保存session id的方式可以采用**cookie**，这样在交互过程中浏览器可以**自动的**按照规则把这个标识发送给服务器。
  - 由于cookie可以被人为的禁用，必须有其他的机制以便在cookie被禁用时仍然能够把session id传递回服务器，经常采用的一种技术叫做**URL重写，就是把session id附加在URL路径的后面**，附加的方式也有两种，一种是作为URL路径的附加信息，另一种是作为查询字符串附加在URL后面。网络在整个交互过程中始终保持状态，就必须在每个客户端可能请求的路径后面都包含这个session id。

- session cookie

  - session通过sessionID来区分不同的客户，session是以cookie或URL重写作为基础的，**默认使用cookie来实现，系统会创造一个名为JSESSIONID的输出cookie**，这称之为 **session cookie**，以区别 **persistent cookies**（也就是我们通常所说的cookie），**session cookie是存储于浏览器内存中的，并不是写道硬盘上的**，通常看不到JSESSIONID，但是当把浏览器的cookie禁止后，web服务器会采用URL重写的方式传递SessionID，这时地址栏可以看到
  - session cookie针对某一次会话而言，会话结束session cookie也就随着消失了，而persistent cookie只是存在于客户端硬盘上的一段文本。
  - 关闭浏览器，指挥使浏览器端内存里的session cookie小时，但不会使保存在服务器端的session对象消失，同样也不会使已经保存到硬盘上的持久化cookie消失。

### 8.1 HttpSession的生命周期

- **什么时候创建HttpSession对象**

  - 对于JSP而言：是否浏览器访问服务端的任何一个JSP或Servlet，服务器就会立即创建一个HttpSession对象呢？

    不一定！

    1. 若当前的JSP是客户端访问的当前WEB应用的第一个资源，且JSP的page指定的session属性值为false，则服务器就不会为jsp创建一个HttpSession对象。
    2. 若当前的JSP不是客户端访问的当前WEB应用的第一个资源，且其他页面已经创建了一个HttpSession对象，则服务器也不会为当前JSP页面创建一个HttpSession对象，而会为当前JSP页面返回一个和当前会话关联的HttpSession对象。

  - jsp中设置session=false表示：当前jsp页面禁用session隐含变量，但可以使用其他的显式的HttpSession对象。

  - 对于Servlet而言：若Servlet是客户端访问的第一个WEB应用的资源，则只有调用了request.getSession()或request.getSession(true)才会创建HttpSession对象。

  - 在Servlet中如何获取HttpSession对象？

    - request.getSession(boolean create)：若create为false，则若没有和当前jsp页面关联的HttpSession对象，则返回null；若有则返回true。

      ```
      HttpSession session = request.getSession(false);
      ```

    - 若create为true，一定返回一个HttpSession对象。若没有和当前jsp页面关联的HttpSession对象，则服务器创建一个新的HttpSession对象返回，若有，直接返回关联的对象。

    - request.getSession()：等同于设置create为true。

- **什么时候销毁HttpSession对象**

  - 直接调用HttpSession的invalidate()方法。该方法使HttpSession失效。

  - 服务器卸载了当前WEB应用。（服务器进程被停止）

  - 超出HttpSession的过期时间。（超过了session的最大有效时间）

    ```
    session.getMaxInactiveInterval()
    session.setMaxInactiveInterval()
    ```

    - 设置HttpSession的过期时间：单位为秒

    - 在web.xml文件中设置HttpSession的过期时间（Tomcat路径下的，单位为分钟）

      ```xml
      <session-config>
      	<session-timeout>30</session-timeout>
      </session-config>
      ```

  - 并不是关闭了浏览器就销毁了HttpSession。

    - 持久化cookie
    - URL重写
  
- **HttpSession的相关的API**

  1. 获取Session的对象：request.getSession()、request.getSession(boolean create)
  2. 属性相关的：setAttribute、getAttribute、removeAttribute
  3. 使HttpSession失效的：invalidate()方法
  4. 设置其最大时效的setMaxInactiveInterval

### 8.2 HttpSession常用方法示例

- session原理

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200620175501573.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

- session代表了浏览器跟服务器的一次会话，其特点是，只要浏览器不关，至始至终都是一个session

- 例子：制作一个登录，重新登录，注销的界面

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200620175649519.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

- 代码

  - login.jsp

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/20
      Time: 17:29
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>登录</title>
    </head>
    <body>
    Session ID: <%= session.getId() %>
    <br><br>
    isNew: <%= session.isNew() %>
    <br><br>
    MaxInactiveInternal: <%= session.getMaxInactiveInterval() %>
    <br><br>
    CreateTime: <%= session.getCreationTime() %>
    <br><br>
    LastAccessTime: <%= session.getLastAccessedTime() %>
    <br><br>
    
    <%
        Object username = session.getAttribute("username");
        if (username == null) {
            username = "";
        }
    %>
    
    <form action="hello.jsp" method="post">
        username: <input type="text" name="username" value="<%= username %>"/>
        <input type="submit" value="Submit"/>
    </form>
    </body>
    </html>
    ```

  - hello.jsp

    ```jsp
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>欢迎界面</title>
    </head>
    <body>
    Session ID: <%= session.getId() %>
    <br><br>
    isNew: <%= session.isNew() %>
    <br><br>
    MaxInactiveInternal: <%= session.getMaxInactiveInterval() %>
    <br><br>
    CreateTime: <%= session.getCreationTime() %>
    <br><br>
    LastAccessTime: <%= session.getLastAccessedTime() %>
    <br><br>
    Hello: <%= request.getParameter("username") %>
    <br><br>
    
    <%-- 如果不用session的方式的话，怎么使得重新登录的时候回传用户名 --%>
    <%--<a href="login.jsp?username=<%=request.getParameter("username")%>">重新登录</a>--%>
    
    <%-- 这里用session做的话,就不需要在后面添加?username等 --%>
    <%
        session.setAttribute("username", request.getParameter("username"));
    %>
    <a href="login.jsp">重新登录</a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="logout.jsp">注销</a>
    
    </body>
    </html>
    ```

  - logout.jsp

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/20
      Time: 17:50
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>注销</title>
    </head>
    <body>
    Session ID: <%= session.getId() %>
    <br><br>
    isNew: <%= session.isNew() %>
    <br><br>
    MaxInactiveInternal: <%= session.getMaxInactiveInterval() %>
    <br><br>
    CreateTime: <%= session.getCreationTime() %>
    <br><br>
    LastAccessTime: <%= session.getLastAccessedTime() %>
    <br><br>
    Bye: <%= session.getAttribute("username") %>
    <br><br>
    
    <a href="login.jsp">重新登录</a>
    
    <%
        session.invalidate();
    %>
    
    </body>
    </html>
    ```

### 8.3 利用URL重写实现Session跟踪

- Servlet规范中引入了一种补充的会话管理机制，**它允许不支持Cookie的浏览器也可以与WEB服务器保持连续的会话**。这种补充机制要求在响应消息的实体内容中必须包含下一次请求的超链接，并将会话标识号作为超链接的URL地址的一个特殊参数。

- **将会话标识号以参数形式附加在超链接的URL地址后面的技术成为URL重写**。如果在浏览器不支持Cookie或者关闭了Cookie功能的情况下，WEB服务器还要能够与浏览器实现有状态的会话，就必须对所有可能被客户端访问的请求路径（包括超链接、form表单的action属性设置和重定向的URL）进行URL重写。

- HttpServletResponse接口中定义了两个用于完成URL重写的方法：

  - encodeURL方法
  - encodeRedirectURL方法

- 实验

  - 如果禁止了cookie，每次刷新的时候sessionid都会变

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200620195237597.gif)

  - 怎么解决？

    - 在login.jsp中，注释的为解决前的代码，后面的为解决后的代码

      ```jsp
      <%--<form action="hello.jsp" method="post">--%>
      <form action="<%= response.encodeURL("hello.jsp") %>" method="post">
      ```

    - hello.jsp中

      ```jsp
      <%--<a href="login.jsp">重新登录</a>--%>
      <a href="<%= response.encodeURL("login.jsp") %>">重新登录</a>
      
      <%--<a href="logout.jsp">注销</a>--%>
      <a href="<%= response.encodeURL("logout.jsp") %>">注销</a>
      ```

    - 利用URL重写实现后，在原来的url后面增加了`;jsessionid=`，就可以实现禁止cookie的情况下，找到服务器的session对象。

      ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200620200753405.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

### 8.4 实现简易购物车

- 练习框架

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200621180958554.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

- 代码

  - step1.jsp：选择要购买的图书界面

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/21
      Time: 18:12
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>选择要购买的书籍</title>
    </head>
    <body>
    <h4>第一步：选择要购买的图书：</h4>
    <form action="<%= request.getContextPath() %>/processStep1" method="post">
        <table border="1" cellpadding="10" cellspacing="0">
            <tr>
                <td>书名</td>
                <td>购买</td>
            </tr>
            <tr>
                <td>Java</td>
                <td><input type="checkbox" name="book" value="Java"></td>
            </tr>
            <tr>
                <td>Oracle</td>
                <td><input type="checkbox" name="book" value="Oracle"></td>
            </tr>
            <tr>
                <td>Struts</td>
                <td><input type="checkbox" name="book" value="Struts"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Submit">
                </td>
            </tr>
        </table>
    </form>
    
    </body>
    </html>
    ```

  - step1Setvlet.java：在step1.jsp中选择书了之后，跳转的Servlet进行处理，最终转发到step2.jsp中。

    ```java
    package com.litian.javaweb;
    
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    
    @WebServlet(name = "s1", urlPatterns = "/processStep1")
    public class ProcessStep1Servlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 1. 获取选中的图书的信息
            String [] books = request.getParameterValues("book");
            // 2. 把图书信息放入到HttpSession中
            request.getSession().setAttribute("books", books);
            // 3. 重定向页面到shoppingcart/step2.jsp
            System.out.println(request.getContextPath() + "/shoppingcart/step2.jsp");
            response.sendRedirect(request.getContextPath() + "/shoppingcart/step2.jsp");
        }
    }
    ```

  - step2.jsp：确认收货地址等信息的页面

    ```jsp
    <%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/21
      Time: 18:26
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>确认收货地址和信用卡信息</title>
    </head>
    <body>
    <h4>第二步：请输入收货地址和信用卡信息：</h4>
    <form action="<%= request.getContextPath() %>/processStep2", method="post">
        <table cellpadding="10" cellspacing="0" border="1">
            <tr>
                <td colspan="2">寄送信息</td>
            </tr>
            <tr>
                <td>姓名：</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>地址：</td>
                <td><input type="text" name="address"></td>
            </tr>
            <tr>
                <td colspan="2">信用卡信息</td>
            </tr>
            <tr>
                <td>种类：</td>
                <td>
                    <input type="radio" name="cardType" value="Visa"/>Visa
                    <input type="radio" name="cardType" value="Master"/>Master
                </td>
            </tr>
            <tr>
                <td>卡号：</td>
                <td><input type="text" name="card"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Submit"/></td>
            </tr>
        </table>
    </form>
    </body>
    </html>
    ```

  - step2Servlet.java：在step2.jsp页面中的信息传到这里来进行处理，然后重定向到确认页面。

    ```java
    package com.litian.javaweb;
    
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    import java.io.IOException;
    
    /**
     * @author: Li Tian
     * @contact: litian_cup@163.com
     * @software: IntelliJ IDEA
     * @file: ${NAME}.java
     * @time: 2020/6/22 10:18
     * @desc: |
     */
    
    @WebServlet(name = "ProcessStep2Servlet", urlPatterns = "/processStep2")
    public class ProcessStep2Servlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 1. 获取请求参数：name，address，cardType，card
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String cardType = request.getParameter("cardType");
            String card = request.getParameter("card");
    
            // 把客户信息封装到customer对象中
            Customer customer = new Customer(name, address, cardType, card);
    
            // 2. 把请求信息存入到HttpSession中
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
    
            // 3. 重定向页面到confirm.jsp
            response.sendRedirect(request.getContextPath() + "/shoppingcart/confirm.jsp");
        }
    }
    ```

  - confirm.jsp：确认页面，确认你的书、个人信息等。

    ```jsp
    <%@ page import="com.litian.javaweb.Customer" %><%--
      Created by IntelliJ IDEA.
      User: Administrator
      Date: 2020/6/22
      Time: 10:24
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>确认信息</title>
    </head>
    <body>
    
    <%
        Customer customer = (Customer) session.getAttribute("customer");
        String[] books = (String[]) session.getAttribute("books");
    %>
    
    <table>
        <tr>
            <td>顾客姓名：</td>
            <td><%= customer.getName() %></td>
        </tr>
        <tr>
            <td>地址：</td>
            <td><%= customer.getAddress() %></td>
        </tr>
        <tr>
            <td>卡号：</td>
            <td><%= customer.getCard() %></td>
        </tr>
        <tr>
            <td>卡的类型：</td>
            <td><%= customer.getCardType() %></td>
        </tr>
        <tr>
            <td>Books: </td>
            <td>
                <%
                    for(String book: books){
                        out.print(book);
                        out.print("<br>");
                    }
                %>
            </td>
        </tr>
    </table>
    </body>
    </html>
    ```

  - Customer.java：用来封装客户的个人信息，这样方便将step2中表单的信息传递到confirm.jsp中。

    ```java
    package com.litian.javaweb;
    
    /**
     * @author: Li Tian
     * @contact: litian_cup@163.com
     * @software: IntelliJ IDEA
     * @file: Customer.java
     * @time: 2020/6/22 10:21
     * @desc: |
     */
    
    public class Customer {
        private String name;
        private String address;
        private String cardType;
        private String card;
    
        public Customer() {
        }
    
        public Customer(String name, String address, String cardType, String card) {
            this.name = name;
            this.address = address;
            this.cardType = cardType;
            this.card = card;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getAddress() {
            return address;
        }
    
        public void setAddress(String address) {
            this.address = address;
        }
    
        public String getCardType() {
            return cardType;
        }
    
        public void setCardType(String cardType) {
            this.cardType = cardType;
        }
    
        public String getCard() {
            return card;
        }
    
        public void setCard(String card) {
            this.card = card;
        }
    }
    ```

### 8.5 JavaWEB中的相对路径和绝对路径

1. 绝对路径的问题：
   1. 开发时建议编写 **绝对路径**：写绝对路径肯定没有问题，但是写相对路径却可能会有问题。
   2. 

