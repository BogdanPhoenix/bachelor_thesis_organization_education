package com.bachelor.thesis.organization_education.requests.update.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import com.bachelor.thesis.organization_education.annotations.ValidNameUser;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserUpdateRequest implements UpdateRequest {
    @Size(min = 2, max = 255, message = "First name should be between 2 and 255 characters")
    @ValidNameUser
    private String firstName;

    @Size(min = 2, max = 255, message = "Last name should be between 2 and 255 characters")
    @ValidNameUser
    private String lastName;

    @Setter(AccessLevel.PRIVATE)
    private FindRequest findRequest = new FindRequest() {};

    public boolean firstNameIsEmpty() {
        return firstName == null;
    }

    public boolean lastNameIsEmpty() {
        return lastName == null;
    }
}
