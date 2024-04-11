package com.bachelor.thesis.organization_education.requests.abstract_type;

import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;
import jakarta.persistence.MappedSuperclass;
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
public abstract class NameEntityRequest extends Request {
    @NotNull
    @ValidNameEntity
    @Size(max = 255)
    private String enName;
    @NotNull
    @ValidNameEntity
    @Size(max = 255)
    private String uaName;

    @Override
    public boolean isEmpty() {
        return enName.isBlank() || uaName.isBlank();
    }

    protected static <T extends NameEntityRequestBuilder<?, ?>> @NonNull T initEmpty(@NotNull T builder) {
        builder.enName("")
                .uaName("");
        return builder;
    }
}
