package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.events.RegistrationCompleteEvent;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
class RegistrationControllerTest {
    private static final String URI = "http://localhost:8080/organization-education/register";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userServiceMock;
    @MockBean
    private ApplicationEventPublisher applicationEventPublisherMock;
    @MockBean
    private HttpServletRequest httpServletRequestMock;
    @MockBean
    private ApplicationListener<RegistrationCompleteEvent> applicationListenerMock;

    private MockMvc mockMvc;

    @MockBean
    private BindingResult bindingResultMock;

    private ObjectWriter objectWriter;

    private RegistrationRequest request;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        request = RegistrationRequest.builder()
                .username("username@gmail.com")
                .password("Qwertyuiop123!@#")
                .matchingPassword("Qwertyuiop123!@#")
                .role(Role.ADMIN)
                .firstName("First Name")
                .lastName("Last Name")
                .build();
    }

    @ParameterizedTest
    @MethodSource("usernameValues")
    @DisplayName("Checking the controller and validating the wrong username passed by the user.")
    void testValidUserName(String value) throws Exception {
        request.setUsername(value);
        accessToServerBadRequest();
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
        accessToServerBadRequest();
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
        accessToServerBadRequest();
    }

    @ParameterizedTest
    @MethodSource("firstAndLastNameValues")
    @DisplayName("Checking the controller and validating the invalid firstName passed by the user.")
    void testValidFirstName(String value) throws Exception {
        request.setFirstName(value);
        accessToServerBadRequest();
    }

    @ParameterizedTest
    @MethodSource("firstAndLastNameValues")
    @DisplayName("Checking the controller and validating the invalid lastName passed by the user.")
    void testValidLastName(String value) throws Exception {
        request.setLastName(value);
        accessToServerBadRequest();
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

    private void accessToServerBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Checking the controller for correct user registration in the system.")
    void testRegistrationCorrect() throws Exception {
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(userServiceMock.registration(any(RegistrationRequest.class))).thenReturn(new RegisteredResponse("", ""));
        when(httpServletRequestMock.getRequestURL()).thenReturn(new StringBuffer());
        doNothing().when(applicationListenerMock).onApplicationEvent(any(RegistrationCompleteEvent.class));
        doNothing().when(applicationEventPublisherMock).publishEvent(any());

        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
