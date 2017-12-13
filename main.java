import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

public class main {




    public static void  main(String args[]) {
    String result=IntelligentHotWords.getInstance().nearToneCorrection("");
    System.out.println(result);
    }
}

