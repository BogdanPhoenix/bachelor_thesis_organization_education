package com.bachelor.thesis.organization_education.repositories.user;

import com.bachelor.thesis.organization_education.dto.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByFirstNameAndLastName(String firstName, String lastName);
}
