# SimulatedCivilization SpigotMC

简单介绍：JAVA是边写边学的，所有可能有些地方写的不是很好  
现在仅包含 `最基本` 的Civ系统，即队伍系统

选出两个可能比较难直接理解的内容讲解一下

---

## CivStart:

```
description: 分配指定数量玩家加入指定文明
usage: /<command> number Civilization TP(0/1)
命令第一个参数是指定数量，第二个参数是文明名，第三个函数是是否tp
例如：/civstart 10 Hello 1
即是选择10个无文明归属的玩家加入Hello文明，并且传送至Hello文明的指定出生点
TP参数为1时为确定传送，为其他时则不传送，不可为空

这个是方便管理快速进行分配，单个分配可以使用civjoin
```

## Civilization.yml配置文件

```
这是唯一可以进行，也是需要进行手动编辑配置文件，players文件夹内的文件请勿乱动

修改前请使用搜索引擎搜索YAML相关知识，并且进行一定实操
确保你对YAML格式有基础的了解，不要写配置写错了

配置项名看不懂可以翻译，一目了然

可以自行添加文明，一定要按照模板上的形式填写，否则可能会出错
```

## Civilization.yml配置文件 BirthPoint配置项

```
在此主要提醒 world 项为minecraft的世界名称
主世界，地狱，末地是原版minecraft包含的三个世界

xyz三项为坐标值，double类型
```

## Civilization.yml配置文件 TeamColor配置项

```
就是 § 后的内容
TeamColor为1，则为§1 [PlayerName]
```

----

## 如果有任何问题以及bug，欢迎提交issues或者pr