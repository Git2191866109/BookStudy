import java.io.*;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: TestFileCopy.java
 * @time: 2019/10/17 18:22
 * @desc: 文件的拷贝
 */

public class TestFileCopy {
    public static void main(String[] args){
        // 1. 创建源
        // 源头
        File src = new File("abc.txt");
        File dest = new File("dest.txt");
        // 2. 选择流
        InputStream is = null;
        OutputStream os = null;
        try{
            is = new FileInputStream(src);
            os = new FileOutputStream(dest, true);
            //

        }
    }
}
