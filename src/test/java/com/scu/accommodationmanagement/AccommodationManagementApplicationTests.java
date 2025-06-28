package com.scu.accommodationmanagement;

import com.scu.accommodationmanagement.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccommodationManagementApplicationTests {

    @Test
    void uploadTest() {

    }

    @Test
    void Md5Test() {
        String defaultPassword = "2022141460307";
        defaultPassword = defaultPassword.substring(Math.max(0, defaultPassword.length() - 6));
        System.out.println(Md5Util.getMD5String(defaultPassword));
    }
}
