redis是单线程+多路IO复用（理解是，一群人找一个黄牛（单线程）买票，然后黄牛分别为他们买（多路IO复用），其他人个做自己的事情，直到有票再回来取）

## redis前台启动

任何位置打开控制台，输入redis-server

前台启动，这个窗口会被占用，一旦关闭redis也就无法使用了

CTRL+C关闭

## redis后台启动

首先在redis.conf文件里面改daemonize后面的参数为Yes，从而支持后台启动

![image-20231005151416105](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005151416105.png)



然后通过redis-server redis.conf(这个就是输入配置文件，如果是在其他文件夹，那么输入其他文件夹下面的路径)

通过ps -ef | grep redis来查询redis是否启动成功

![image-20231005151654398](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005151654398.png)



## redis开启与关闭

这种方式启动了redis,哪怕关闭了这个窗口，也仍然是开着redis的。

再使用redis-cli来连接

关闭再服务里面，用shutdown，在服务外面就用redis-cli shutdown

由于我设置了密码，所以需要密码，但是不影响学习

也可以用指定窗口的关闭，redis-cli -p 6379 shutdown

![image-20231005152045280](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005152045280.png)



redis内部有16个库，分别为0，1，2...15

用select 1（0-15）来切换



密码登录，分别有在内和在外

![image-20231009115516831](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009115516831.png)

![image-20231009115535192](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009115535192.png)

# Redis的数据类型

redis支持多种数据类型，常用的五大数据类型为String，List，Set，Hash，Zset

Redis是基于key-value的形式开展的。

### 常用的键命令：

keys *		--查看所有key

set key1 jack	--设置key为key1，value为jack的K—V对

set key2 mary	--同上

set age 17			--同上

exists key1 		--查看是否存在key为key1 的KV对，存在返回1

type key1			--查看key1这个key的数据类型

del key1			--删除key1，直接删，最终效果与下面一样

unlink key1	--删除key1，但是仅删除key1从keyspace中删除，真正的删除后续异步进行，根据value选择非阻塞删除

expire key1 10 --设置key1的生存时间为10s

ttl key1 			--查看key1这个键值对的剩余生存时间，-1表示永不过期，-2表示已经过期，其他的则表示剩余多少秒

dbsize  		--查看当前的数据库有多少KV对

flushdb 		--清空当前库

flushall		--通杀所有库

## String

### String数据类型

常用的命令

set key value		--设置k-v对，如果后面的key重复了，会覆盖前面的

get key				  --得到对应key的value

append key value 	--在key对应的value后面加上这里value，然后返回值是长度

strlen key			--得到key的长度

setnx key value 	--与set不同于，只能在key不存在的时候使用，如果key存在不会进行覆盖的

incr key			--如果存的String是数字，可以加一

decr key			--如果存的String是数字，可以减一

incrby key 10	--加10，数字可以自己选择

decrby key 10	--减10

这两个操作是原子操作，即不会被线程调度机制打断的操作

mset key1 value1 key2 value2.....		--可以一次性存多个

mget key1 key2...

msetnx key1 value1 key2 value2.....	--仅当key都不存在才可以

get range key 头  尾 ：get range name 0 3(共前四个字母)

setex key  time value：setex name 20 zhangan 	--设置key为name,value为zhangsan的键值对的存活时间为20s

getset key value 设置新的值，获得旧的值



### String底层结构

动态字符串，类似Java的StringBuilder，自动扩容，初是分配一般回长于实际字符串长度，小于1M到时候都是翻倍，大于1M的时候，最多每次加1M，最大为512M



## List列表

底层为双向链表，单件多值

### 常见操作

lpush/rpush

lpush key1 value1 value2 value3...		--在左边插入1个或多个值

lrange key 头 尾 ：lrange key1 0 2		--得到头到尾的下标的值

​	如果是0 -1 会取出所有值

lpop/rpop 	key			--从左/右边吐出一个值。当某个键对应的值都没了，那么键也就没有了

rpoplpush key1 key2			--从key1的右边拿吃出一个值插到key2的左边

index key (index):index key1 0			--取出key1这个键的第1个value（下标为0）

llen key1	--获取该键的列表的长度

linsert key before/after value newvalue		--返回值为该key的新的长度，然后是在某个value后面/前面加个newvalue

lrem key  length value1		--从左边删除length个值为value1（从左边往右边）的内容，**等后期用一个例子来说明**

lset key 1 newvalue	--将key的下标为1的value换位newvalue

### 数据结构

数据结构是快速链表quickList

元素少的时候，是一个压缩列表（ziplist），多的时候，用多个压缩列表生成一个链表



## Set

sadd k1 v1 v2 v3...		--将一个或者多个member元素加入集合k1中，已经存在member元素会被忽略

smembers k1 		--取出该集合中的所有值

smembers k1 v1 		--判断是否k1中存在v1，如果有，返回值1，没有返回值0

scard key		--返回该集合的元素个数

srem k1 v1 v2		--删除k1中的v1,v2

spop key			——从key中随机吐出一个值

