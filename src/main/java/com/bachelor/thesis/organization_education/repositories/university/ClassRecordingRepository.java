package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;

@Repository
public interface ClassRecordingRepository extends BaseTableInfoRepository<ClassRecording> {
    List<ClassRecording> findAllByMagazineAndClassTopic(GroupDiscipline discipline, String classTopic);
}
