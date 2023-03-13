package com.parentapp.backend.fixture;

import com.parentapp.backend.auth.model.URole;
import com.parentapp.backend.users.UserDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class UserDTOFixture {
    private static UserDTO testUserDTO1;
    private static UserDTO testUserDTO2;
    static {
        testUserDTO1 = UserDTO.builder()
                .id("test01")
                .username("ParentAssistAppTest1")
                .email("testparentassist@gmail.com")
                .password("testparentassist")
                .userRole(Set.of(URole.USER))
                .build();
        testUserDTO2 = UserDTO.builder()
                .id("test02")
                .username("ParentAssistAppTest2")
                .email("test@gmail.com")
                .password("testparentassist2")
                .userRole(Set.of(URole.USER))
                .build();
    }
    public static UserDTO testUserDTO() {
        return testUserDTO1;
    }

    public static List<UserDTO> userDTOList() {
        return Arrays.asList(testUserDTO1, testUserDTO2);
    }

}
