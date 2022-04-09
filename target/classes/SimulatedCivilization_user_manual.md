# SCiv - SpigotMC_Plugin 使用文档 Update - 2022.4.9

---

## SCiv 简介

SCiv 是由`Simulated Civilization` 简化而来的名称，直译则为`模拟文明` 。来自一种MC服务器玩法，我也正式游玩了一次这种玩法而产生编写一款这样的插件，以能满足一些想要开此类服务器的需求。

但是，由于此类玩法开创性较大，会有很多内容需要自行整合、创造。理论上除了此插件可以增加一些数据包、模组以及其他插件来增强游戏性。故此SCiv结合了PlaceholderAPI（软依赖，可以独立运行），来提供一些接口，详见[`SCiv - PlaceholderAPI`](##SCiv - PAPI变量)，接口会逐渐增多的。

一般来说，社会模拟服务器回承载较多的玩家，此插件没有经过很大负荷量的运行，不过我通过一些同一步骤重复循环来模拟这种压力，并且进行了一定地优化。但是实践是检验真理的唯一标准，所以此插件的一些承载量并不是很清晰。如果有人使用了，可以反馈一些详情给我。

如果你有什么好的建议也可以联系我，也许我会加入TODO。

---

## 配置文件项

### Civilization.yml 文明配置文件项

- 请查看注释

```yaml
CivilizationList:      # 文明列表，所有文明都需要写入这里
  - FiristCiv          # 务必确保文明列表和文明详情中的拼写一致
  - NewCiv
CivilizationDetails:   # 文明详情
  FiristCiv:           # 第一行 文明名称
    MemberMAX: 30      # 第二行 文明最大人数
    BirthPoint:        # 第三行 文明统一出生点
      world: world     # 第四行 文明统一出生点 出生世界
      x: 100.0         # 第五、六、七行 文明出生点XYZ坐标
      y: 100.0
      z: 100.0
    SphereOfInfluence: # 第八行 文明势力范围
      shape: circular  # 第九行 文明范围形状，共有circular、ball、square、cube，分别为圆、球、正方形、正方体
      radius: 50       # 第十行 范围半径 圆和球为半径,正方形和立方体为1/2个边长
    TeamColor: 1       # 第十一行 文明颜色，将用于列表显示和对话框显示
    MemberList: [ ]    # 第十二行 文明成员列表，请勿自行修改！
  NewCiv:
    MemberMAX: 20
    BirthPoint:
      world: world
      x: 200.0
      y: 100.0
      z: 200.0
    SphereOfInfluence:
      shape: ball
      radius: 100
    TeamColor: 2
    MemberList: [ ]

```

### config.yml 基础配置项

- 请查看注释

```yaml
BanDead: true            #死亡Ban掉玩家 开关
Debug: false             #Debug模式 开关
SphereOfInfluence: false # 文明势力范围开关
```

### \players\\_PlayerList.yml 玩家列表 <此项不要手动编写>

### \players\\_DeadBanList.yml 因死亡被ban玩家列表 <此项不要手动编写>

---

## 指令项

在此采用驼峰命名法是为了方便区分，在mc中，全部为小写。

```yaml
CivList:                                  # 指令名
  description: 显示所有的文明以及详情         # 指令简介
  permission: op                          # 运行指令所需权限
  permission-message: 需要op才能使用此命令   # 没有运行权限的提示
  usage: /<command>                       # 指令用法

CivJoin:
  description: 修改一个玩家的所属文明
  permission: op
  permission-message: 需要op才能使用此命令
  usage: /<command> <Name> <Civilization>

CivQuit:
  description: 使一个玩家退出所在文明
  permission: op
  permission-message: 需要op才能使用此命令
  usage: /<command> <Name>

CivStart:
  description: 分配指定数量玩家加入指定文明
  permission: op
  permission-message: 需要op才能使用此命令
  usage: /<command> <Number> <Civilization> <TP(0/1)> # TP项不能为空

CivDebug:
  description: SimulatedCivilization debug
  permission: op
  permission-message: 需要op才能使用此命令
  usage: /<command>

CivDeadUnban:
  description: 解除被BanDead功能ban掉的指定的玩家
  permission: op
  permission-message: 需要op才能使用此命令
  usage: /<command> <Name>

CivDeadUnbanAll:
  description: 解除所有被BanDead功能ban掉的玩家
  permission: op
  permission-message: 需要op才能使用此命令
  usage: /<command>
```

---

## SCiv - PAPI变量

### %SCiv_Civ%

获取此玩家所在的文明，无归属的文明则返回`null`

### %SCiv_CivColor%

获取此玩家所属文明的显示颜色，无阵营则返回`null`

颜色类型为`String`，在此举个例子应该能说得更清楚

CivColor的值即为接在转义字符`§`后的，如果我填写的是`§1`,那么CivColor则为`1`

### %SCiv_InCiv%

为了防止过快地监听对服务器造成太大的压力，此值的更新速度为20tick。

获取此玩家现在所在区域，如果不在任何文明的的地区，则返回`0`，值类型为String
