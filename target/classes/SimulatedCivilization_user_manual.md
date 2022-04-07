# SimulatedCivilization-SpigotMC_Plugin 使用文档 Update - 2022.4.7

---

## 配置文件项

### Civilization.yml 文明配置文件项

- 请查看注释

```yaml
CivilizationList: # 文明列表，所有文明都需要写入这里
  - FiristCiv        # 务必确保文明列表和文明详情中的拼写一致
  - NewCiv
CivilizationDetails: # 文明详情
  FiristCiv: # 第一行 文明名称
    MemberMAX: 30    # 第二行 文明最大人数
    BirthPoint: # 第三行 文明统一出生点
      world: world   # 第四行 文明统一出生点 出生世界
      x: 100.0       # 第五、六、七行 文明出生点XYZ坐标
      y: 100.0
      z: 100.0
    TeamColor: 1     # 第八行 文明颜色，将用于列表显示和对话框显示
    MemberList: [ ]  # 第八行 文明成员列表，请勿自行修改！
  NewCiv: # 第二个文明，形式同上
    MemberMAX: 20
    BirthPoint:
      world: world
      x: 100.0
      y: 100.0
      z: 100.0
    TeamColor: 2
    MemberList: [ ]
```

### config.yml 基础配置项

- 请查看注释

```yaml
BanDead: true #死亡Ban掉玩家 开关
Debug: false  #Debug模式
```

### \players\\_PlayerList.yml 玩家列表 <此项不要手动编写>

### \players\\_DeadBanList.yml 因死亡被ban玩家列表 <此项不要手动编写>

---

## 指令项

在此采用驼峰命名法是为了方便区分，在mc中，全部为小写。

```yaml
CivList: # 指令名
  description: 显示所有的文明以及详情             # 指令简介
  permission: op                            # 运行指令所需权限
  permission-message: 需要op才能使用此命令      # 没有运行权限的提示
  usage: /<command>                            # 指令用法

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

