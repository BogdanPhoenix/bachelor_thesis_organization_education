package com.bachelor.thesis.organization_education.requests.insert.user;

import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Request for registration of the university administrator in the system.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationUserRequest extends RegistrationRequest {
}
