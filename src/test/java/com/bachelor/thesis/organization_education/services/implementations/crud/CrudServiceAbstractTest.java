package com.bachelor.thesis.organization_education.services.implementations.crud;

import com.bachelor.thesis.organization_education.requests.update.UpdateData;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
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
    @Mock
    private FindRequest findRequestMock;
    @Mock
    private UpdateRequest updateRequestMock;
    @InjectMocks
    private UpdateData<UpdateRequest> updateDataMock;
    @Mock
    private BaseTableInfo tableInfoMock;

    @Nested
    @DisplayName("Test cases for addValue method")
    class AddValueTests {
        @Test
        @DisplayName("Checking for an exception when null was passed in the request to add data.")
        void testAddValueThrowsNullPointerException() {
            addValueCallRealMethod(null);
            assertThrows(NullPointerException.class, () -> serviceMock.addValue(null));
        }

        @Test
        @DisplayName("Checking for an exception when the request to add data contained data that was already in the table.")
        void testAddValueThrowsDuplicateException() {
            when(requestMock.getFindRequest()).thenReturn(findRequestMock);
            when(serviceMock.isDuplicate(any(FindRequest.class))).thenReturn(true);
            addValueCallRealMethod(requestMock);
            assertThrows(DuplicateException.class, () -> serviceMock.addValue(requestMock));
        }

        @Test
        @DisplayName("Checking for the correct addition of a new entity to the table")
        void testAddValueCorrect() throws Exception {
            FieldUtils.writeField(serviceMock, "repository", repositoryMock, true);

            when(repositoryMock.save(any(BaseTableInfo.class))).thenReturn(tableInfoMock);
            when(serviceMock.isDuplicate(any(FindRequest.class))).thenReturn(false);
            when(serviceMock.createEntity(requestMock)).thenReturn(tableInfoMock);
            addValueCallRealMethod(requestMock);

            serviceMock.addValue(requestMock);
            verify(repositoryMock).save(any());
        }

        void addValueCallRealMethod(Request request) {
            doCallRealMethod()
                    .when(serviceMock)
                    .addValue(request);
        }
    }

    @Nested
    @DisplayName("Test cases for updateValue method")
    class UpdateValueTests {
        @BeforeEach
        void init() {
            updateDataMock.setId(1L);
            when(updateRequestMock.getFindRequest()).thenReturn(findRequestMock);
            doCallRealMethod()
                    .when(serviceMock)
                    .updateValue(updateDataMock);
        }

        @Test
        @DisplayName("Check for an exception when the data update request contains data that already exists in the table.")
        void testUpdateValueThrowsDuplicateException() {
            when(serviceMock.isDuplicate(any(FindRequest.class), anyLong())).thenReturn(true);
            assertThrows(DuplicateException.class, () -> serviceMock.updateValue(updateDataMock));
        }

        @Test
        @DisplayName("Check for an exception when the data update request contains data that is not in the table.")
        void testUpdateValueThrowsNotFindEntityInDataBaseException() {
            when(serviceMock.isDuplicate(any(FindRequest.class), anyLong())).thenReturn(false);
            when(serviceMock.findById(anyLong())).thenThrow(NotFindEntityInDataBaseException.class);

            assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.updateValue(updateDataMock));
        }

        @Test
        @DisplayName("Checking for the correct update of the entity.")
        void testUpdateValueCorrect() throws IllegalAccessException {
            FieldUtils.writeField(serviceMock, "repository", repositoryMock, true);

            when(serviceMock.isDuplicate(any(FindRequest.class), anyLong())).thenReturn(false);
            when(serviceMock.findById(anyLong())).thenReturn(tableInfoMock);

            doNothing()
                    .when(serviceMock)
                    .updateEntity(any(BaseTableInfo.class), any(UpdateRequest.class));

            when(repositoryMock.save(any(BaseTableInfo.class))).thenReturn(tableInfoMock);

            serviceMock.updateValue(updateDataMock);
            verify(repositoryMock).save(any());
        }
    }

    @Nested
    @DisplayName("Test cases for deleteValue method")
    class DeleteValueTests {
        @Test
        @DisplayName("Checking for an exception when null was passed in the request to delete data.")
        void testDeleteValueThrowsNullPointerException() {
            deleteValueCallRealMethod(null);
            assertThrows(NullPointerException.class, () -> serviceMock.deleteValue(null));
        }

        @Test
        @DisplayName("Check for exceptions when the table cannot find the entity for the specified query.")
        void testDeleteValueThrowsNotFindEntityInDataBaseException() {
            when(serviceMock.getEntity(any(FindRequest.class))).thenThrow(NotFindEntityInDataBaseException.class);
            deleteValueCallRealMethod(findRequestMock);
            assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.deleteValue(findRequestMock));
        }

        void deleteValueCallRealMethod(FindRequest request) {
            doCallRealMethod()
                    .when(serviceMock)
                    .deleteValue(request);
        }
    }

    @Test
    @DisplayName("Check for an exception when the request to activate an entity failed to find the entity.")
    void testEnableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.getEntity(findRequestMock)).thenThrow(NotFindEntityInDataBaseException.class);

        doCallRealMethod()
                .when(serviceMock)
                .enable(findRequestMock);

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.enable(findRequestMock));
    }

    @Test
    @DisplayName("Check for an exception when the request to deactivate an entity failed to find the entity.")
    void testDisableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.getEntity(findRequestMock)).thenThrow(NotFindEntityInDataBaseException.class);

        doCallRealMethod()
                .when(serviceMock)
                .disable(findRequestMock);

        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.disable(findRequestMock));
    }
}
