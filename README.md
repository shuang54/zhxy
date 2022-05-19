# 智慧校园管理系统

# 一 项目展示

## 1 登录及角色控制



![1649385057395](http://niu.foogeoo.ltd/GitHubImg/1649385057395.png)

## 2首页展示



![1649385122247](http://niu.foogeoo.ltd/GitHubImg/1649385122247.png)



## 3 业务模块展示

![1649385173120](http://niu.foogeoo.ltd/GitHubImg/1649385173120.png)



# 二 智慧校园系统简介

## 2.1  项目简介

 智慧校园管理系统：主要是以年级、班级为单位，进行老师和学生信息记录和统计功能。项目采用前后端分离架构思想，前端采用HTML+CSS+VUE来实现页面效果展示，后端采用SpringBoot+MybatisPlus框架实现数据存储等服务。存储层使用高性能的MySQL，服务器使用SpringBoot内置的Tomcat9.x，项目构建工具使用Maven来管理jar包和项目构建。

## 2.2 项目模块

![项目功能介绍](http://niu.foogeoo.ltd/GitHubImg/项目功能介绍.png)

## 2.3 技术栈





![1646297696190](http://niu.foogeoo.ltd/GitHubImg/1646297696190.png)



> `VUE` 
> 是一套用于构建用户界面的**渐进式框架**。与其它大型框架不同的是，Vue 被设计为可以自底向上逐层应用。Vue 的核心库只关注视图层，不仅易于上手，还便于与第三方库或既有项目整合。另一方面，当与现代化的工具链以及各种支持类库结合使用时，Vue 也完全能够为复杂的单页应用提供驱动。 前后端分离是目前一种非常流行的开发模式，它使项目的分工更加明确：后端：负责处理、存储数据. 前端：负责显示数据 前端和后端开发人员通过 接口 进行数据的交换。
> `Spring`
> 　Spring就像是整个项目中装配bean的大工厂，在配置文件中可以指定使用特定的参数去调用实体类的构造方法来实例化对象。也可以称之为项目中的粘合剂。
> 　Spring的核心思想是IoC（控制反转），即不再需要程序员去显式地`new`一个对象，而是让Spring框架帮你来完成这一切。
>
> `SpringMVC`
> 　SpringMVC在项目中拦截用户请求，它的核心Servlet即DispatcherServlet承担中介或是前台这样的职责，将用户请求通过HandlerMapping去匹配Controller，Controller就是具体对应请求所执行的操作。SpringMVC相当于SSH框架中struts。
> `mybatis-plus`
> 　mybatis是对jdbc的封装，它让数据库底层操作变的透明。mybatis的操作都是围绕一个sqlSessionFactory实例展开的。mybatis通过配置文件关联到各实体类的Mapper文件，Mapper文件中配置了每个类对数据库所需进行的sql语句映射。在每次与数据库交互时，通过sqlSessionFactory拿到一个sqlSession，再执行sql命令。`MyBatis-plus`就是在MyBatis的基础上,为Mapper接口,Service层提供一些比较全面的CURD的业务逻辑功能,使程序员可以减少在Mapper和Service层的代码编写
>
> `MVC项目架构`
>
> 页面发送请求给控制器，控制器调用业务层处理逻辑，逻辑层向持久层发送请求，持久层与数据库交互，后将结果返回给业务层，业务层将处理逻辑发送给控制器，控制器再调用视图展现数据。

## 2.4  软件环境



![1646728754228](http://niu.foogeoo.ltd/GitHubImg/1646728754228.png)

## 2.5 项目结构

1. java目录下
    1. config : 项目的配置类
    2. controller: 控制层
    3. mapper : 持久层接口
    4. pojo :  实体类
    5. service: 服务层
    6. util: 工具类包
    7. ZhxyApplication : 启动类

2. resources目录下
    1. mapper :持久层映射文件
    2. public/upload:文件上传目录
    3. static: 静态资源目录
    4. application.yml :SpringBoot核心配置文件 

![1646279611127](http://niu.foogeoo.ltd/GitHubImg/1646279611127.png)
