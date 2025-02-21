package com.SpringSecurityPart1.SpringSecurityPart1.repositories;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

}
