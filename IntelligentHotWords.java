/**
 * 热词模糊音匹配替换算法接口
 * <p>
 * 主要作用：通过加载用户设置的热词和用户模糊音的设置，将所有汉字进行模糊编码
 * 通过对传入的汉语进行编码和内存中编码好的HashMap进行匹配，如果匹配成功，则进行替换
 *
 * @example: 热词：人生苦短     传入：人参库段，我用python   返回：人生苦短，我用python
 * @Author:dengchengchao
 * @Time:2017-12-12
 */


import java.util.*;

public class IntelligentHotWords {


    /** 保存所有的热词 */
    private  Map<String, String> totalHotWordMap = new LinkedHashMap<>();
    private  SentenceCode codeObj =new SentenceCode();

//    private static IntelligentHotWords instance = new IntelligentHotWords();
//
//    public static IntelligentHotWords getInstance() {
//        return instance;
//    }

    public IntelligentHotWords() {

        recodeHotWord(ApproximateTable.defaultFuzzyList);
        mergeUserWordAndCourWord();
    }


    /**
     *  处理热词中同时包含：《国际私法》/国际私法的基本原则  冲突的问题
     *  原则：以含有书名号的为准
     *  解决方式： 将含有书名号的热词放在不含书名号的热词后面。
     */
    private void increaseBookTitleWeight(Map<String, String> sentenceMap) {
        Map<String, String> afterMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : sentenceMap.entrySet()) {
            if (entry.getValue().contains("《")) {
                afterMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : afterMap.entrySet()) {
            sentenceMap.remove(entry.getKey());
            sentenceMap.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     *   合并用户热词和自带的库热词
     *   用户热词放在靠后，提高权重
     */
    private  void mergeUserWordAndCourWord() {
        totalHotWordMap.putAll(codeObj.getCourWordTable());
        totalHotWordMap.putAll(codeObj.getUserWordTable());
    }

    /**
     *  根据整句的code和热词code
     *  找到匹配的热词并替换热词
     *  @return 替换完成的原句
     */
    private String marryWordAndReplace(String sentence, String sentenceCode, Map.Entry<String, String> wordAndCode) {
        assert (sentence != null);
        int virtualIndex = sentenceCode.indexOf(wordAndCode.getKey()) / 5;
        int realIndex = caculateChineseRealIndexWithPun(sentence, virtualIndex);
        String withHotWordSentence = sentence.substring(realIndex);
        int realSubLength = calculateChineseRealIndex(withHotWordSentence, wordAndCode.getKey().length() / 5);
        String replaceWord = sentence.substring(realIndex, realIndex + realSubLength);
        String resultSentence = sentence.replaceFirst(replaceWord, wordAndCode.getValue());
        return dealbeginPunctuHotWord(resultSentence, wordAndCode.getValue());
    }

    /**
     *  替换处理原文中带有标点的热词
     *  example: 原句可能为： 我在看《巴黎圣母院》   或者  我在看巴黎圣母院
     *           热词为：《巴黎圣母院》
     *           返回结果：我在看《巴黎圣母院》      \    我在看《巴黎圣母院》
     */
    private String dealbeginPunctuHotWord(String sentence, String hotword) {
        String charBefore = getBeforHotWordChar(sentence, hotword);
        String punctBefore = getHotWordBeginPunctuation(hotword);
        String punctEnd = getHotWordEndPunctuation(hotword);
        String surplusSentence = sentence;//处理所有热词
        if (!Tools.isNullorEmpty(punctBefore)) {
            if (getSentenceBeginIndexPunctuation(sentence, hotword, punctBefore).equals(punctBefore)) {
                surplusSentence = charBefore + hotword.substring(punctBefore.length()) + sentence.substring(sentence.indexOf(hotword) + hotword.length());
            }
        }
        if (!Tools.isNullorEmpty(punctEnd)) {
            if (getHotWordEndIndexPunctuation(surplusSentence, hotword, punctEnd) == punctEnd) {
                int index = surplusSentence.indexOf(hotword) + hotword.length() - charBefore.length();
                String beginSentence = surplusSentence.substring(0, index);
                String surpStr = surplusSentence.substring(index + punctEnd.length());
                surplusSentence = beginSentence + surpStr;
            }
        }
        return surplusSentence;
    }


    public String nearToneCorrection(String sentence) {
        String surplusSentence = sentence;
        String sentenceCode = codeObj.sentenceConvertToNormalCode(sentence);
        Map<String, String> sentenceItem = getMatchWordMap(totalHotWordMap, sentenceCode);
        increaseBookTitleWeight(sentenceItem);
        for (Map.Entry<String, String> entry : sentenceItem.entrySet()) {
            String handledSentence = "";
            String notHandleSentence = surplusSentence;
            int matchWordCount = getHotWordCoutInSentence(entry.getKey(), sentenceCode);
            int handledCount = 0;
            while (handledCount != matchWordCount) {
                handledCount++;
                if (1 == handledCount) {
                    surplusSentence = marryWordAndReplace(surplusSentence, sentenceCode, entry);

                } else if (!Tools.isNullorEmpty(entry.getKey())) {
                    handledSentence = removeHandledWord(surplusSentence, entry, handledCount);
                    notHandleSentence = getBeleftSentence(surplusSentence, entry, handledCount);
                    String notHandleSentenceCode = codeObj.sentenceConvertToNormalCode(notHandleSentence);
                    notHandleSentence = marryWordAndReplace(notHandleSentence, notHandleSentenceCode, entry);
                    surplusSentence = handledSentence + notHandleSentence;
                }

            }
        }
        return surplusSentence;
    }

    private String removeHandledWord(String sentence, Map.Entry<String, String> hotWordEntry, int indexOfSentence) {
        return sentence.substring(0, sentence.indexOf(hotWordEntry.getValue(), indexOfSentence - 1) + hotWordEntry.getValue().length());
    }

    private String getBeleftSentence(String sentence, Map.Entry<String, String> hotWordEntry, int indexOfSentence) {
        int beginIndex = sentence.indexOf(hotWordEntry.getValue(), indexOfSentence - 1);
        return sentence.substring(beginIndex + beginIndex + hotWordEntry.getValue().length());
    }

    /**
     *   找到所有匹配的热词
     */
    private Map<String, String> getMatchWordMap(Map<String, String> hotWordMap, String sentenceCode) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        if (Tools.isNullorEmpty(sentenceCode)) return resultMap;
        for (Map.Entry<String, String> entry : hotWordMap.entrySet()) {
            if (sentenceCode.contains(entry.getKey()) && !resultMap.containsKey(entry.getKey())) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        return resultMap;
    }


    //region 热词索引
    private String getBeforHotWordChar(String sentence, String hotWord) {
        return sentence.substring(0, sentence.indexOf(hotWord));
    }

    //获取热词最开始的标点
    private String getHotWordBeginPunctuation(String hotWord) {
        String resultPun = "";
        for (char item : hotWord.toCharArray()) {
            if (Tools.isChineseChar(item)) {
                break;
            }
            resultPun += item;
        }
        return resultPun;
    }

    //获得热词结尾的标点
    private String getHotWordEndPunctuation(String hotWord) {
        String resultPun = "";
        for (int i = hotWord.length() - 1; i >= 0; i--) {
            if (Tools.isChineseChar(hotWord.charAt(i))) break;
            resultPun = hotWord.charAt(i) + resultPun;
        }
        return resultPun;
    }

    // 我在看《巴黎圣母院》  ->  《
    private String getSentenceBeginIndexPunctuation(String sentence, String hotWord, String hotWordBeginPunt) {
        String sentencePunt = sentence.substring(0, sentence.indexOf(hotWord));
        if (sentencePunt.length() < hotWordBeginPunt.length()) return "";
        return sentencePunt.substring(sentencePunt.length() - hotWordBeginPunt.length());
    }


    //我在看《巴黎圣母院》啊  ->  》
    private String getHotWordEndIndexPunctuation(String sentence, String hotWord, String hotWordEndPunct) {
        int punctIndex = sentence.indexOf(hotWord) + hotWord.length();
        return sentence.substring(punctIndex, punctIndex + hotWordEndPunct.length() - 1);

    }

    /**
     *  查找热词在这句话中出现的次数：用于处理一句话中包含多个热词的情况
     */
    private int getHotWordCoutInSentence(String hotword, String sentence) {
        if (hotword == null || sentence == null) return 0;
        String tempSentence = sentence.replace(hotword, "");
        return (sentence.length() - tempSentence.length()) / hotword.length();
    }


    /**
     *   查找第virtualIndex 次出现汉字的索引 用于查找出现的热词索引（withPun : 加上标点）
     */
    private int caculateChineseRealIndexWithPun(String filterChar, int virtualIndex) {
        int realCount = calculateBeforIndexChineseCount(filterChar, virtualIndex, -1);
        return realCount > filterChar.length() ? filterChar.length() - 1 : realCount - 1;
    }

    /**
     *   查找第virtualIndex 次出现汉字的索引（默认不处理标点）
     */
    private int calculateChineseRealIndex(String filterChar, int virtualIndex) {
        return calculateBeforIndexChineseCount(filterChar, virtualIndex, 0);
    }

    /**
     *   得到热词前面有多少个汉字
     *   参数说明：
     *   sentence: 我在看《巴黎圣母院》      /   热词为：巴黎圣母院
     *   endIndex=0 ：返回不包含标点的索引（看）
     *   endIndex=-1 ：返回包含标点的索引（《）
     */
    private int calculateBeforIndexChineseCount(String sentence, int limitIndex, int endIndex) {
        int realCount = 0;
        for (char item : sentence.toCharArray()) {
            if (limitIndex == endIndex) break;
            if (Tools.isChineseChar(item)) limitIndex--;
            realCount++;
        }
        return realCount;
    }
    //endregion

    //region 修改设置接口

    /**
     *  重新编码模糊音设置
     *
     */
    public void recodeHotWord(ArrayList<String> fuzzyList) {
        codeObj.reloadFuzzySetting(fuzzyList);
        totalHotWordMap.clear();
        mergeUserWordAndCourWord();
    }

    /**
     *  重新加载用户热词
     */
    public void reloadUserWord() {
        codeObj.loadLocalHotWord();
        totalHotWordMap.clear();
        mergeUserWordAndCourWord();
    }

    /**
     *  设置热词
     */
    public void setUserHotWord(ArrayList<String> wordList ){
        codeObj.setUserWordList(wordList);
        reloadUserWord();
    }

    /**
     *  设置多音字
     */
    public void setIsPhoneticize(boolean b)
    {
        codeObj.setPolyphone(b);
    }
    //endregion


}
