package com.example.source.item;

import com.example.source.item.util.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SourceItemApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(DigestUtils.Md5("admin", "123456"));
    }

}
