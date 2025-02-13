package com.example.UserRegistrationSystem.dao;

import com.example.UserRegistrationSystem.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    @Query("""
select t from Token t inner join User u on t.user.id = u.id
where t.user.id = :userId
""")
    List<Token> findAllAccessTokensByUser(@Param("userId") UUID userId);

    Optional<Token> findByAccessToken(String token);
}
