# IntelligentHotWords

IntelligenHotWord 是基于纠正语音识别和输入法错误的热词替换算法。

使用IntelligenHotWord，你可以：
- 纠正热词同音字：   
   - example:   
   #热词：加特林哒哒哒   
   #输入：我要冒蓝火的加特林大大大   
   #输出：我要冒蓝火的加特林哒哒哒   
   
- 强制替换不同音标但是同音的字   
   - example:   
   #热词：我的家在东北，松花江上啊   
   #输入：窝德假在冻被，送滑浆尚啊   
   #输出：我的家在东北，松花江上啊   
   
- 自动增加标点修饰   
   - example:   
   #热词：《老人与海》   
   #输入：我在看老人与海   
   #输出：我在看《老人与海》   
   
- 设置地方口音模糊音：z-zh,c-ch,n-l等   
   - example：   
   #模糊音设置：f-h      
   #热词：蝴蝶     
   #输入：看，美丽的福蝶      
   #输出：看，美丽的蝴蝶   

## Why IntelligentHotWords?   

- IntelligentHotWords  主要存储使用HashMap，10条热词处理<100ms
- IntelligentHotWords  热词处理速度只与输入的String长度有关，也就是内部热词只受存储本机上的服务器物理内存限制
- IntelligentHotWords  使用单例类在启动的时候编码所有热词，并一直存储在内存中，索引速度基本为O(1)
- IntelligentHotWords  编码为单独的模块，可以重写编码规则（比如同音调才替换）使用不同的场景
- IntelligentHotWords  采用精准编码，可以忽略标点符号对热词匹配的影响


## TODO
- 增加NLP算法智能识别热词是否该替换   
- 多音字处理只是一个简单的内置库，对于多音字处理不大好
- 预增加处理数字读音（10023-> 一万零二十三）


## Getting Started
- 依赖pinyin4j库
clone源代码并编译为一个jar包
```
    import IntelligentHotWords.IntelligentHotWords;
    String result=IntelligentHotWords.getInstance().nearToneCorrection("我在看老人与海");
    System.out.println(result);//result:我在看《老人与海》
```

## Setting

 配置在Config.java中
 ```
 setPolyphonySwitch(bool b);         //是否开启多音字
 setFuzzyOption(List<String> fuzzy); //模糊音匹配表 
 setHotWord(List<String> hotWord);   //热词列表
 ```