standmember key n 		--随机从该集合中取出n个值，不会从集合中删除

smove source destination value	--把集合中的一个值从一个集合移动到另一个集合

sinter key1 key2 		--返回两个集合的交集元素

sunion key1 key2 		--返回两个集合的并集元素

sdiff key1 key2 		--返回两个集合的差集元素（k1中有的，不包含k2中的）

### hash

### Zset

有序，引入了评分

zadd key top1 score value top2 score2 value2 ...		--可以插入多个，包括key,score,value。将一个或者多个member元素以及其socre值加入到有序集key当中



zrange key 0 -1 			--取出全部

zrange key 0 -1 withscores			--取出全部,并且返回评分

zrangebyscore  key score1 score2 (withscores)	--把分数在score1和score2 里面的member从小到大排序出来，带有分数的那种（排序的方式是根据score1和score2的大小来决定得到，如果前面大，就是从大到小）



zincrby key 增加的分数 value1 	--给该key下 的value1对应的value增加对应的分数

zcount key min max		--统计区间内有多少member

zrank key value	--返回该值在集合的排名，从0开始



数据结构

底层为跳跃表和哈希（field是key,value 是score）、





## Redis配置文件

![image-20231009113412865](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009113412865.png)

当这里的bind 127。。。。存在的时候，说明我们的redis只支持本地连接而不支持远程连接，我们注释掉就可以远程连接了

下面这里的是开启保护模式，我们只可以本地访问，不可以远程访问，改成no就可以远程访问了

![image-20231009113539954](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009113539954.png)



timeout 	后面是0的时候，意味着永远不会超时。如果改成1，那么就是1s不动redis,redis就会自己释放了。超时之后需要重新连接

![image-20231009113726209](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009113726209.png)



这个检查心跳时间，默认300s，如果连接上了很久没有操作，就会结束连接

![image-20231009113924174](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009113924174.png)

改为yes,实现后台启动

![image-20231009114048996](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009114048996.png)

日志等级和日志的输出路径，默认为空，我们可以自己改

![image-20231009114220389](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009114220389.png)



这里配置自己的密码

![image-20231009114444112](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009114444112.png)



## 订阅与接收

订阅用的是SUBSCRIBE channel,发布用的是publish channel +内容

也可以订阅多个频道，是一种消息的通信模式

![image-20231009115709803](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009115709803.png)



## Redis新的数据类型

### Bitmaps

setbit 		getbit

### HyperLogLog





## Jedis

pom文件中引入如下依赖

```
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>4.3.1</version>
        </dependency>
```

同时，想要链接要将redis的配置改一改，第一个是注释掉bind,不然只能本地连接；第二是protected-mode no，改成no

防火墙是否关闭？需要关闭

如何关闭？

**systemctl stop firewalld**		这个是临时关的（需要配置文件改和防火墙关闭）

![image-20231009163906513](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009163906513.png)

![image-20231009164058271](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009164058271.png)

![image-20231009164809452](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009164809452.png)



Jedis的方法与redis里面的命令是一一对应的，所以我们可以通过对应方法迁移过来



### Jedis实现手机验证码的发送

两分钟有效，6为随机数字

验证成功

每个手机号每天只能输入三次

思路：验证码放入redis中，设置过期时间120s,如experi;数字则通过Random类；每个手机三次，可以通过incr，每次发送+1，如果大于2，就不能再发送了



存在问题：

![image-20231009194748794](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231009194748794.png)

代码：

```java
import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {
        String get=verityCode("15757");
        System.out.println(get);
        //System.out.println(yanZhen("385090","15757"));
    }
    public static String getCode(){
        //使用Random获取六位数字
        String result="";
        Random random=new Random();
        for(int i=0;i<6;i++){
            int i1 = random.nextInt(10);//得到一个数字，范围是0-9的int
            result += i1;
        }
        return result;
    }

    //第二个方法，是设定一天不超过三次
    public static String verityCode(String phoneNumber){
        Jedis jedis=new Jedis("192.168.146.128",6379);
        jedis.auth("password");
        String phoneCode=jedis.get(phoneNumber);
        if(phoneCode==null){
            //这个情况说明一次都还没有用过，那么我们要创建，然后设置时间为1天
            jedis.setex(phoneNumber,24*60*60,"1");
        }else if(Integer.parseInt(jedis.get(phoneNumber))<=3){//为啥3是能用四次？
            jedis.incr(phoneNumber);
        }else{
            System.out.println("今天已经三次了，不可以再发送");
            jedis.close();
            return null;
        }
        //只有这里没问题了，才要搞获取code
        String numberCode = phoneNumber+"code:";
        String code = getCode();
        jedis.setex(numberCode,120,code);
        return code;
    }

    public static boolean yanZhen(String input,String phoneNumber){
        String numberCode = phoneNumber+"code:";
        Jedis jedis=new Jedis("192.168.146.128",6379);
        jedis.auth("password");
        if(jedis.get(numberCode).equals(input)){
            return true;
        }else {
            return false;
        }
    }
}

```

