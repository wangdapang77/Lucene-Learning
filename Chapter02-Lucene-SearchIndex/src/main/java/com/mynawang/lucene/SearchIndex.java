package com.mynawang.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created by mynawang on 2017/8/8 0008.
 */
public class SearchIndex {

    public static void main(String[] args) {
        Directory directory = null;
        try {
            // 索引在硬盘中存储路径
            directory = FSDirectory.open(new File("E:\\JavaPOJO\\Lucene\\indexes\\testindex").toPath());
            // 读取索引
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            // 创建索引检索对象
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

            // 实例化分词标准分析器
            Analyzer analyzer = new StandardAnalyzer();

            QueryParser queryParser = new QueryParser("content", analyzer);
            Query query = queryParser.parse("工具包");

            TopDocs topDocs = indexSearcher.search(query, 10);
            if (null != topDocs) {
                System.out.println("find counts: " + topDocs.totalHits);

                for (int i = 0; i < topDocs.scoreDocs.length; i++) {

                    Document doc = indexSearcher.doc(topDocs.scoreDocs[i].doc);
                    System.out.println("the i name:" + doc.get("name") + " content: " + doc.get("content"));
                }
            }

            directoryReader.close();
            directory.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
