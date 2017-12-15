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
            put("0","Ling");
            put("1","Yi");
            put("2","Er");
            put("3","San");
            put("4","Si");
            put("5","Wu");
            put("6","Liu");
            put("7","Qi");
            put("8","Ba");
            put("9","Jiu");
        }
    };
    //endregion

    //region 常用拼音多音字表
    public static final Map<String,String> polyphoneTable=new LinkedHashMap<String,String>(){
        {
            put("阿","E");       //阿胶
            put("拗","Niu");     //执拗
            put("扒","Pa");      //扒手
            put("剥","Bo");      //剥皮
            put("辟","Bi");      //复辟
            put("扁","Pian");    //扁舟
            put("藏","Zang");    //西藏
            put("差","Chai");    //出差
            put("颤","Zhan");    //打颤
            put("澄","Deng");    //澄清
            put("臭","Xiu");     //乳臭未干
            put("畜","Xu");      //畜牧
            put("伺","Ci");      //伺候
            put("攒","Cuan");    //攒动
            put("大","Dai");     //大夫
            put("单","Shan");    //姓单
            put("提","Di");      //提防
            put("都","Dou");     //都行
            put("给","Ji");      //给予
            put("更","Jing");   //三更半夜
            put("和","Huo");    //和稀泥
            put("会","Kuai");   //会计
            put("奇","Ji");     //奇偶
            put("系","Ji");     //系鞋带
            put("茄","Jia");    //雪茄
            put("角","Jue");    //角色
            put("校","Jiao");   //校场
            put("狼","Ji");     //狼藉
            put("关","Qia");    //关卡
            put("壳","Qiao");   //地壳
            put("俩","Liang");  //伎俩
            put("露","Lou");    //露马脚
            put("绿","Lu");     //鸭绿江
            put("落","La");     //丢三落四
            put("埋","Man");    //埋怨
            put("秘","Bi");     //秘鲁
            put("模","Mu");     //模样
            put("娜","Nuo");    //婀娜多姿
            put("迫","Pai");    //迫击炮
            put("胖","Pan");    //心宽体胖
            put("刨","Bao");    //刨冰
            put("朴" ,"Piao");  //姓朴
            put("曝" ,"Bao");   //曝光
            put("强","Jiang");  //倔强
            put("卜","Bu");      //姓卜
        }
    };
    //endregion

    //region 所有的拼音组合
    public final static String [] phoneticizeValue=new String[]{
            "A","Ai","An","Ang","Ao","Ba","Bai","Ban","Bang","Bao","Bei","Ben",
            "Beng","Bi","Bian","Biao","Bie","Bin","Bing","Bo","Bu","Ca","Cai","Can",
            "Cang","Cao","Ce","Ceng","Cha","Chai","Chan","Chang","Chao","Che","Chen","Cheng","Cen",
            "Chi","Chong","Chou","Chu","Chuai","Chuan","Chuang","Chui","Chun","Chuo","Ci","Cong",
            "Cou","Cu","Cuan","Cui","Cun","Cuo","Da","Dai","Dan","Dang","Dao","De",
            "Deng","Di","Dian","Diao","Die","Ding","Diu","Dong","Dou","Du","Duan","Dui",
            "Dun","Duo","E","En","Er","Fa","Fan","Fang","Fei","Fen","Feng","Fo",
            "Fou","Fu","Ga","Gai","Gan","Gang","Gao","Ge","Gei","Gen","Geng","Gong",
            "Gou","Gu","Gua","Guai","Guan","Guang","Gui","Gun","Guo","Ha","Hai","Han",
            "Hang","Hao","He","Hei","Hen","Heng","Hong","Hou","Hu","Hua","Huai","Huan",
            "Huang","Hui","Hun","Huo","Ji","Jia","Jian","Jiang","Jiao","Jie","Jin","Jing",
            "Jiong","Jiu","Ju","Juan","Jue","Jun","Ka","Kai","Kan","Kang","Kao","Ke",
            "Ken","Keng","Kong","Kou","Ku","Kua","Kuai","Kuan","Kuang","Kui","Kun","Kuo",
            "La","Lai","Lan","Lang","Lao","Le","Lei","Leng","Li","Lia","Lian","Liang",
            "Liao","Lie","Lin","Ling","Liu","Long","Lou","Lu","Lv","Luan","Lue","Lun","Luo",
            "Ma","Mai","Man","Mang","Mao","Me","Mei","Men","Meng","Mi","Mian",
            "Miao","Mie","Min","Ming","Miu","Mo","Mou","Mu","Na","Nai","Nan","Nang",
            "Nao","Ne","Nei","Nen","Neng","Ni","Nian","Niang","Niao","Nie","Nin","Ning",
            "Niu","Nong","Nu","Nv","Nuan","Nue","Nuo","O","Ou","Pa","Pai","Pan",
            "Pang","Pao","Pei","Pen","Peng","Pi","Pian","Piao","Pie","Pin","Ping","Po",
            "Pu","Qi","Qia","Qian","Qiang","Qiao","Qie","Qin","Qing","Qiong","Qiu","Qu",
            "Quan","Que","Qun","Ran","Rang","Rao","Re","Ren","Reng","Ri","Rong","Rou",
            "Ru","Ruan","Rui","Run","Ruo","Sa","Sai","San","Sang","Sao","Se","Sen",
            "Seng","Sha","Shai","Shan","Shang","Shao","She","Shen","Sheng","Shi","Shou","Shu",
            "Shua","Shuai","Shuan","Shuang","Shui","Shun","Shuo","Si","Song","Sou","Su","Suan",
            "Sui","Sun","Suo","Ta","Tai","Tan","Tang","Tao","Te","Teng","Ti","Tian",
            "Tiao","Tie","Ting","Tong","Tou","Tu","Tuan","Tui","Tun","Tuo","Wa","Wai",
            "Wan","Wang","Wei","Wen","Weng","Wo","Wu","Xi","Xia","Xian","Xiang","Xiao",
            "Xie","Xin","Xing","Xiong","Xiu","Xu","Xuan","Xue","Xun","Ya","Yan","Yang",
            "Yao","Ye","Yi","Yin","Ying","Yo","Yong","You","Yu","Yuan","Yue","Yun",
            "Za", "Zai","Zan","Zang","Zao","Ze","Zei","Zen","Zeng","Zha","Zhai","Zhan",
            "Zhang","Zhao","Zhe","Zhei","Zhen","Zheng","Zhi","Zhong","Zhou","Zhu","Zhua","Zhuai","Zhuan",
            "Zhuang","Zhui","Zhun","Zhuo","Zi","Zong","Zou","Zu","Zuan","Zui","Zun","Zuo"
    };
    //endregion

}
