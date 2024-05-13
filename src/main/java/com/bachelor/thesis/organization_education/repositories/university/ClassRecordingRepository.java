package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassRecordingRepository extends BaseTableInfoRepository<ClassRecording> {
    List<ClassRecording> findAllByMagazineAndClassTopic(GroupDiscipline discipline, String classTopic);

    @Query("""
        SELECT cr FROM ClassRecording cr
        JOIN GroupDiscipline gb ON cr.magazine = gb
        WHERE gb.lecturer.id = :lecturerId AND cr.enabled = TRUE
    """)
    Page<ClassRecording> findAll(UUID lecturerId, Pageable pageable);
}
