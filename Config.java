/**
 *    用户配置接口
 *
 *    @Author:dengchengchao
 *    @Time:2017-12-11
 *
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class Config {

    //region 热词
    private static List<String> hotWord=new ArrayList<>();
    public static List<String> getHotWord() {
        return hotWord;
    }

    public static void setHotWord(List<String> hotWord) {
        hotWord = hotWord;
    }
    //endregion

    //region 多音字
    private static boolean PolyphonySwitch=false;
    public static boolean isPolyphonySwitch() {
        return PolyphonySwitch;
    }
    public static void setPolyphonySwitch(boolean polyphonySwitch) {
        PolyphonySwitch = polyphonySwitch;
    }
   //endregion

    //region 模糊音
    private static List<String> fuzzyOption=new ArrayList<>();
    public static List<String> getFuzzyOption() {
        return fuzzyOption;
    }

    public static void setFuzzyOption(List<String> fuzzyOption) {
        fuzzyOption = fuzzyOption;
    }
   //endregion





}
