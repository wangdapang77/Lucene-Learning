package com.mynawang.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created by mynawang on 2017/8/8 0008.
 */
public class CreateIndex {
    public static void main(String[] args) {
        // 实例化词法分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 对indexWriter进行配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 设置索引文件的打开方式，没有就创建，有就打开【和平时sql中的insertOrUpdate类似】
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        Directory directory = null;
        IndexWriter indexWriter = null;
        try {
            // 设置索引硬盘存储路径
            directory = FSDirectory.open(new File("E:\\JavaPOJO\\Lucene\\indexes\\testindex").toPath());
            // 设置操作对象
            indexWriter = new IndexWriter(directory, indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建文档一
        Document document1 = new Document();
        // 对name域赋值“测试标题” 此处YES适用于存储之前没有使用分析器的文本，如标题等
        document1.add(new TextField("name", "测试标题", Field.Store.YES));
        document1.add(new TextField("content", "测试内容", Field.Store.YES));

        try {
            // 将文档写入索引中
            indexWriter.addDocument(document1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建文档二
        Document document2 = new Document();
        document2.add(new TextField("name", "Lucene简介", Field.Store.YES));
        document2.add(new TextField("content", "Lucene 是一个基于 Java 的全文" +
                "信息检索工具包，它不是一个完整的搜索应用程序，而是为你的应用程序提供" +
                "索引和搜索功能。", Field.Store.NO));

        try {
            // 将文档写入索引中
            indexWriter.addDocument(document2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 提交事务，关闭资源
            indexWriter.commit();
            indexWriter.close();
            directory.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
