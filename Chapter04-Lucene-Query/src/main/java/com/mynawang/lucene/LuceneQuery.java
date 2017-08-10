package com.mynawang.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

/**
 * Created by mynawang on 2017/8/10 0010.
 */
public class LuceneQuery {

    public static void main(String[] args) {
        Analyzer analyzer = new StandardAnalyzer();

        String queryKey = "Lucene简介";

        // 单个搜索域
        String field = "content";

        // 多个搜索域
        String[] fileds = {"name", "content"};

        // 单个域构建查询语句
        QueryParser queryParser = new QueryParser(field, analyzer);
        try {
            Query query = queryParser.parse(queryKey);
            System.out.println(queryParser.getClass());
            System.out.println(query.toString());
            System.out.println("\n");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 多个域构建查询语句
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fileds, analyzer);
        try {
            Query query = multiFieldQueryParser.parse(queryKey);
            System.out.println(multiFieldQueryParser.getClass());
            System.out.println(query.toString());
            System.out.println("\n");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // 词条搜索
        try {
            Term term = new Term(field, queryKey);
            Query query = new TermQuery(term);
            System.out.println(query.getClass());
            System.out.println(query.toString());
            System.out.println("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 前缀搜索
        try {
            Term term = new Term(field, queryKey);
            PrefixQuery prefixQuery = new PrefixQuery(term);
            System.out.println(prefixQuery.getClass());
            System.out.println(prefixQuery.toString());
            System.out.println("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 短语搜索
        try {
            PhraseQuery.Builder builder = new PhraseQuery.Builder();
            builder.setSlop(2);
            builder.add(new Term("content", "基于"), 0);
            builder.add(new Term("content", "工具包"), 1);
            PhraseQuery pq = builder.build();
            System.out.println(pq.getClass());
            System.out.println(pq.toString());
            System.out.println("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 通配符搜索
         *
         * ? 匹配单个字符
         * * 匹配0个或多个字符
         * =>想要搜索test或者text
         *  te?t
         * =>想要搜索test  tests  tester
         *  test*
         */
        Query query = new WildcardQuery(new Term(field, "基于?"));
        System.out.println(query.getClass());
        System.out.println(query.toString());
        System.out.println("\n");




        // 字符串范围搜索
        query = TermRangeQuery.newStringRange(field, "abc", "azz" , true, false);
        System.out.println(query.getClass());
        System.out.println(query.toString());



    }


}