package com.codingshuttle.SecurityApp.SecurityApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.codingshuttle.SecurityApp.SecurityApplication.entities.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
