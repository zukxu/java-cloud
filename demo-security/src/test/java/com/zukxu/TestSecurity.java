package com.zukxu;

import com.zukxu.controller.HelloService;
import com.zukxu.model.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 15:56:13
 */
@SpringBootTest
public class TestSecurity {

    @Autowired
    private HelloService helloService;

    @Test
    //@WithMockUser(roles = "ADMIN")
    @WithMockUser(roles = "admin")
    void testPreAuthorize() {
        String hello = helloService.hello();
        assertNotNull(hello);
    }

    @Test
    //@WithMockUser(roles = "ADMIN", username = "zukxu")
    @WithMockUser(roles = "ADMIN", username = "zukxu1")
    void testPreAuthorize2() {
        String hello = helloService.hello2();
        assertNotNull(hello);
    }

    @Test
    @WithMockUser(username = "zukxu")
    void testPreAuthorize3() {
        String hello = helloService.hello3("zukxu");
        assertNotNull(hello);
    }

    @Test
    @WithMockUser(username = "zukxu")
    void testPreFilter() {
        List<SysUser> users = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            users.add(new SysUser(i, "zukxu :" + i));
        }
        helloService.addUsers(users, 99);
    }

    @Test
    @WithMockUser(username = "zukxu")
    void testPostAuthorize() {
        SysUser user = helloService.getUserById(1);
        System.out.println(user);
        assertNotNull(user);
    }

    @Test
    @WithMockUser(username = "zukxu")
    void testPostFilter() {
        List<SysUser> user = helloService.listUser();
        user.forEach(System.out::println);
        assertNotNull(user);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "zukxu")
    void testSecured() {
        SysUser user = helloService.getUserByUsername("zukxu");
        System.out.println(user);
        assertNotNull(user);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "zukxu")
    void testRolesAllowed() {
        String s = helloService.rolesAllowed();
        System.out.println(s);
        assertNotNull(s);
    }

}
