package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.UUID;

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

    @Query("""
        SELECT s FROM Schedule s
        JOIN Audience a ON s.audience = a
        JOIN University u ON a.university = u
        WHERE u.adminId = :adminId AND s.enabled = TRUE
    """)
    Page<Schedule> findAllByUniversityAdmin(UUID adminId, Pageable pageable);

    @Query("""
        SELECT s FROM Schedule s
        JOIN GroupDiscipline gd ON s.groupDiscipline = gd
        JOIN Lecturer l ON gd.lecturer = l
        WHERE l.id = :lecturerId AND s.enabled = TRUE
    """)
    Page<Schedule> findAllByLecturer(UUID lecturerId, Pageable pageable);

    @Query("""
        SELECT s FROM Schedule s
        JOIN GroupDiscipline gd ON s.groupDiscipline = gd
        JOIN Student st ON st.group = gd.group
        WHERE st.id = :studentId AND s.enabled = TRUE
    """)
    Page<Schedule> findAllByStudent(UUID studentId, Pageable pageable);
}
