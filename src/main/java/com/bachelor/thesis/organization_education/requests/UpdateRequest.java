package com.bachelor.thesis.organization_education.requests;

import com.bachelor.thesis.organization_education.annotations.ValidRequestEmpty;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ValidRequestEmpty
public class UpdateRequest<T extends Response, R extends Request> extends Request {
    @NotNull
    private T oldValue;
    @NotNull
    private R newValue;

    @Override
    public boolean isEmpty() {
        return oldValue.isEmpty() || newValue.isEmpty();
    }
}
