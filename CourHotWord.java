import java.util.ArrayList;
import java.util.List;

public class CourHotWord {
    /**
     *  存在自带的热词，也可以作为公共热词库使用
     *  每个map存放5000条热词
     *  Author:dengchengchao
     *  Time:2017-12-11
     */
    private static final List<String> theFirstCourTable;
 static {
     theFirstCourTable=new ArrayList<String>(){
         {
             add("内部热词，需要预加载的热词");
             add("《老人与海》");
         }
     };
    }

    private  static List<String> allHotWord;
    static {
        allHotWord=new ArrayList<>();
        allHotWord.addAll(theFirstCourTable);

    }


    /**
     *  获取所有的热词列表
     */

    public  static List<String> getHotWordList(){
        return allHotWord;
    }

}
