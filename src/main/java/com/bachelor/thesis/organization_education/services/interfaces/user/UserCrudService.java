package com.bachelor.thesis.organization_education.services.interfaces.user;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.dto.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.bachelor.thesis.organization_education.requests.user.UserRequest;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.services.interfaces.CrudService;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

@Transactional
public interface UserCrudService extends CrudService {
    /**
     * Creates a user in the database.
     * @param request user data for registration.
     * @return basic information about the user.
     * @throws DuplicateException if there is a user in the database with the data passed in the request.
     * @throws NullPointerException if null was passed to the request.
     */
    UserDetails createUser(@NonNull RegistrationRequest request) throws DuplicateException, NullPointerException;

    /**
     * Searches for a registered user in the database.
     * @param request user search data.
     * @return the essence of the registered user.
     * @throws NotFindEntityInDataBaseException if the registered user could not be found in the database.
     */
    User findAuthenticatedUser(@NonNull UserRequest request) throws NotFindEntityInDataBaseException;
}
