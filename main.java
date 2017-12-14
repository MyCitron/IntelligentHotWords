import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

public class main {


    public  static  int [] test(int[] c){
        c[0]=1;
        return  c;
    }

    public static void main(String args[]) {
        ArrayList<String> arr1 = new ArrayList<String>();
        arr1.add("《老人与海》");
        arr1.add("其中值");
        arr1.add("的顺序");
        arr1.add("不相同");

        IntelligentHotWords ill = new IntelligentHotWords();
        long startTime = System.currentTimeMillis();
        ill.setUserHotWord(arr1);
        String result = ill.nearToneCorrection("中华人民共和国合同法");
        long endTime = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}

