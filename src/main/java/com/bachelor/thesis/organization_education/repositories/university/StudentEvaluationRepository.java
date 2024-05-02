package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Student;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.dto.StudentEvaluation;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;

@Repository
public interface StudentEvaluationRepository extends BaseTableInfoRepository<StudentEvaluation> {
    Page<StudentEvaluation> findAllByClassRecording(ClassRecording classRecording, Pageable pageable);
    Page<StudentEvaluation> findAllByStudent(Student student, Pageable pageable);
    List<StudentEvaluation> findByStudentAndClassRecording(Student student, ClassRecording classRecording);
}
