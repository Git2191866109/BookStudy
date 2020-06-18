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