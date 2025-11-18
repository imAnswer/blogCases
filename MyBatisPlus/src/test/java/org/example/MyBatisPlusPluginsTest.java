package org.example;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.mapper.ProductMapper;
import org.example.mapper.UserMapper;
import org.example.pojo.Product;
import org.example.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liushaoya
 * @since 2025-05-13 14:16
 */
@SpringBootTest
public class MyBatisPlusPluginsTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void test() {
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPage(page, null);
        //获取查询结果数据
        System.out.println(page.getRecords());
        //获取总页数
        System.out.println(page.getPages());
        //获取总记录数
        System.out.println(page.getTotal());
        //是否有下一页
        System.out.println(page.hasNext());
        //是否有上一页
        System.out.println(page.hasPrevious());
    }

    @Test
    public void testPageVo() {
        Page<User> page = new Page(1, 3);
        userMapper.selectPageVo(page, 20);
        //获取查询结果数据
        System.out.println(page.getRecords());
        //获取总页数
        System.out.println(page.getPages());
        //获取总记录数
        System.out.println(page.getTotal());
        //是否有下一页
        System.out.println(page.hasNext());
        //是否有上一页
        System.out.println(page.hasPrevious());
    }

    /**
     * 一件商品，成本价是80元，售价是100元。老板先是通知小李，说你去把商品价格增加50元。小
     * 李正在玩游戏，耽搁了一个小时。正好一个小时后，老板觉得商品价格增加到150元，价格太
     * 高，可能会影响销量。又通知小王，你把商品价格降低30元。
     * 此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格100元；小王
     * 也在操作，取出的商品价格也是100元。小李将价格加了50元，并将100+50=150元存入了数据
     * 库；小王将商品减了30元，并将100-30=70元存入了数据库。是的，如果没有锁，小李的操作就
     * 完全被小王的覆盖了。
     * 现在商品价格是70元，比成本价低10元。几分钟后，这个商品很快出售了1千多件商品，老板亏1
     * 万多
     */
    @Test
    public void testSelectMapById() {
        //小李查询商品价格
        Product productLi = productMapper.selectById(1);
        System.out.println("小李查询的商品价格：" + productLi.getPrice());
        //小王查询商品价格
        Product productWang = productMapper.selectById(1);
        System.out.println("小王查询的商品价格：" + productWang.getPrice());
        //小李将商品价格加50
        productLi.setPrice(productLi.getPrice() + 50);
        productMapper.updateById(productLi);
        //小王将商品价格-30
        productWang.setPrice(productWang.getPrice() - 30);
        int result = productMapper.updateById(productWang);
        if(result == 0) {
            //操作失败，重试
            Product productNew = productMapper.selectById(1);
            productNew.setPrice(productNew.getPrice() - 30);
            productMapper.updateById(productNew);
        }

        //老板查询商品价格
        Product productBoss = productMapper.selectById(1);
        System.out.println("老板查询的商品价格：" + productBoss.getPrice());
    }
}
