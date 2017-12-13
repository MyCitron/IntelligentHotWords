import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

public class main {




    public static void  main(String args[]) {
        Config.setHotWord(new ArrayList<String>(){{add("《老人与海》");}});
    String result=IntelligentHotWords.getInstance().nearToneCorrection("我在看老人与海");
    System.out.println(result);
    }
}

