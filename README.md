#### 一、简介

本项目对Java语言代码进行重构，实现代码格式重构，特定类型命名重构，等价语句互转以及程序正确性性检测及自动修复，正确率均达到99%以上。

##### 开发环境：

算法部分：VSCode  JDK1.8

展示部分（前后端）：IntelliJ IDEA，Django

#### 二、目录介绍

![1559541834496](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559541834496.png)

![1559542086663](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559542086663.png)

###### 命令行交互类：

src/main/java/org/hacksource/cli包内

###### 核心算法类：

src/main/java/org/hacksource/core包内

###### 测试类：

src/main/resources包中的 example.java、example2.java、example3.java

#### 三、快速开始

进入http://www.kaqiz.cn，如图所示，左面输入代码，点击Run后，右边则给出重构后的代码，前端并提供了几个示例代码（下拉列表框实例代码处）。

![1559824984009](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559824984009.png)

#### 四、测试

**测试代码与运行结果截图**

**测试1（代码格式重构）：**

![1559825333032](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559825333032.png)



![1559825360854](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559825360854.png)

如上图所示，左面为输入的测试代码，右边为运行后重构的代码，运行后会将代码不规范的地方高亮显示，并定位到具体格式有问题的行与列，在最上方给出具体的规范说明（部分空行，空格只进行更正不进行高亮提示以及规范说明）。重构后，正确率在99%以上，包括不得省略花括号、换行、空白、表达式圆括号等。

**测试2（特定类型命名重构）：**

![1559825796754](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559825796754.png)

![1559825825060](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559825825060.png)

![1559826028653](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559826028653.png)

如上图所示，对于命名不规范（不遵循lowerCamelCase）的变量，运行后，定位到命名不规范变量的位置并高亮显示，在上方给出具体的分析说明，右边则为重构后的代码。重构后，命名正确率在99%以上，包括：包命名，类命名，方法命名，常量命名，成员变量命名，参数命名，局部变量命名等。

**测试3（等价语句互转）：**

![1559826912432](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559826912432.png)

![1559826945001](http://git.imtwice.cn:1024/HackingToTheSource/JavaIntelligence/src/dev/doc/imgs/1559826945001.png)

如上图所示，根据给定的代码规范，对于多条件单if语句/单条件多if互转语句，for/while互转，多if/switch互转等，实现不满足结构规范的代码，对其结构自动重构，会将代码不规范的地方高亮显示，并定位到结构规范有问题的行与列，在最上方给出具体的规范说明，重构后，结构正确率在99%以上。

#### 五、开发者

刘明阳、陈嘉宁、宋国伟

#### 六、总结讨论

本项目对Java语言代码进行重构，对于不规范代码的位置进行定位并高亮显示，给出详细的分析说明，并进行代码重构，达到很高的正确率。采用前端平台进行展示，方便快捷。实现了代码格式重构（重构后正确率需在99%以上）、特定类型命名重构（重构后命名正确率需在99%以上）、等价语句互转（重构后结构正确率需在99%以上）以及程序正确性性检测及自动修复等。







