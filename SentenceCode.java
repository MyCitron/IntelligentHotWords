/**
 *    根据用户设置，将汉字转化为拼音然后通过ApproximateTable将拼音进行编码
 *
 *    将用户存入的热词和法律库的热词保存在HashMap中
 *
 *    @Author:dengchengchao
 *    @Time:2017-12-11
 *
 */
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class SentenceCode {

    /* 自定义关于法律的热词库**/
    private static final Map<String,String> courWordTable=new LinkedHashMap<>();
    /* 用户传入的用户自定义的热词表 **/
    private   Map<String,String> userWordTable=new LinkedHashMap<>();

    /* 用户自定义的热词*/
    private   ArrayList<String> userWordList=new ArrayList<>();

    private  boolean isPolyphone=false;
    private  ApproximateTable approximateTable=new ApproximateTable();
    //定义输出类型，以后可以扩展为音标相同的格式
    private static final HanyuPinyinOutputFormat hanyuPinyinOutputFormat;
    static {
        hanyuPinyinOutputFormat =new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }


    public SentenceCode(){
          loadCourHotWord();
          loadLocalHotWord();
    }
    //endregion

    //region 汉字->拼音
    private String letterConvertToPhoneticize(char letter){
        String letterStr=String.valueOf(letter);
        String resultPhoneticize="";
        if (Tools.isChineseChar(letter)){
            if (Define.digitPhoneticize.containsKey(letterStr)){
                return Define.digitPhoneticize.get(letterStr);
            }
            resultPhoneticize=getLetterPinYin(letter);
        }
        return  resultPhoneticize;
    }

    private String getLetterPinYin(char letter){
        String[] rightLetter=null;
        try{
            rightLetter= PinyinHelper.toHanyuPinyinStringArray(letter,hanyuPinyinOutputFormat);
        }catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination){
            //TODO：增加日志处理
        }
        if (rightLetter==null||rightLetter.length<1)return "";
        return  Tools.upperFirstLetter(rightLetter[0]);
    }


    private  String  getLetterPolyphony(char letter){
       String result= Define.polyphoneTable.get(letter);
        return result==null?"":result;
    }

    //endregion

    //region 拼音->编码
    private String phoneticizeConverToHashCode(String phonetic)
    {
        return approximateTable.getApproPhoneticTable().get(phonetic);
    }
    //endregion

    //region 句子->编码


    private String sentenceConvertToCode(String sentence,boolean isPolyphone){
        String resultHashCode="";
          for (char item:sentence.toCharArray()){
              String letterPhoneticize="";
              if (isPolyphone){
                  letterPhoneticize=getLetterPolyphony(item);
              }
              //如果这个字不是多音字，则获取它的正常读音
              if(!isPolyphone|| Tools.isNullorEmpty(letterPhoneticize)){
                  letterPhoneticize=getLetterPinYin(item);
              }
              //将拼音进行编码
              if(!Tools.isNullorEmpty(letterPhoneticize)){
                  resultHashCode+=phoneticizeConverToHashCode(letterPhoneticize);
              }

          }
          return  resultHashCode;
    }
    //endregion

    /**
     *  不使用多音字表中的编码
     */
    public String sentenceConvertToNormalCode(String sentence){ return  sentenceConvertToCode(sentence,false); }

    /**
     *  使用多音字表中的编码
     */
    public String sentenceConverToPolyphony(String sentence){ return sentenceConvertToCode(sentence,true); }

    //region 热词加载缓存
    //自带的法律热词
    private void loadCourHotWord(){
       List<String> courWordList=CourHotWord.getHotWordList();
       loadSentenceToTable(courWordList,courWordTable,isPolyphone);
    }

    /**
     *  重新加载热词库的时候，只刷新用户的热词列表。
     */
    public void loadLocalHotWord()
    {
        userWordTable.clear();
        loadSentenceToTable(userWordList,userWordTable,isPolyphone);
    }

    //将整个热词加载到map中
   private void loadSentenceToTable(List<String> sentenceList,Map<String,String> targeMap,boolean isPolyphony){
        for(String item :sentenceList){
            loadNormalCodeToMap(item,targeMap);
            if (isPolyphony){
                loadPolyphonyCodeToMap(item ,targeMap);
            }
        }
   }

   private void loadPolyphonyCodeToMap(String sentence,Map<String,String> targeMap){
       String wordCode=sentenceConverToPolyphony(sentence);
       targeMap.put(wordCode,sentence);
   }

   private void loadNormalCodeToMap(String sentence,Map<String,String> targeMap){
      String wordCode=sentenceConvertToNormalCode(sentence);
       targeMap.put(wordCode,sentence);
   }

   /**
    *  重新加载所有热词
    *      应用于设置被修改后
    */
   private  void reCodeAllHotWord()
   {
      userWordTable.clear();
      courWordTable.clear();
      loadCourHotWord();
      loadLocalHotWord();
   }
   //endregion

    /**
     *  重新加载用户设置并重新编码所有热词
     */
    public void reloadFuzzySetting(List<String> fuzzyList){
        approximateTable.modifyAllTable(fuzzyList);
       reCodeAllHotWord();
    }

    public Map<String,String> getCourWordTable(){ return courWordTable; }

    public  Map<String,String> getUserWordTable(){ return userWordTable; }

    public ArrayList<String> getUserWordList() {
        return userWordList;
    }

    public void setUserWordList(ArrayList<String> wordList){
        userWordList.clear();
        this.userWordList=wordList;
    }

    public void setPolyphone(boolean isPolyphone){
        this.isPolyphone=isPolyphone;
    }
}
