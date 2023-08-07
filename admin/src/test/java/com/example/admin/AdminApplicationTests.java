package com.example.admin;

import com.example.library.service.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    public void testEdit() {
            long a =2;
        Assertions.assertThat(categoryService.findById(a).getName().equals("1"));

    }

}


