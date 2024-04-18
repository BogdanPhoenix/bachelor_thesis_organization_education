package com.bachelor.thesis.organization_education.requests.update.user;

import com.bachelor.thesis.organization_education.annotations.ValidEmail;
import com.bachelor.thesis.organization_education.annotations.ValidNameUser;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserUpdateRequest implements UpdateRequest {
    @NotBlank(message = "The email should not be empty or contain only one space.")
    @Size(min = 3, max = 255, message = "Email should be between 3 and 255 characters")
    @ValidEmail
    private String username;

    @NotBlank(message = "The first name should not be empty or contain only one space.")
    @Size(min = 2, max = 255, message = "First name should be between 2 and 255 characters")
    @ValidNameUser
    private String firstName;

    @NotBlank(message = "The last name should not be empty")
    @Size(min = 2, max = 255, message = "Last name should be between 2 and 255 characters")
    @ValidNameUser
    private String lastName;

    @Setter(AccessLevel.PRIVATE)
    private FindRequest findRequest = new FindRequest() {
        @Override
        public boolean skip() {
            return true;
        }
    };
}
