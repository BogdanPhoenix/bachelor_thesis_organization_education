package com.bachelor.thesis.organization_education.controllers.user;

import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.junit.jupiter.params.ParameterizedTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.ResultMatcher;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationUserRequest;

import java.util.stream.Stream;

@SpringBootTest
@WebAppConfiguration
class UserControllerTest {
    private static final String URI_REGISTER = "/users/register";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectWriter objectWriter;
    private RegistrationUserRequest request;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        request = RegistrationUserRequest.builder()
                .username("username@gmail.com")
                .password("Qwertyuiop123!@#")
                .matchingPassword("Qwertyuiop123!@#")
                .firstName("First Name")
                .lastName("Last Name")
                .build();
    }

    @ParameterizedTest
    @MethodSource("usernameValues")
    @DisplayName("Checking the controller and validating the wrong username passed by the user.")
    void testValidUserName(String value) throws Exception {
        request.setUsername(value);
        postRequestToServer(MockMvcResultMatchers.status().isBadRequest());
    }

    private static @NonNull Stream<Arguments> usernameValues() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("         "),
                Arguments.of("fa"),
                Arguments.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et ante consequat, elementum neque non, volutpat metus. Vestibulum ut felis dui. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vel tincidunt sapien, et viverra elit. Praesent vestibulum "),
                Arguments.of("test_gmail.com"),
                Arguments.of("test@gmailcom"),
                Arguments.of("test@gmail_com"),
                Arguments.of("test1gmail.com")
        );
    }

    @ParameterizedTest
    @MethodSource("passwordValues")
    @DisplayName("Checking the controller and validating the wrong password transmitted by the user.")
    void testValidPassword(String value) throws Exception {
        request.setPassword(value);
        postRequestToServer(MockMvcResultMatchers.status().isBadRequest());
    }

    private static @NonNull Stream<Arguments> passwordValues() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("         "),
                Arguments.of("qwE@t1"),
                Arguments.of("qwertyui"),
                Arguments.of("qwe3#yui"),
                Arguments.of("qWert4tdfgd")
        );
    }

    @Test
    @DisplayName("Controller checking and validation when password and matchingPassword fields have different data.")
    void testMatchesPassword() throws Exception {
        request.setMatchingPassword("qWertyuiop123!@#");
        postRequestToServer(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("firstAndLastNameValues")
    @DisplayName("Checking the controller and validating the invalid firstName passed by the user.")
    void testValidFirstName(String value) throws Exception {
        request.setFirstName(value);
        postRequestToServer(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("firstAndLastNameValues")
    @DisplayName("Checking the controller and validating the invalid lastName passed by the user.")
    void testValidLastName(String value) throws Exception {
        request.setLastName(value);
        postRequestToServer(MockMvcResultMatchers.status().isBadRequest());
    }

    private static @NonNull Stream<Arguments> firstAndLastNameValues() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("         "),
                Arguments.of("f"),
                Arguments.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et ante consequat, elementum neque non, volutpat metus. Vestibulum ut felis dui. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vel tincidunt sapien, et viverra elit. Praesent vestibulum "),
                Arguments.of("first%name"),
                Arguments.of("first5name")
        );
    }

    private void postRequestToServer(ResultMatcher matcher) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URI_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(request)))
                .andExpect(matcher);
    }
}
