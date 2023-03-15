package com.parentapp.fixture;

import com.parentapp.auth.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class UserFixture {
    private static final User testUser1;
    private static final User testUser2;
    static{
        testUser1 = User.builder()
                .id("test01")
                .username("ParentAssistAppTest1")
                .email("testparentassist@gmail.com")
                .password("testparentassist")
                .userRole(Set.of(RoleFixture.userRole))
                .build();
        testUser2 = User.builder()
                .id("test02")
                .username("ParentAssistAppTest2")
                .email("test@gmail.com")
                .password("testparentassist2")
                .userRole(Set.of(RoleFixture.userRole))
                .build();
    }
    public static User testUser(){
        return testUser1;
    }
    public static List<User> testUserList(){
        return Arrays.asList(testUser1, testUser2);
    }
}
