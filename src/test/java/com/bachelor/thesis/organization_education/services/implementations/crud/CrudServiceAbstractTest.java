package com.bachelor.thesis.organization_education.services.implementations.crud;

import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.function.Consumer;
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
    private FindRequest findRequestMock;
    @Mock
    private BaseTableInfo tableInfoMock;

    @Nested
    @DisplayName("Test cases for addValue method")
    class AddValueTests {
        @Mock
        private Request requestMock;

        @Test
        @DisplayName("Checking for an exception when null was passed in the request to add data.")
        void testAddValueThrowsNullPointerException() {
            Request value = null;
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
        private static final Long ID = 1L;

        @Mock
        private UpdateRequest updateDataMock;

        @BeforeEach
        void init() {
            when(updateDataMock.getFindRequest()).thenReturn(findRequestMock);
            when(findRequestMock.skip()).thenReturn(false);
            doCallRealMethod()
                    .when(serviceMock)
                    .updateValue(ID, updateDataMock);
        }

        @Test
        @DisplayName("Check for an exception when the data update request contains data that already exists in the table.")
        void testUpdateValueThrowsDuplicateException() {
            when(serviceMock.isDuplicate(any(FindRequest.class), anyLong())).thenReturn(true);
            assertThrows(DuplicateException.class, () -> serviceMock.updateValue(ID, updateDataMock));
        }

        @Test
        @DisplayName("Check for an exception when the data update request contains data that is not in the table.")
        void testUpdateValueThrowsNotFindEntityInDataBaseException() {
            when(serviceMock.isDuplicate(any(FindRequest.class), anyLong())).thenReturn(false);
            when(serviceMock.findById(anyLong())).thenThrow(NotFindEntityInDataBaseException.class);

            assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.updateValue(ID, updateDataMock));
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
            FindRequest value = null;
            doCallRealMethodForAction(serviceMock::deleteValue, value);
            assertThrows(NullPointerException.class, () -> serviceMock.deleteValue(value));
        }

        @Test
        @DisplayName("Check for exceptions when the table cannot find the entity for the specified query.")
        void testDeleteValueThrowsNotFindEntityInDataBaseException() {
            when(serviceMock.getEntity(any(FindRequest.class))).thenThrow(NotFindEntityInDataBaseException.class);
            doCallRealMethodForAction(serviceMock::deleteValue, findRequestMock);
            assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.deleteValue(findRequestMock));
        }
}

    @Test
    @DisplayName("Check for an exception when the request to activate an entity failed to find the entity.")
    void testEnableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.getEntity(any(FindRequest.class))).thenThrow(NotFindEntityInDataBaseException.class);
        testEntityNotFoundAction(serviceMock::enable);
    }

    @Test
    @DisplayName("Check for an exception when the request to deactivate an entity failed to find the entity.")
    void testDisableEntityThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.getEntity(any(FindRequest.class))).thenThrow(NotFindEntityInDataBaseException.class);
        testEntityNotFoundAction(serviceMock::disable);
    }

    @Test
    @DisplayName("Check the exception when the entity return request did not find an entity.")
    void testGetValueThrowsNotFindEntityInDataBaseException() {
        when(serviceMock.findEntity(any(FindRequest.class))).thenThrow(NotFindEntityInDataBaseException.class);
        testEntityNotFoundAction(serviceMock::getValue);
    }

    private void testEntityNotFoundAction(Consumer<FindRequest> action) {
        doCallRealMethodForAction(action, findRequestMock);
        assertThrows(NotFindEntityInDataBaseException.class, () -> action.accept(findRequestMock));
    }

    private <T> void doCallRealMethodForAction(Consumer<T> action, T value) {
        doCallRealMethod().when(serviceMock);
        action.accept(value);
    }
}
