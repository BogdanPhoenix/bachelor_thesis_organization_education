package com.bachelor.thesis.organization_education.requests.general.abstracts;

import com.bachelor.thesis.organization_education.annotations.ValidYearRange;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

/**
 * An interface representing a range of years.
 * Implementations of this interface should provide methods to retrieve the start and end years of the range.
 */
@ValidYearRange(groups = {InsertRequest.class, UpdateRequest.class})
public interface YearRange {
    Short getYearStart();
    Short getYearEnd();
}
