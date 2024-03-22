package com.bachelor.thesis.organization_education.services.implementations;

import org.apache.commons.lang.reflect.FieldUtils;
import org.mockito.Mock;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.responces.user.UserResponse;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CrudServiceAbstractTest {
    @Mock
    private JpaRepository<BaseTableInfo, Long> repositoryMock;
    @Mock
    private CrudServiceAbstract<BaseTableInfo, JpaRepository<BaseTableInfo, Long>> serviceMock;
    @Mock
    private Request requestMock;
    @Mock
    private BaseTableInfo tableInfoMock;

    @Test
    @DisplayName("Checking for an exception when null was passed in the request to add data.")
    void testAddValueThrowsNullPointerException() {
        addValueCallRealMethod();
        assertThrows(NullPointerException.class, () -> serviceMock.addValue(requestMock));
    }

    @Test
    @DisplayName("Checking for an exception when the request to add data contained data that was already in the table.")
    void testAddValueThrowsDuplicateException() {
        when(serviceMock.isDuplicate(requestMock)).thenReturn(true);
        addValueCallRealMethod();
        assertThrows(DuplicateException.class, () -> serviceMock.addValue(requestMock));
    }

    @Test
    @DisplayName("Checking for the correct addition of a new entity to the table")
    void testAddValueCorrect() throws Exception {
        FieldUtils.writeField(serviceMock, "repository", repositoryMock, true);
        Response response = UserResponse.builder()
                .username("username@gmail.com")
                .role(Role.SYSTEM_ADMIN)
                .build();

        when(repositoryMock.save(any(BaseTableInfo.class))).thenReturn(tableInfoMock);
        when(serviceMock.isDuplicate(requestMock)).thenReturn(false);
        when(serviceMock.createEntity(requestMock)).thenReturn(tableInfoMock);
        when(tableInfoMock.getResponse()).thenReturn(response);

        addValueCallRealMethod();

        Response returnResponse = serviceMock.addValue(requestMock);

        assertThat(returnResponse)
                .isNotNull()
                .isEqualTo(response);
    }

    void addValueCallRealMethod() {
        doCallRealMethod()
                .when(serviceMock)
                .addValue(requestMock);
    }

    @Test
    @DisplayName("Check for an exception when the request to activate an entity failed to find the entity.")
    void testEnableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.findEntity(requestMock)).thenReturn(Optional.empty());

        doCallRealMethod()
                .when(serviceMock)
                .enable(requestMock);

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.enable(requestMock));
    }

    @Test
    @DisplayName("Check for an exception when the request to deactivate an entity failed to find the entity.")
    void testDisableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.findEntity(requestMock)).thenReturn(Optional.empty());

        doCallRealMethod()
                .when(serviceMock)
                .disable(requestMock);

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.disable(requestMock));
    }
}
