package com.bachelor.thesis.organization_education.requests.user;

import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRequest extends Request {
    @NotNull
    @NotEmpty(message = "Email should not be empty")
    @Size(min = 3, max = 255, message = "Email should be between 3 and 255 characters")
    @Email(message = """
            The email address you provided: "%s" has not been validated. It must comply with the following rules:
                - The main body must contain only letters (uppercase or lowercase) of the Latin alphabet, numbers, or the symbols '_', '.', '+', or '-'.
                - The '@' symbol must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, or a dash.
                - The dot (.) in the domain must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, a dash or a period.
            Your email address should look like: "username@domain.domain_zone"
            """)
    private String username;

    @Override
    public boolean isEmpty() {
        return username.isBlank();
    }

    public static @NonNull UserRequest empty() {
        return builder()
                .username("")
                .build();
    }
}
