package com.mynawang.lucene;


import com.alibaba.fastjson.JSON;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * @auther mynawang
 * @create 2017-07-06 15:25
 */
public class HelloWorld {

    public void testLucene() throws IOException, ParseException {
        // 实例化词法分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 确定索引文件位置（内存中）
        // Directory directory = new RAMDirectory();

        // 确定文件在硬盘中，用下面这段
        Directory directory = FSDirectory.open(new File("E:\\JavaPOJO\\Lucene\\Rabbish\\testindex").toPath());
        // 对indexWriter进行配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        // 实例化Docement
        Document doc = new Document();
        // 将要用来索引的句子
        String text = "This is the text to be indexed.";
        // 存储text,表名为fieldname
        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
        // doc对象添加到索引创建中
        iwriter.addDocument(doc);
        // 关闭iwriter
        iwriter.close();

        // 打开存储位置
        DirectoryReader ireader = DirectoryReader.open(directory);
        // 创建索引搜索
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // 创建查询器，查询为fieldname的表
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("fieldname", analyzer);
        // 查询包含“text”
        Query query = parser.parse("text");
        // 查询得到的结果集
        ScoreDoc[] hits = isearcher.search(query, 100).scoreDocs;
        System.out.println(JSON.toJSONString(hits));
        // 遍历结果
        for (int i = 0; i < hits.length; i++) {
            System.out.println(i);
            Document hitDoc = isearcher.doc(hits[i].doc);
            System.err.println("This is the text to be indexed.".equals(hitDoc.get("fieldname")));
        }
        ireader.close();
        directory.close();
    }


    public static void main(String[] args) throws IOException, ParseException {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.testLucene();
    }

}
