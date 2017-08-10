package com.mynawang.lucene;

import org.ansj.lucene6.AnsjAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.cz.CzechAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.nl.DutchAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by mynawang on 2017/8/9 0009.
 */
public class LuceneAnalyzer {

    public static void main(String[] args) {

        Analyzer analyzer = null;

        // 标准分词器
        analyzer = new StandardAnalyzer();
        exeAnalyzer(analyzer);

        // 空格分词器
        analyzer = new WhitespaceAnalyzer();
        exeAnalyzer(analyzer);

        // 简单分词器
        analyzer = new SimpleAnalyzer();
        exeAnalyzer(analyzer);

        // 二分法分词器（同一个字会和它左边右边各组合成一个词）
        analyzer = new CJKAnalyzer();
        exeAnalyzer(analyzer);

        // 关键字分词
        analyzer = new KeywordAnalyzer();
        exeAnalyzer(analyzer);

        // 被忽略的词分词器（如标点、空格等）
        analyzer = new StopAnalyzer();
        exeAnalyzer(analyzer);

        // 中文分词 与标准分词一致，在Lucene新版本中去除
        //analyzer = new ChineseAnalyzer();

        // 巴西语言分词
        analyzer = new BrazilianAnalyzer();

        // 捷克语言分词
        analyzer = new CzechAnalyzer();

        // 荷兰语言分词
        analyzer = new DutchAnalyzer();

        // 法国语言分词
        analyzer = new FrenchAnalyzer();

        // 德国语言分词
        analyzer = new GermanAnalyzer();

        // 希腊语言分词
        analyzer = new GreekAnalyzer();

        // 俄罗斯语言分词
        analyzer = new RussianAnalyzer();

        // 泰国语言分词
        analyzer = new ThaiAnalyzer();

        // 中文分词Ansj
        analyzer = new AnsjAnalyzer(AnsjAnalyzer.TYPE.base_ansj);
        exeAnalyzer(analyzer);
    }

    public static void exeAnalyzer(Analyzer analyzerIn) {
        String str = "Lucene 是一个基于 Java 的全文信息检索工具包，它不是一个完整的搜索" +
                "应用程序，而是为你的应用程序提供索引和搜索功能。";
        Analyzer analyzer = analyzerIn;
        //使用分词器处理测试字符串
        StringReader reader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream("", reader);
        try {
            tokenStream.reset();
            CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
            int l = 0;
            //输出分词器和处理结果
            System.out.println(analyzer.getClass() + "====");
            while(tokenStream.incrementToken()){
                System.out.print(term.toString() + "|");
                l += term.toString().length();
                //如果一行输出的字数大于30，就换行输出
                if (l > 30) {
                    System.out.println();
                    l = 0;
                }
            }
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
