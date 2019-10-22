import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: CIOTest1.java
 * @time: 2019/10/22 16:00
 * @desc:
 */

public class CIOTest1 {
    public static void main(String[] args) throws IOException {
        // 文件大小
        long len = FileUtils.sizeOf(new File("D:\\李添的数据哦！！！\\BookStudy\\else\\JAVAPro\\src\\CIOTest1.java"));
        System.out.println(len);

        // 目录大小
        len = FileUtils.sizeOf(new File("D:\\李添的数据哦！！！\\BookStudy"));
        System.out.println(len);

        // 列出子孙集
        /*
        第一个参数：目标路径
        第二个参数：过滤文件：
            NOT_EMPTY，即只要非空文件
            SuffixFileFilter，即只要该后缀名的文件
        第三个参数：过滤目录：
            INSTANCE，即只看子孙集
         */
        Collection<File> files = FileUtils.listFiles(
                new File("D:\\李添的数据哦！！！\\BookStudy\\else\\JAVAPro"),
                FileFilterUtils.or(EmptyFileFilter.NOT_EMPTY, new SuffixFileFilter("java"), new SuffixFileFilter("class")), DirectoryFileFilter.INSTANCE
        );
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }

        // 读取文件内容
        String path = "D:\\李添的数据哦！！！\\BookStudy\\else\\【参考】3. 代码快捷键操作.md";
        String msg = FileUtils.readFileToString(new File(path), "UTF-8");
        System.out.println(msg);
        byte[] datas = FileUtils.readFileToByteArray(new File(path));
        System.out.println(datas.length);

        // 逐行读取
        List<String> msgs = FileUtils.readLines(new File((path)), "UTF-8");
        for (String str : msgs) {
            System.out.println(str);
        }
        // 逐行读取2
        LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        while (it.hasNext()) {
            System.out.println(it.nextLine());
        }

        // 写出内容到文件
        FileUtils.write(new File("happy.txt"), "学习是一件伟大的事业\n", "UTF-8");
        FileUtils.writeStringToFile(new File("happy.txt"), "学习是一件辛苦的事业\n", "UTF-8", true);
        FileUtils.writeByteArrayToFile(new File("happy.txt"), "学习是一件快乐的事业\n".getBytes("UTF-8"), true);

        // 写出列表
        List<String> dd = new ArrayList<>();
        dd.add("马云");
        dd.add("马化腾");
        dd.add("礼拜");
        FileUtils.writeLines(new File("happy.txt"), dd, "-", true);

    }
}
