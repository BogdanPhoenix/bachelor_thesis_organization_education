package com.bachelor.thesis.organization_education.requests.general.abstracts;

import com.bachelor.thesis.organization_education.annotations.ValidTimeRange;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

import java.sql.Time;

/**
 * An interface representing a time range, defining methods to retrieve the start and end times.
 * Implementations of this interface should provide functionality to represent a specific period of time.
 */
@ValidTimeRange(groups = {InsertRequest.class, UpdateRequest.class})
public interface TimeRange {
    Time getStartTime();
    Time getEndTime();
}
