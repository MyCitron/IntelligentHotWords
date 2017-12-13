/**
 *    传入汉字的拼音，根据Approximate定义的编码规则以及加载的用户编码设置进行编码
 *    返回编码后字符串
 *
 *    @Model:单例类（getInstance()）
 *    @Author:dengchengchao
 *    @Time:2017-12-11
 *
 */
import javax.tools.Tool;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
class ApproximateTable {

    /**
     *   获取单例类的实例
     */
    public  static ApproximateTable getInstance(){
        return _instance;
    }

    //region 拼音匹配表
    //固定编码顺序，同类型的拼音按length排序
    private static final String[] initials = { "b", "p", "m", "f", "d", "t", "n", "l", "g", "k", "h", "j", "q", "x", "r", "z", "c", "s", "zh", "ch", "sh", "y", "w" };
    private static final String[] syllable = { "a", "o", "e", "i", "u", "v", "ai", "ei", "ui", "ao", "ou", "iu", "ie", "ue", "er", "an", "en", "in", "un", "ang", "eng", "ing", "ong", "ia", "iao", "ian", "iang", "iong", "uo", "ua", "uai", "uan", "uang" };
    private static final String[] otherPinYin = { "a", "ai", "an", "ang", "o", "ao", "e", "en", "er", "eng", "ou" };
    //声母表
    private static final Map<String, Integer> initialsTable = new LinkedHashMap<>();
    //韵母表
    private static final Map<String, Integer> syllableTable = new LinkedHashMap<>();
    //En,Ai等
    private static final Map<String, Integer> otherTable = new LinkedHashMap<>();
    //正确编码与近似音编码表对应表
    private static Map<String,String> approPhoneticTable=new LinkedHashMap<>();
    //模糊音矫正编码
    private static final Map<String, String> positiveSimilarTable = new LinkedHashMap<String,String>()
    {
        {
           put("z", "zh");
           put("c","ch");
           put("s","sh");
           put("l","n");
           put("f","h");
           put("an","ang");
           put("en","eng");
           put("in","ing");
           put("zh","z");
           put("ch","c");
           put("sh","s");
           put("n","l");
           put("h","f");
           put("ang","an");
           put("eng","en");
           put("ing","in");

        }
    };

    //region 初始实例化
    private static  ApproximateTable _instance=new ApproximateTable();
    private  ApproximateTable() {
        initAllTable();
        modifyAllTable(Config.getFuzzyOption());
        recodePhoneticizeToApproTable();
    }
    //endregion


//endregion

    //region 初始化

   private  void initAllTable() {
       initInitialsTable();
       initSyllableTable();
       initOtherPinYin();
   }

   private  void initInitialsTable() {
       //initial 由50开始编码
       initPinYinHashTable(initials,initialsTable,50);

   }

   private void initSyllableTable() {
       // syllable 由100 开始编码
       initPinYinHashTable(syllable,syllableTable,100);
   }



    private void initOtherPinYin() {
        //other从30开始编码
        initPinYinHashTable(otherPinYin,otherTable,30);
    }

    private  void initPinYinHashTable(String[] mapIndex,Map<String,Integer> initTable,int codeBegin){
        int code = codeBegin;
        for (String item : mapIndex)
        {
            initTable.put(item, code);
            code++;
        }
    }

    private void recodePhoneticizeToApproTable(){
         approPhoneticTable.clear();
         for(String item : Define.phoneticizeValue){
             String code=getPhoneticCode(item);
             approPhoneticTable.put(item,code);
         }

    }
    //endregion

    //region 修改模糊音



    private void modifyInitialsTable(String letter){
        modifyPhoneticizeTable(initialsTable,letter);
    }

    private  void modifySyllableTable(String letter){
        modifyPhoneticizeTable(syllableTable,letter);
    }

    private  void  modifyOtherTable(String letter){
        modifyPhoneticizeTable(otherTable,letter);
    }


    private  void modifyPhoneticizeTable(Map<String,Integer> modifyTable,String letter){
        if(!positiveSimilarTable.containsKey(letter)) return;
        if(!modifyTable.containsKey(letter))return;
        int letterKey=modifyTable.get(letter);
        String similarKey=positiveSimilarTable.get(letter);
        modifyTable.put(similarKey,letterKey);

    }

//endregion

    /**
     *  重新编码各个拼音表，在用户修改了模糊音配置（z->zh ,c->ch等）后调用
     *  @param modifyLetterList:修改后的对应表
     */
    public void modifyAllTable(List<String> modifyLetterList){
        for(String item:modifyLetterList){
            modifyInitialsTable(item);
            modifySyllableTable(item);
            modifyOtherTable(item);
        }
        recodePhoneticizeToApproTable();
    }

    //region 编码




    private  String codeOtherPinYin(String pinYin){
        for(int i=otherPinYin.length-1;i>=0;i--){
            if (pinYin.contains(otherPinYin[i])) {
                return otherTable.get(otherPinYin[i])+"000";//保证每个拼音编码都是5位数，方便后续索引
            }
        }
        return "";
    }

    //endregion
    /**
     *  获取pinyin的编码
     *  @param pinYin:需要获取的拼音
     */
    public String getPhoneticCode(String pinYin){
        String realCode=codeOtherPinYin(pinYin);
        if(!Tools.isNullorEmpty(realCode))return realCode;
        for (int i=initials.length-1;i>=0;i--){
            if(pinYin.contains(initials[i])) {
                String phoneticize = initials[i];
                realCode += initialsTable.get(phoneticize);
                pinYin = Tools.stringRemove(pinYin, pinYin.indexOf(phoneticize), phoneticize.length());
                break;
            }
        }

        for(int i=syllable.length-1;i>=0;i--){
            if(pinYin.contains(syllable[i])){
                String phoneticize=syllable[i];
                realCode+=syllableTable.get(phoneticize);
                pinYin=Tools.stringRemove(pinYin,pinYin.indexOf(phoneticize),phoneticize.length());
                break;
            }
        }
        return realCode;
    }

    /**
     *  最终编码的Hash映射表
     */
    public  Map<String,String> getApproPhoneticTable()
    {
        return approPhoneticTable;
    }

}


