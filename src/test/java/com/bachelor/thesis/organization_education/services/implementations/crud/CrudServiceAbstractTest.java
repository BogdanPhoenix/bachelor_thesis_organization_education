package com.bachelor.thesis.organization_education.services.implementations.crud;

import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.boot.test.context.SpringBootTest;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.UUID;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CrudServiceAbstractTest {
    @Mock
    private BaseTableInfoRepository<BaseTableInfo> repositoryMock;
    @Mock
    private CrudServiceAbstract<BaseTableInfo, BaseTableInfoRepository<BaseTableInfo>> serviceMock;
    @Mock
    private FindRequest findRequestMock;
    @Mock
    private BaseTableInfo tableInfoMock;

    @Nested
    @DisplayName("Test cases for addValue method")
    class AddValueTests {
        @Mock
        private InsertRequest requestMock;

        @Test
        @DisplayName("Checking for an exception when null was passed in the request to add data.")
        void testAddValueThrowsNullPointerException() {
            InsertRequest value = null;
            doCallRealMethodForAction(serviceMock::addValue, value);
            assertThrows(NullPointerException.class, () -> serviceMock.addValue(value));
        }

        @Test
        @DisplayName("Checking for an exception when the request to add data contained data that was already in the table.")
        void testAddValueThrowsDuplicateException() {
            when(requestMock.getFindRequest()).thenReturn(findRequestMock);
            when(serviceMock.isDuplicate(any(FindRequest.class))).thenReturn(true);
            doCallRealMethodForAction(serviceMock::addValue, requestMock);
            assertThrows(DuplicateException.class, () -> serviceMock.addValue(requestMock));
        }

        @Test
        @DisplayName("Checking for the correct addition of a new entity to the table")
        void testAddValueCorrect() throws Exception {
            FieldUtils.writeField(serviceMock, "repository", repositoryMock, true);

            when(repositoryMock.save(any(BaseTableInfo.class))).thenReturn(tableInfoMock);
            when(serviceMock.isDuplicate(any(FindRequest.class))).thenReturn(false);
            when(serviceMock.createEntity(requestMock)).thenReturn(tableInfoMock);
            doCallRealMethodForAction(serviceMock::addValue, requestMock);

            serviceMock.addValue(requestMock);
            verify(repositoryMock).save(any());
        }
    }

    @Nested
    @DisplayName("Test cases for updateValue method")
    class UpdateValueTests {
        private static final UUID ID = UUID.randomUUID();

        @Mock
        private UpdateRequest updateDataMock;

        @BeforeEach
        void init() {
            when(updateDataMock.getFindRequest()).thenReturn(findRequestMock);
            doCallRealMethod()
                    .when(serviceMock)
                    .updateValue(ID, updateDataMock);
        }

        @Test
        @DisplayName("Check for an exception when the data update request contains data that already exists in the table.")
        void testUpdateValueThrowsDuplicateException() {
            when(serviceMock.isDuplicate(any(FindRequest.class), any(UUID.class))).thenReturn(true);
            assertThrows(DuplicateException.class, () -> serviceMock.updateValue(ID, updateDataMock));
        }

        @Test
        @DisplayName("Check for an exception when the data update request contains data that is not in the table.")
        void testUpdateValueThrowsNotFindEntityInDataBaseException() {
            when(serviceMock.isDuplicate(any(FindRequest.class), any(UUID.class))).thenReturn(false);
            when(serviceMock.findValueById(any(UUID.class))).thenThrow(NotFindEntityInDataBaseException.class);

            assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.updateValue(ID, updateDataMock));
        }

        @Test
        @DisplayName("Checking for the correct update of the entity.")
        void testUpdateValueCorrect() throws IllegalAccessException {
            FieldUtils.writeField(serviceMock, "repository", repositoryMock, true);

            when(serviceMock.isDuplicate(any(FindRequest.class), any(UUID.class))).thenReturn(false);
            when(serviceMock.findValueById(any(UUID.class))).thenReturn(tableInfoMock);

            doNothing()
                    .when(serviceMock)
                    .updateEntity(any(BaseTableInfo.class), any(UpdateRequest.class));

            doCallRealMethod().when(serviceMock)
                    .updateValue(any(BaseTableInfo.class), any(UpdateRequest.class));

            when(repositoryMock.save(any(BaseTableInfo.class))).thenReturn(tableInfoMock);

            serviceMock.updateValue(ID, updateDataMock);
            verify(repositoryMock).save(any());
        }
    }

    @Nested
    @DisplayName("Test cases for deleteValue method")
    class DeleteValueTests {
        @Test
        @DisplayName("Checking for an exception when null was passed in the request to delete data.")
        void testDeleteValueThrowsNullPointerException() {
            doCallRealMethodForAction(serviceMock::deleteValue, null);
            assertThrows(NullPointerException.class, () -> serviceMock.deleteValue(null));
        }
    }


    @Test
    @DisplayName("Check the exception when the entity return request did not find an entity.")
    void testGetValueThrowsNotFindEntityByRequestInDataBaseException() {
        when(serviceMock.findAllEntitiesByRequest(any(FindRequest.class))).thenThrow(NotFindEntityInDataBaseException.class);
        doCallRealMethodForAction(serviceMock::getValue, findRequestMock);
        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.getValue(findRequestMock));
    }

    private void testEntityNotFoundAction(Consumer<UUID> action) {
        var ID = UUID.randomUUID();
        doCallRealMethod()
                .when(serviceMock)
                .updateEnabled(any(UUID.class), any(Boolean.class));

        when(serviceMock.findEntityById(any(UUID.class)))
                .thenThrow(NotFindEntityInDataBaseException.class);
        doCallRealMethodForAction(action, ID);

        assertThrows(NotFindEntityInDataBaseException.class, () -> action.accept(ID));
    }

    private <T> void doCallRealMethodForAction(Consumer<T> action, T value) {
        doCallRealMethod().when(serviceMock);
        action.accept(value);
    }
}
