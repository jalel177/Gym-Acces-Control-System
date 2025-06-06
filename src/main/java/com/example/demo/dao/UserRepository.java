package com.example.demo.dao;

import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    @Query("SELECT u from  User u  WHERE u.username=?1")
    User finduserByUsername (String username) ;
    @Query(value = "select * from users u where u.username like :cle%", nativeQuery = true)
    List<User> getUserSW(@Param("cle") String un);
    @Query(value = "select * from users u where u.email like %:domain%", nativeQuery = true)
    List<User> getUsersByEmail(@Param("domain") String un);
    @Modifying
    @Query(value = "DELETE FROM session_users WHERE cours_id = :coursid", nativeQuery = true)
    @Transactional
    void clearSessionRelationships(@Param("coursid") Long coursid);

    User findByEmail(String email);

}

