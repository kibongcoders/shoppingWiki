package com.kibong.shoppingwiki.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.user.dto.RequestUser;
import com.kibong.shoppingwiki.user.dto.ResponseUserDto;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import com.kibong.shoppingwiki.user.service.UserService;
import com.kibong.shoppingwiki.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class SignUpTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    UserService userService;

    @Autowired
    Environment env;
    
    @DisplayName("회원가입 서비스 테스트")
    void signUpService(@Mock UserRepository userRepository){
        //given
        RequestUser requestUser = givenRequestUser();
        User givenUser = givenUser(requestUser);

        given(userRepository.getUserByUserEmailAndUserNickname(requestUser.getUserEmail(), requestUser.getUserNickname())).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(givenUser);

        //when
        //UserService userService = new UserServiceImpl(userRepository, passwordEncoder, env);

        ResponseUserDto user = userService.signUp(requestUser);

        //then
//        assertEquals(requestUser.getUserEmail(), user.getUserEmail());
//        assertEquals(requestUser.getUserNickname(), user.getUserNickname());
    }

    @DisplayName("회원가입 컨트롤러 테스트")
    @WithMockUser("user1")
    void signUpController() throws Exception {
        //given
        RequestUser requestUser = givenRequestUser();
        User user = givenUser(requestUser);

        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setToken("");
        //responseUserDto.setUserUuid("");

        given(userService.signUp(any(RequestUser.class))).willReturn(responseUserDto);

        //When
        RequestBuilder reuqest = MockMvcRequestBuilders.post("http://localhost:8000/shoppingwiki/user/createUser")
                .content(asJsonString(requestUser))
                .contentType("application/json")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(reuqest).andExpect(status().isCreated()).andReturn();

        assertEquals("{\"userId\":1}",mvcResult.getResponse().getContentAsString());
    }

     User givenUser(RequestUser requestUser){
         return User.builder()
                 .id(1L)
                 .userEmail(requestUser.getUserEmail())
                 .userNickname(requestUser.getUserNickname())
                 .userUseYn(true)
                 .password(requestUser.getPassword())
                 .build();
    }

    RequestUser givenRequestUser(){
        return new RequestUser("test1234@test.com", "1234", "김기봉");
    }

    // 객체를 JSON 문자열로 변환하는 메서드
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
