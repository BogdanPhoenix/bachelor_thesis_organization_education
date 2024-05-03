package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.Schedule;
import com.bachelor.thesis.organization_education.enums.TypeClass;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;

@Repository
public interface ScheduleRepository extends BaseTableInfoRepository<Schedule> {
    List<Schedule> findAllByGroupDisciplineAndLecturerAndTypeClass(GroupDiscipline groupDiscipline, Lecturer lecturer, TypeClass typeClass);
}
