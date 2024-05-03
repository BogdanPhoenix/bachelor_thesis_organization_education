package com.bachelor.thesis.organization_education.requests.general.abstracts;

import com.bachelor.thesis.organization_education.annotations.ValidTimeRange;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

/**
 * An interface representing a range of years.
 * Implementations of this interface should provide methods to retrieve the start and end years of the range.
 */
@ValidTimeRange(groups = {InsertRequest.class, UpdateRequest.class})
public interface YearRange {
    short getYearStart();
    short getYearEnd();
}
