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
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @auther mynawang
 * @create 2017-07-06 15:25
 */
public class HelloWorld {

    public void testLucene() throws IOException, ParseException {
        // 实例化词法分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 确定索引文件位置（内存中）
        Directory directory = new RAMDirectory();
        // 确定文件在硬盘中，用下面这段
        //Directory directory = FSDirectory.open("/tmp/testindex");
        // 对indexWriter进行配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        // 实例化Docement
        Document doc = new Document();
        // 将要用来索引的句子
        String text = "This is the text to be indexed.";
        // 存储为fieldname文件
        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
        // doc对象添加到索引创建中
        iwriter.addDocument(doc);
        // 关闭iwriter
        iwriter.close();

        // 打开存储位置
        DirectoryReader ireader = DirectoryReader.open(directory);
        // 创建索引搜索
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("fieldname", analyzer);
        Query query = parser.parse("text");
        ScoreDoc[] hits = isearcher.search(query, 5).scoreDocs;
        System.out.println(JSON.toJSON(hits));

        System.out.println(1 == hits.length);
        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
        }
        ireader.close();
        directory.close();

    }


    public static void main(String[] args) throws IOException, ParseException {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.testLucene();
    }

}
