# Java高并发秒杀系统

## 本项目于2018年1月16日完成

## 项目使用技术

项目使用了spring + springmvc + mybatis + mysql + redis技术，并且用了少数第三方jar包

数据库连接池用的是c3p0

## 项目坏境

jdk1.8 + eclipse + window8.1 + tomcat8.5

## 项目地址

已放在阿里云公网上 <a href="http://120.78.159.149:8080/seckill/list" target="_blank">点我即可</a>

## 项目思路

1：首先用户先进入所有商品的列表页

2：然后点击要秒杀商品的链接

3：先获取cookie，看cookie是否有用户的手机号，如果没有弹出窗口，填写手机号

4：如果商品秒杀的开始时间还没有到达，则在界面倒数。如果秒杀已经结束，则在界面显示已经结束。

5：如果秒杀正在进行，暴露接口的地址。为了防止有用户提前模拟秒杀url地址。所以用了spring的DigestUtils.md5DigestAsHex md5进行验证。验证规则 salt + seckillId + salt 来生成md5

6：然后用户可以点击秒杀的按钮进行秒杀。发送id，md5，phone信息，后端验证id号和md5，如果报错则给用户提示，否则进行service层的事务。

## 高并发优化思路（Important）

1：静态资源 如bootstrap，jquery等框架 用服务商提供的cdn加速

2：用redis缓存。比如快到秒杀开启的时间，一般用户都会一直刷新。那么前端就会到后台数据库中一直查询秒杀开启时间，然后和当前时间的对比，无疑加大了数据库的压力。

因为这条商品记录只需要读的数据，不需要改的数据。不要去mysql中去读取，放到redis缓存中去。

具体流程如下：

	get from redis 
	if (null) {
		get from mysql
		put into redis				
	} 
			
ps. 序列化和反序列的时候用到了第三方的框架protostuff，这个序列化更快而且数据大小可以达到原来的1/5 - 1/10左右