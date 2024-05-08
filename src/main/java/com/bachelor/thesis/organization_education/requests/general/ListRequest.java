package com.bachelor.thesis.organization_education.requests.general;

import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import org.hibernate.validator.constraints.UniqueElements;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;

import java.util.Collection;

public record ListRequest<E>(@UniqueElements Collection<@Valid @ConvertGroup(to = InsertRequest.class) E> collection) {
}
