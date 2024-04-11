package com.bachelor.thesis.organization_education.services.implementations;

import com.bachelor.thesis.organization_education.requests.UpdateRequest;
import org.apache.commons.lang.reflect.FieldUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CrudServiceAbstractTest {
    @Mock
    private JpaRepository<BaseTableInfo, Long> repositoryMock;
    @Mock
    private CrudServiceAbstract<BaseTableInfo, JpaRepository<BaseTableInfo, Long>> serviceMock;
    @Mock
    private Request requestMock;
    @Mock
    private Response responseMock;
    @InjectMocks
    private UpdateRequest<Response, Request> updateRequestMock;
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
        Response response = mock(Response.class);

        when(repositoryMock.save(any(BaseTableInfo.class))).thenReturn(tableInfoMock);
        when(serviceMock.isDuplicate(requestMock)).thenReturn(false);
        when(serviceMock.createEntity(requestMock)).thenReturn(tableInfoMock);
        when(tableInfoMock.getResponse()).thenReturn(response);
        addValueCallRealMethod();

        serviceMock.addValue(requestMock);

        verify(repositoryMock).save(any());
        verify(tableInfoMock).getResponse();
    }

    void addValueCallRealMethod() {
        doCallRealMethod()
                .when(serviceMock)
                .addValue(requestMock);
    }

    @Test
    @DisplayName("Check for an exception when the request to activate an entity failed to find the entity.")
    void testEnableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.findEntity(requestMock)).thenThrow(NotFindEntityInDataBaseException.class);

        doCallRealMethod()
                .when(serviceMock)
                .enable(requestMock);

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.enable(requestMock));
    }

    @Test
    @DisplayName("Check for an exception when the request to deactivate an entity failed to find the entity.")
    void testDisableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.findEntity(requestMock)).thenThrow(NotFindEntityInDataBaseException.class);

        doCallRealMethod()
                .when(serviceMock)
                .disable(requestMock);

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.disable(requestMock));
    }

    @Test
    @DisplayName("Check for an exception when getting an entity was passed a null value in the request. ")
    void testGetValueThrowsNullPointerException() {
        getValueCallRealMethod();
        assertThrows(NullPointerException.class, () -> serviceMock.getValue(requestMock));
    }

    @Test
    @DisplayName("Check the exception when the entity could not be found for the specified query.")
    void testGetValueThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.getEntity(requestMock)).thenThrow(NotFindEntityInDataBaseException.class);
        getValueCallRealMethod();

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.getValue(requestMock));
    }

    void getValueCallRealMethod() {
        doCallRealMethod()
                .when(serviceMock)
                .getValue(requestMock);
    }

    @Test
    @DisplayName("Check for an exception when the data update request contains data that already exists in the table.")
    void testUpdateValueThrowsDuplicateException() {
        when(serviceMock.isDuplicate(any(Request.class))).thenReturn(true);
        updateValueCallRealMethod();

        assertThrows(DuplicateException.class, () -> serviceMock.updateValue(updateRequestMock));
    }

    @Test
    @DisplayName("Check for an exception when the data update request contains data that is not in the table.")
    void testUpdateValueThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.isDuplicate(any(Request.class))).thenReturn(false);
        when(responseMock.getId()).thenReturn(1L);
        when(serviceMock.findById(any(Long.class))).thenThrow(NotFindEntityInDataBaseException.class);
        updateValueCallRealMethod();

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.updateValue(updateRequestMock));
    }

    @Test
    @DisplayName("Checking for the correct update of the entity.")
    void testUpdateValueCorrect() throws IllegalAccessException {
        FieldUtils.writeField(serviceMock, "repository", repositoryMock, true);
        Response response = mock(Response.class);

        when(serviceMock.isDuplicate(any(Request.class))).thenReturn(false);
        when(responseMock.getId()).thenReturn(1L);
        when(serviceMock.findById(any(Long.class))).thenReturn(tableInfoMock);

        doNothing()
                .when(serviceMock)
                .updateEntity(any(BaseTableInfo.class), any(Request.class));

        when(repositoryMock.save(any(BaseTableInfo.class))).thenReturn(tableInfoMock);
        when(tableInfoMock.getResponse()).thenReturn(response);
        updateValueCallRealMethod();

        serviceMock.updateValue(updateRequestMock);

        verify(repositoryMock).save(any());
        verify(tableInfoMock).getResponse();
    }

    void updateValueCallRealMethod() {
        doCallRealMethod()
                .when(serviceMock)
                .updateValue(updateRequestMock);
    }
}
