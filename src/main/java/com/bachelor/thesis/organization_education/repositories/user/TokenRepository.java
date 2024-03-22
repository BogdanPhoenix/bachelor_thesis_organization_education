package com.bachelor.thesis.organization_education.repositories.user;

import com.bachelor.thesis.organization_education.dto.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join User r\s
      on t.user.id = r.id\s
      where r.username = :username and (t.expired = false and t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(String username);
    Optional<Token> findByAccessToken(String accessToken);
}