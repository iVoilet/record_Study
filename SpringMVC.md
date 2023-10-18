### SpringMVC的配置类：

```java
public class ServletContainersInitConfig extends
AbstractDispatcherServletInitializer {
//加载springmvc配置类
protected WebApplicationContext createServletApplicationContext() {
//初始化WebApplicationContext对象
AnnotationConfigWebApplicationContext ctx = new
AnnotationConfigWebApplicationContext();
//加载指定配置类
ctx.register(SpringMvcConfig.class);
return ctx;
}
//设置由springmvc控制器处理的请求映射路径
protected String[] getServletMappings() {
return new String[]{"/"};
}
//加载spring配置类
protected WebApplicationContext createRootApplicationContext() {
return null;
}
}
```

常见的注解开发命令：

![image-20231011175222219](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011175222219.png)

![image-20231011175235724](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011175235724.png)



![image-20231011175248166](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011175248166.png)

实践案例展示：

![image-20231011175305844](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011175305844.png)



1、导入依赖

2、定义Controller,这个类里面写的是路径和返回值类型，即上面那张图，可以写多个路径

3、写一个Configuration注释的类

4、定义一个servlet容器启动的配置类，在里面加载spring的配置，如下面这张图

![image-20231011201651208](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011201651208.png)

5、加入tomcat的插件<build>...</build>

6、运行

7、启动类的精简

![image-20231011203834825](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011203834825.png)

## Bean加载

背景：Spring和SPringMVC的要管理各自的bean，为了不让bean被两边都加载，所以要区分

思路：一种是精确导包，另一种是全导入然后排除一部分

对于第二种的实现：如下代码的意思是，value包名下的所有，除了指定的类型的注释不要，其他都要

```
@ComponentScan(value="包名",
excludeFilters=@ComponentScan.Filter(
type=FilterType.ANNOTATION,classes=Controller.class))
```





#### 请求路径的前缀

当在类前哪里加了RequestMapping("/路径")后，意味着类里面都是默认加上这个前缀了，因此会减少重复写

![image-20231011204412811](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011204412811.png)



#### 获取get请求的参数

通过在方法上面加参数即可，只要名字对上了，就可以接收

![image-20231011204941431](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011204941431.png)

那如果不一样呢？

加入如下代码,这样就可以用userName来代替name，相当于是一个重命名。

```
(@RequestParam("name") String userName,int age)
```

##### pojo

当我们收到的数据是要用于pojo类的属性赋值呢？直接用pojo类来接受就可以了

![image-20231011210136432](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011210136432.png)

![image-20231011210152504](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011210152504.png)

##### 嵌套pojo

![image-20231011210259960](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011210259960.png)

![image-20231011210344788](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011210344788.png)

##### 数组

![image-20231011210411565](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011210411565.png)



##### JSON对象

1、导入依赖

2、在@Configuration文件中添加注解@EnableWebMvc，这样子我们的mvc就可以将JSON文件转为对象

3、参数添加@RequestBody的注释。



##### 日期型参数传递

如果最终的展示想要是2023-10-1的形式，那么方法的对应参数前添加注释@DateTimeFormat(pattern="yyyy-MM-dd")，其他的变种，根据之前学的Date类型改动即可。

@EnableWebMvc也可以打开一些默认的转换形式

#### PostMan中POST的选择

当为POST的时候，选择如下

![image-20231011205155244](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231011205155244.png)



#### 乱码的过滤器

在springMVC的配置文件里面加入如下的代码

```
//乱码处理
@Override
protected Filter[] getServletFilters() {
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("UTF-8");
    return new Filter[]{filter};
}
```



## 响应

### 响应页面

return 页面路径

### 响应文本数据

return 文本即可

### 响应POJO对象

不论是单个还是集合

如果没有@ResponseBody的话，返回值只能是字符串，但是有了就可以返回其他类型，转为JSON，当然这一步需要导入Jackson依赖



该功能是HttpMessageConverter这个接口实现的。



## REST风格

中文名表现形式状态转换

REST风格简介

![image-20231016212127761](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231016212127761.png)

那我们使用了这种模糊的形式，我们应该怎么区分呢？通过类型

![image-20231016212836546](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231016212836546.png)

理论可行，那么我们如何在代码里面区分呢？

在RequestMapping里面添加method属性，并在后面指定类型（原本只有路径）

![image-20231016212939533](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231016212939533.png)

问题又来了，参数如何传呢？

分两步，第一步要说明参数来自于路径@PathVariable

第二步要在参数上面加内容例如下

![image-20231016213341158](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231016213341158.png)

一般而言，是单个参数的时候用@PathVariable，但是多个参数的时候，一般用封装成pojo，然后用JSON接受



## 《提取公因子》

@RequestMapping		可以提到类前面，填上共有的路径

@RequestBody				可以提到类前面，相当于每一个都写了

@RestController			等于@ResponseBody和@Controller的结合



在类中，@PostMapping《====》@RequestMapping(method=RequestMethod.POST)

@DeleteMapping("/{id}")《====》@RequestMapping(value="/{id}" method=method=RequestMethod.DELETE)



## 设置静态资源过滤

背景，由于我们设置了SpringMVC管控了所有的路径访问，导致了tomcat无法获取静态资源html等，我们需要给改资源放行

1、创建该类继承WebMvcConfigurationSupport

2、要让它受到SpringMVC管辖

3、重写方法addRescourceHandlers

4、添加放性目录：registry.addResourceHandler()....

![image-20231016221906262](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231016221906262.png)



# SSM整合









