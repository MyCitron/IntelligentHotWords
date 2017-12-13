/**
 *  PinYin表
 *
 *  Author:dengchengchao
 *  Time:2017-12-11
 */
import java.util.LinkedHashMap;
import java.util.Map;

public class Define {

    /** 热词最小数字限制 */
    public final static int __HOTWORD_MIN_LENGTH__=4;
    /** 热词最大数字限制 */
    public final  static  int __HOTWORD_MAX_LENGTH__=15;
    //region 数字读音
    public final static Map<String,String> digitPhoneticize=new LinkedHashMap<String,String>(){
        {
            put("0","ling");
            put("1","yi");
            put("2","er");
            put("3","san");
            put("4","si");
            put("5","wu");
            put("6","liu");
            put("7","qi");
            put("8","ba");
            put("9","jiu");
        }
    };
    //endregion

    //region 常用拼音多音字表
    public static final Map<String,String> polyphoneTable=new LinkedHashMap<String,String>(){
        {
            put("阿","e");       //阿胶
            put("拗","niu");     //执拗
            put("扒","pa");      //扒手
            put("剥","bo");      //剥皮
            put("辟","bi");      //复辟
            put("扁","pian");    //扁舟
            put("藏","zang");    //西藏
            put("差","chai");    //出差
            put("颤","zhan");    //打颤
            put("澄","deng");    //澄清
            put("臭","xiu");     //乳臭未干
            put("畜","xu");      //畜牧
            put("伺","ci");      //伺候
            put("攒","cuan");    //攒动
            put("大","dai");     //大夫
            put("单","shan");    //姓单
            put("提","di");      //提防
            put("都","dou");     //都行
            put("给","ji");      //给予
            put("更","jing");   //三更半夜
            put("和","huo");    //和稀泥
            put("会","kuai");   //会计
            put("奇","ji");     //奇偶
            put("系","ji");     //系鞋带
            put("雪","jia");    //雪茄
            put("角","jue");    //角色
            put("校","jiao");   //校场
            put("狼","ji");     //狼藉
            put("关","qia");    //关卡
            put("壳","qiao");   //地壳
            put("俩","liang");  //伎俩
            put("露","lou");    //露马脚
            put("绿","lu");     //鸭绿江
            put("落","la");     //丢三落四
            put("埋","man");    //埋怨
            put("秘","bi");     //秘鲁
            put("模","mu");     //模样
            put("娜","nuo");    //婀娜多姿
            put("迫","pai");    //迫击炮
            put("胖","pan");    //心宽体胖
            put("刨","bao");    //刨冰
            put("朴" ,"piao");  //姓朴
            put("曝" ,"bao");   //曝光
            put("强","jiang");  //倔强
            put("卜","bu");      //姓卜
        }
    };
    //endregion

    //region 所有的拼音组合
    public final static String [] phoneticizeValue=new String[]{
            "a","ai","an","ang","ao","ba","bai","ban","bang","bao","bei","ben",
            "beng","bi","bian","biao","bie","bin","bing","bo","bu","ca","cai","can",
            "cang","cao","ce","ceng","cha","chai","chan","chang","chao","che","chen","cheng","cen",
            "chi","chong","chou","chu","chuai","chuan","chuang","chui","chun","chuo","ci","cong",
            "cou","cu","cuan","cui","cun","cuo","da","dai","dan","dang","dao","de",
            "deng","di","dian","diao","die","ding","diu","dong","dou","du","duan","dui",
            "dun","duo","e","en","er","fa","fan","fang","fei","fen","feng","fo",
            "fou","fu","ga","gai","gan","gang","gao","ge","gei","gen","geng","gong",
            "gou","gu","gua","guai","guan","guang","gui","gun","guo","ha","hai","han",
            "hang","hao","he","hei","hen","heng","hong","hou","hu","hua","huai","huan",
            "huang","hui","hun","huo","ji","jia","jian","jiang","jiao","jie","jin","jing",
            "jiong","jiu","ju","juan","jue","jun","ka","kai","kan","kang","kao","ke",
            "ken","keng","kong","kou","ku","kua","kuai","kuan","kuang","kui","kun","kuo",
            "la","lai","lan","lang","lao","le","lei","leng","li","lia","lian","liang",
            "liao","lie","lin","ling","liu","long","lou","lu","lv","luan","lue","lun","luo",
            "ma","mai","man","mang","mao","me","mei","men","meng","mi","mian",
            "miao","mie","min","ming","miu","mo","mou","mu","na","nai","nan","nang",
            "nao","ne","nei","nen","neng","ni","nian","niang","niao","nie","nin","ning",
            "niu","nong","nu","nv","nuan","nue","nuo","o","ou","pa","pai","pan",
            "pang","pao","pei","pen","peng","pi","pian","piao","pie","pin","ping","po",
            "pu","qi","qia","qian","qiang","qiao","qie","qin","qing","qiong","qiu","qu",
            "quan","que","qun","ran","rang","rao","re","ren","reng","ri","rong","rou",
            "ru","ruan","rui","run","ruo","sa","sai","san","sang","sao","se","sen",
            "seng","sha","shai","shan","shang","shao","she","shen","sheng","shi","shou","shu",
            "shua","shuai","shuan","shuang","shui","shun","shuo","si","song","sou","su","suan",
            "sui","sun","suo","ta","tai","tan","tang","tao","te","teng","ti","tian",
            "tiao","tie","ting","tong","tou","tu","tuan","tui","tun","tuo","wa","wai",
            "wan","wang","wei","wen","weng","wo","wu","xi","xia","xian","xiang","xiao",
            "xie","xin","xing","xiong","xiu","xu","xuan","xue","xun","ya","yan","yang",
            "yao","ye","yi","yin","ying","yo","yong","you","yu","yuan","yue","yun",
            "za", "zai","zan","zang","zao","ze","zei","zen","zeng","zha","zhai","zhan",
            "zhang","zhao","zhe","zhen","zheng","zhi","zhong","zhou","zhu","zhua","zhuai","zhuan",
            "zhuang","zhui","zhun","zhuo","zi","zong","zou","zu","zuan","zui","zun","zuo"
    };
    //endregion

}
