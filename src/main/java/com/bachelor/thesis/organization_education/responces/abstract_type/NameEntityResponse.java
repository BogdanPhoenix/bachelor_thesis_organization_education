package com.bachelor.thesis.organization_education.responces.abstract_type;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class NameEntityResponse extends Response {
    private String enName;
    private String uaName;

    protected static <T extends NameEntityResponseBuilder<?, ?>> @NonNull T initEmpty(T builder) {
        Response.initEmpty(builder)
                .uaName("")
                .enName("");
        return builder;
    }
}
