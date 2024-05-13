package com.bachelor.thesis.organization_education.repositories.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.UniversityGroup;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupDisciplineRepository extends BaseTableInfoRepository<GroupDiscipline> {
    List<GroupDiscipline> findAllByGroupAndDiscipline(UniversityGroup group, AcademicDiscipline discipline);

    @Query("SELECT gb FROM GroupDiscipline gb WHERE gb.enabled = TRUE")
    Page<GroupDiscipline> findAllActive(@NonNull Pageable pageable);

    @Query("SELECT gb FROM GroupDiscipline gb WHERE gb.enabled = TRUE AND gb.lecturer = :lecturer")
    Page<GroupDiscipline> findAllByLecturer(
            @Param("lecturer") Lecturer lecturer,
            Pageable pageable
    );

    @Query("""
        SELECT gb FROM GroupDiscipline gb
        JOIN UniversityGroup ug ON gb.group = ug
        JOIN Faculty f ON ug.faculty = f
        JOIN University u ON f.university = u
        WHERE u.adminId = :adminId AND gb.enabled = TRUE
    """)
    Page<GroupDiscipline> findAll(UUID adminId, Pageable pageable);
}
