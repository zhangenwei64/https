package com.aisino.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;

/**
 * @Author ：alfredzhang
 * @Description：
 * @Modified By：
 * @Version: :
 * @Date ：2019/8/2 15:59
 */
public class LuceneSecond {
    @Test
    public void creadIndex() throws Exception{
//        1.指定索引库的存放位置Directory对象,将索引库保存在磁盘中
//        Directory directory = new RAMDirectory();
        Directory directory = FSDirectory.open(new File("E:\\workspace\\4-JavaEE\\lucene\\tmp\\index").toPath());

//        2.指定一个IndexWriterConfig对象。
        IndexReader indexReader= DirectoryReader.open(directory);
//        3.创建一个indexsearch对象，构造方法中的参数为indexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        4.创建一个Query对象，TermQuery
        Query query = new TermQuery(new Term("content", "spring"));
//        5.执行查询，得到一个TopDocs对象
//        参数1：查询对象， 参数2：查询结果返回的最大记录
        TopDocs topDocs = indexSearcher.search(query, 10);
//        6.取查询结果的总记录数
        System.out.println("查询总统计数："+topDocs.totalHits);
//        7.取文档列表
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//        8.打印文档中的内容
        for (ScoreDoc doc : scoreDocs) {
//            8.1 取文档id
            int docId = doc.doc;
//            8.2 根据id取文档对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println(document.get("content"));
            System.out.println("----------------------------");
        }
//        第五步：关闭IndexWriter对象。
        indexReader.close();
    }
}
