package com.bachelor.thesis.organization_education.services.implementations.user;

import org.mockito.Mock;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.boot.test.context.SpringBootTest;
import com.bachelor.thesis.organization_education.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.bachelor.thesis.organization_education.requests.user.UserRequest;
import com.bachelor.thesis.organization_education.responces.user.UserResponse;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.user.UserInfoResponse;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserInfoService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserCrudServiceImplTest {
    private static final RegistrationRequest request = RegistrationRequest
            .builder()
            .username("username@gmail.com")
            .password("qwertyuiop[]")
            .matchingPassword("qwertyuiop[]")
            .role(Role.SYSTEM_ADMIN)
            .firstName("firstName")
            .lastName("lastName")
            .build();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserCrudServiceImpl serviceMock;
    @Mock
    private UserInfoService userInfoServiceMock;

    @Test
    @DisplayName("Check for the exception when a null value was passed in the user registration request.")
    void testCreateUserThrowsNullPointerException() {
        RegistrationRequest request = null;
        doCallRealMethod().when(serviceMock).createUser(request);
        assertThrows(NullPointerException.class, () -> serviceMock.createUser(request));
    }

    @Test
    @DisplayName("Check for the exception when a user exists in the database with the data passed in the query.")
    void testCreateUserThrowsDuplicateException() {
        when(serviceMock.addValue(any(Request.class))).thenThrow(DuplicateException.class);
        doCallRealMethod().when(serviceMock).createUser(request);

        assertThrows(DuplicateException.class, () -> serviceMock.createUser(request));
    }

    @Test
    @DisplayName("Checking the method for correct user registration.")
    void testCreateUserCorrect() throws Exception {
        FieldUtils.writeField(serviceMock, "userInfoService", userInfoServiceMock, true);
        UserResponse userResponse = UserResponse
                .builder()
                .username(request.getUsername())
                .role(request.getRole())
                .build();
        User user = User
                .builder()
                .id(1L)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        when(serviceMock.addValue(any(Request.class))).thenReturn(userResponse);
        when(serviceMock.createEntity(any(Request.class))).thenReturn(user);
        when(userInfoServiceMock.addValue(any(Request.class))).thenReturn(new UserInfoResponse(request.getFirstName(), request.getLastName()));

        doCallRealMethod().when(serviceMock).createUser(request);

        UserDetails userActual = serviceMock.createUser(request);

        assertThat(userActual)
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    @DisplayName("Check for the NotFindEntityInDataBaseException exception when searching for a registered user in the database.")
    void testFindAuthenticatedUserThrowNotFindEntityInDataBaseException() {
        UserRequest request = new UserRequest();
        when(serviceMock.findEntity(any(Request.class))).thenReturn(Optional.empty());
        doCallRealMethod().when(serviceMock).findAuthenticatedUser(any(UserRequest.class));
        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.findAuthenticatedUser(request));
    }
}
