package test;

import com.sxt.SORM.core.Query;
import com.sxt.SORM.core.QueryFactory;
import po.Emp;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: Test.java
 * @time: 2020/3/17 17:38
 * @desc: |
 */

public class Test {
    public static void main(String[] args) {
        // 通过这个方法可以生成po类
        // TableContext.updateJavaPOFile();

        // add();
        // select();
        // delete();
        update();

    }

    public static void add() {
        // 测试插入对象
        Emp e = new Emp();
        e.setAge(18);
        e.setEmpname("我");
        e.setSalary(2000.0);

        Query q = QueryFactory.createQuery();
        q.insert(e);
    }

    public static void delete(){
        // 测试删除对象
        Emp e = new Emp();
        e.setId(12);
        Query q = QueryFactory.createQuery();
        q.delete(e);
    }

    public static void update(){
        // 测试删除对象
        Emp e = new Emp();
        e.setId(1);
        e.setAge(1);
        Query q = QueryFactory.createQuery();
        q.update(e, new String[]{"age"});
    }

    public static void select(){
        // 测试查询
        Query q = QueryFactory.createQuery();
        Number n = q.queryNumber("select count(*) from emp where salary>?", new Object[]{100});
        System.out.println(n);
    }
}
