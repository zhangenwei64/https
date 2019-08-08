package com.aisino.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author ：alfredzhang
 * @Description：
 * @Modified By：
 * @Version: :
 * @Date ：2019/8/2 15:16
 */
public class LuceneFirst {
    @Test
    public void creadIndex() throws Exception {
//        1.指定索引库的存放位置Directory对象,将索引库保存在磁盘中
//        Directory directory = new RAMDirectory();
        Directory directory = FSDirectory.open(new File("E:\\workspace\\4-JavaEE\\lucene\\tmp\\index").toPath());

//        2.指定一个IndexWriterConfig对象。
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());

//            3.读取磁盘上的文件，对应每个文件创建一个文档对象
        File dir = new File("E:\\workspace\\4-JavaEE\\lucene\\tmp\\searchsource");
        File[] files = dir.listFiles();
        for (File file : files) {
//            3.1获取文件名
            String fileName = file.getName();
//            3.2 获取文件路径
            String filePath = file.getPath();
//            3.2 获取文件内容
            String fileContent = FileUtils.readFileToString(file, "utf-8");
//            3.4 获取文件大小
            long fileSize = FileUtils.sizeOf(file);

//            创建filed
//            参数1：域的名称 参数2:域的内容 参数3：是否存储
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);
//            4.创建文档对象，想文档中添加域
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);
//            5.将文档对象写入文档
            indexWriter.addDocument(document);
        }
//        6.关闭indexWriter对象
        indexWriter.close();
    }



    @Test
    public void testTokenStream() throws Exception {
//        String testString = "The Spring Framework provides a comprehensive programming and configuration model.";
        //创建一个标准分析器对象
//        Analyzer analyzer = new StandardAnalyzer();
        String testString = "2006年，反党几个热血青年怀揣着对教育事业的拳拳之心，一起探讨中国教育的发展方向，同年5月8日传智播客正式成立，改变中国IT教育的星星之火由此点燃。\n" +
                "历经13年风雨，传智播客从当年那不足10人的小团队，已发展成为现在拥有2000多名员工的教育集团；从成立最初的单一Java学科，到现在已包括JavaEE、Python+人工智能、前端与移动开发、UI/UE设计、大数据、Go语言与区块链等14门学科培训；从屈指可数的几间教室，发展成为分布于北京、上海、广州、深圳、武汉、郑州、西安、长沙、济南、重庆、南京、杭州、成都、石家庄、合肥、太原、厦门、沈阳等18所城市直营中心的规模。目前旗下也已拥有了“黑马程序员”、 “博学谷”、“传智专修学院”、“酷丁鱼”、“传智汇”、“院校邦”等子品牌。";
        //创建一个标准分析器对象
        Analyzer analyzer = new IKAnalyzer();

        //获得tokenStream对象
        //第一个参数：域名，可以随便给一个
        //第二个参数：要分析的文本内容
        TokenStream tokenStream = analyzer.tokenStream("test", testString);
        //添加一个引用，可以获得每个关键词
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //将指针调整到列表的头部
        tokenStream.reset();
        //遍历关键词列表，通过incrementToken方法判断列表是否结束
        while(tokenStream.incrementToken()) {
            //关键词的起始位置
//            System.out.println("start->" + offsetAttribute.startOffset());
            //取关键词
            System.out.println(charTermAttribute);
            //结束位置
//            System.out.println("end->" + offsetAttribute.endOffset());
        }
        tokenStream.close();
    }


    @Test
    public void creadIndexNew() throws Exception {
//        1.指定索引库的存放位置Directory对象,将索引库保存在磁盘中
//        Directory directory = new RAMDirectory();
        Directory directory = FSDirectory.open(new File("E:\\workspace\\4-JavaEE\\lucene\\tmp\\index\\newIndex").toPath());

//        2.指定一个IndexWriterConfig对象。
        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, config);

//        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());

//            3.读取磁盘上的文件，对应每个文件创建一个文档对象
        File dir = new File("E:\\workspace\\4-JavaEE\\lucene\\tmp\\searchsource");
        File[] files = dir.listFiles();
        for (File file : files) {
//            3.1获取文件名
            String fileName = file.getName();
//            3.2 获取文件路径
            String filePath = file.getPath();
//            3.2 获取文件内容
            String fileContent = FileUtils.readFileToString(file, "utf-8");
//            3.4 获取文件大小
            long fileSize = FileUtils.sizeOf(file);

//            创建filed
//            参数1：域的名称 参数2:域的内容 参数3：是否存储
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);
//            4.创建文档对象，想文档中添加域
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);
//            5.将文档对象写入文档
            indexWriter.addDocument(document);
        }
//        6.关闭indexWriter对象
        indexWriter.close();
    }
}
