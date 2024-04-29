package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface ClassRecordingRepository extends JpaRepository<ClassRecording, UUID> {
    Optional<ClassRecording> findByMagazineAndClassTopic(GroupDiscipline discipline, String classTopic);
}
