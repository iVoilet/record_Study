



Spring的构造注入

![image-20230913192409337](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230913192409337.png)

![image-20230913192354681](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230913192354681.png)





内部bean

![image-20230913193132618](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230913193132618.png)



简单类型的注入，使用value不使用ref

![image-20230913211043426](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230913211043426.png)



![image-20230913221205989](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230913221205989.png)





xml文件中注入数组，List，Set的时候，只是把array标签改为list,set即可

![image-20230914100652413](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230914100652413.png)



map则有所不同，引入entry

![image-20230914101115681](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230914101115681.png)





关于赋空值和null![image-20230914102656453](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230914102656453.png)



P空间，C空间，Util空间命名看pdf94页



自动装配是指，不配置value属性，而在后面添加autowire=“byName”的属性，例如外部类的id为“aaa”，那我们就需要提供一个setAaa的方法，然后就会自己装配了

![image-20230914104046115](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230914104046115.png)



当我们选择是是autowire="byType"的时候，也是调用set方法，但是不能出现两个class属性一样的bean，不然会报错。



使用FactoryBean的时候，定义的类要实现接口FactoryBean<E>，然后在xml文件里面这样写，class的路径时工厂的路径，得到的bean是getObject()方法里面的类型

还有其他几种自己写工厂的方法，要指定factory-method，较为复杂，不推荐使用。

![image-20230914153113109](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230914153113109.png)



BeanFactory和FactoryBean的区别

![image-20230914153533607](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230914153533607.png)

注解的标志

![image-20230915105425683](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20230915105425683.png)





# AOP

#### 什么是AOP?

 AOP(Aspect Oriented Programming)面向切面编程，一种编程范式，指导开发者如何组织程 序结构。 OOP(Object Oriented Programming)面向对象编程 我们都知道OOP是一种编程思想，那么AOP也是一种编程思想，编程思想主要的内容就是指导程序员该 如何编写程序，所以它们两个是不同的编程范式。 

#### AOP作用 作用:

在不惊动原始设计的基础上为其进行功能增强，前面咱们有技术就可以实现这样的功能即代 理模式。 前面咱们有技术就可以实现这样的功能即代理模式。

#### 连接点与切入点，通知类与切面：

连接点是可以进行aop的点，切入点是实际进行aop的点。

![image-20231009201933658](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009201933658.png)

通知：共性

通知类：放通知的类，也就是公共的东西

切面：在哪个切入点切入哪些通知。

![image-20231009202144699](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009202144699.png)

## AOP案例

#### 第一步，导入依赖，主要是aspect那个

```
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.4</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>6.0.11</version>
    </dependency>
```

启动AOP需要的配置，一个是在Configuration里面加上EnableAspectJAutoProxy,这个是说明我们有引入注解开发，然后再通知类里面，加上@Aspect

![image-20231009205759069](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009205759069.png)

![image-20231009205936532](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009205936532.png)

总结：第一步要导入依赖，然后要注册通知类，通知类上面添加注释Aspect；通知类中要写Pointcut（切点）和通知方法，然后通过Before，After等来确定。第三步是要再Configuration里面配上EnableAspectJAutoProxy的注释



### AOP工作流程

1、Spring容器启动

2、读取所有切面配置中的切入点

3、初始化bean,判定bean对应的类中的方法是否匹配到任意切入点



AOP使用的是代理模式进行的

不论是使用接口还是实现类的方法，都是ok的

### AOP切入点表示式

对于切入点表达式的语法为: 

切入点表达式标准格式：动作关键字(访问修饰符 返回值 包名.类/接口名.方法名(参数) 异常 名）

对于这个格式，我们不需要硬记，通过一个例子，理解它: 

```
execution(public User com.itheima.service.UserService.findById(int))
```

execution：动作关键字，描述切入点的行为动作，例如execution表示执行到指定切入点

public:访问修饰符,还可以是public，private等，可以省略

User：返回值，写返回值类型

com.itheima.service：包名，多级包使用点连接

UserService:类/接口名称

findById：方法名 int:参数，直接写参数的类型，多个类型用逗号隔开

异常名：方法定义中抛出指定异常，可以省略

#### 通配符来表示切入点

![image-20231011143252430](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011143252430.png)

AOP书写技巧

![image-20231011144140022](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011144140022.png)



**around使用，注意点是**

**@Around("")**  

**引入ProceedingJoinPoint  pip**

**pip.proceed()	区分around的前后**

**处理异常**

![image-20231011150045288](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011150045288.png)





当原始方法有返回值的时候，要输出return ,不然报错，用法如下

![image-20231011150616632](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011150616632.png)

Before，After，Around，AfterReturning，AfterThrowing

AfterReturning是要运行成功后，如果出异常了，就不会执行。

最重要的是Around

#### Around的注意事项

对于第二点，可以进行隔离，作为一些权限的作业

![image-20231011150915933](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011150915933.png)

### AOP例子



#### AOP获取参数、异常、返回值

获取参数args，而且这个参数是可以修改的，然后用在pjp.proceed(Object[])里面，从而处理数据，实现更强的健壮性

```

```

获取返回值

#### 案例：百度网盘密码兼容问题

trim()处理





## 理解AOP

对于大量同样的操作需要添加的时候，可以先考虑AOP

AOP也可以增强健壮性，处理参数等。





# Spring事务开发









