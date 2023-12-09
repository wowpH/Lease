### 2022-09-05 09:43:15

不想用lombok插件了，删掉了lombok依赖，启动后登录报错。原因是缺少无参构造方法。

```java
class com.ph.lease.entity.Admin with invalid types()or values().Cause:java.lang.NoSuchMethodException:com.ph.lease.entity.Admin.<init>()]with root cause
        java.lang.NoSuchMethodException:com.ph.lease.entity.Admin.<init>()
        at java.lang.Class.getConstructor0(Class.java:3082)
        at java.lang.Class.getDeclaredConstructor(Class.java:2178)
```

### 2020-4-26 19:50:51

t表示有效，f表示无效，，，映射文件不要写错了啊。。。半天没找出错误

### 2020-4-24 22:03:32

获取最近记录的时候，不能按照创建时间降序，因为可能出现时间一样的，应该按照主键降序排序

### 2020-4-24 15:13:41

mapper映射文件，查询单独的varchar字段的时候，resultType要设为String

### 2020-4-23 23:13:52

前端输入框的日期字符串，通过ajax发送到后端时，报错400，
问题是字符串无法绑定到实体类的Date类型的变量，
解决办法：https://blog.csdn.net/panchang199266/article/details/81951872

### 2020-4-23 21:19:53

form.on一定要return false，否则会刷新页面

### 2020-4-21 22:42:56

订单编号没有设计好，现在该怎么生成订单编号呢

### 2020-4-21 18:19:02

数据库SQL语句记得取别名，不然容易乱，
Java日期类java.sql.Date和java.util.Date不一样，前者是后者的子类，导包不要导错了。
如果时间缺少时分秒，检查一下导包

### 2020-4-19 23:02:20

decimal比较要用=，我写成了like，关键是mysql还不报错，不好找

### 2020-4-19 22:51:16

xml映射文件用count函数查询记录数的时候，需要制定resultType为int或者Integer，不指定会报错

### 2020-4-19 21:07:23

巨坑
函数中，$.ajax后面的语句先执行，我打算将return语句放到complete中，还是不行，我只能妥协了，传入index参数，在函数里面关闭弹出层
原因是我用的ajax是异步方式，执行ajax的同时会执行后面的语句，可以考虑改为同步，即async: false

### 2020-4-19 20:03:56

ajax如果一直是error而不是success，可能是Controller没有添加@ReponseBody注解

注意：除了返回页面的情况，其他情况全部都要加@ReponseBody注解

### 2020-4-17 12:38:53

插入重复字段的时候报错

```text
java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '13016405502' for key 'tbl_customer_telephone_uindex'
```

在xml映射文件中的insert into中间添加ignore变为：insert ignore into

### 2020-4-15 10:10:37

```text
2020-04-15 10:08:32 831  WARN org.springframework.web.servlet.PageNotFound:org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1118) - No mapping found for HTTP request with URI [/loginCheck.do] in DispatcherServlet with name 'springmvc'
```

改了请求之后，别忘了Ctrl+F5刷新浏览器缓存

### 2020-4-12 18:49:01

```text
严重: Servlet.service() for servlet [springmvc] in context with path [] threw exception [Request processing failed; nested exception is org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named 'name' in 'class com.ph.lease.entity.Admin'] with root cause
org.apache.ibatis.reflection.ReflectionException: There is no getter for property named 'name' in 'class com.ph.lease.entity.Admin'
```

修改Admin实体类的name为username后，mapper.xml文件中的name忘改了

### 2020-4-12 00:22:50

```text
2020-04-12 00:19:54 650  WARN org.springframework.web.servlet.PageNotFound:org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1118) - No mapping found for HTTP request with URI [/layui/css/layui.css] in DispatcherServlet with name 'springmvc'
2020-04-12 00:19:54 660  WARN org.springframework.web.servlet.PageNotFound:org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1118) - No mapping found for HTTP request with URI [/layuiadmin/css/login.css] in DispatcherServlet with name 'springmvc'
2020-04-12 00:19:54 665  WARN org.springframework.web.servlet.PageNotFound:org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1118) - No mapping found for HTTP request with URI [/layui/layui.js] in DispatcherServlet with name 'springmvc'
2020-04-12 00:19:54 661  WARN org.springframework.web.servlet.PageNotFound:org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1118) - No mapping found for HTTP request with URI [/layuiadmin/css/admin.css] in DispatcherServlet with name 'springmvc'
2020-04-12 00:19:54 666  WARN org.springframework.web.servlet.PageNotFound:org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1118) - No mapping found for HTTP request with URI [/lease/js/login.js] in DispatcherServlet with name 'springmvc'
2020-04-12 00:19:54 690  WARN org.springframework.web.servlet.PageNotFound:org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1118) - No mapping found for HTTP request with URI [/lease/js/login.js] in DispatcherServlet with name 'springmvc'
```

找不到静态资源，在mvc配置文件添加

```xml

<mvc:default-servlet-handler/>
```

### 2020-4-9 20:28:59

xml文件上方出现

```text
MVC application context in module lease.file is included in 4 contexts.
```

File > Project Structure > Modules > Spring 点击减号，全部删完后点击加号，选中当前Modules并确定

### 2020-4-9 19:55:35

用Alt+Shift+R修改包名的时候，配置文件中的双引号中的名称是不会变的，会出现mapper未注入的情况

1、检查以下spring配置文件，用Ctrl+F搜索旧包名即可

### 2020-4-9 09:00:46

启动项目时，控制台出现：

```text
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
```

在pom.xml文件中添加如下代码：

```xml

<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```
