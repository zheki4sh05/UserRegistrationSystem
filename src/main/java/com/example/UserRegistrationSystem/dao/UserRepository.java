package com.example.UserRegistrationSystem.dao;

import com.example.UserRegistrationSystem.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""

select u 
from User u 
where u.email= :email
""")
    Optional<User> findByEmail(@Param("email") String data);

    @Query("""

select u 
from User u 
where u.email=:data
""")
    User getByEmailOrLogin(@Param("data") String email);


}
