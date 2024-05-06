package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bachelor.thesis.organization_education.dto.Audience;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.Schedule;
import com.bachelor.thesis.organization_education.enums.DayWeek;
import com.bachelor.thesis.organization_education.enums.TypeClass;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.sql.Time;
import java.util.List;

@Repository
public interface ScheduleRepository extends BaseTableInfoRepository<Schedule> {
    List<Schedule> findAllByGroupDisciplineAndTypeClass(GroupDiscipline groupDiscipline, TypeClass typeClass);

    @Query("""
        SELECT s FROM Schedule s
        JOIN GroupDiscipline gd ON s.groupDiscipline = gd
        WHERE gd.lecturer = :lecturer
        AND s.dayWeek = :dayWeek
        AND ((s.startTime <= :startTime AND s.endTime >= :startTime)
        OR (s.startTime <= :endTime AND s.endTime >= :endTime))
    """)
    List<Schedule> findForMatchesByLecturer(
            @Param("lecturer") Lecturer lecturer,
            @Param("dayWeek") DayWeek dayWeek,
            @Param("startTime") Time startTime,
            @Param("endTime") Time endTime
    );

    @Query("""
        SELECT s FROM Schedule s
        WHERE s.audience = :audience
        AND s.dayWeek = :dayWeek
        AND ((s.startTime <= :startTime AND s.endTime >= :startTime)
        OR (s.startTime <= :endTime AND s.endTime >= :endTime))
    """)
    List<Schedule> findForMatchesByAuditory(
            @Param("audience") Audience audience,
            @Param("dayWeek") DayWeek dayWeek,
            @Param("startTime") Time startTime,
            @Param("endTime") Time endTime
    );
}
