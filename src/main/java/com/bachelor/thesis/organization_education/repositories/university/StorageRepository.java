package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Storage;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StorageRepository extends BaseTableInfoRepository<Storage> {
    List<Storage> findAllByUserIdAndClassRecordingAndFileName(UUID userId, ClassRecording classRecording, String fileName);
    List<Storage> findAllByUserId(UUID userId);
    Page<Storage> findAllByUserIdAndClassRecording(UUID userId, ClassRecording classRecording, Pageable pageable);
}
