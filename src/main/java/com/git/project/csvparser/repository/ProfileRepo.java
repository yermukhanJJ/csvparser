package com.git.project.csvparser.repository;

import com.git.project.csvparser.entity.ProfileTab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<ProfileTab, Long> {

    Optional<ProfileTab> findByItn(String itn);

    Boolean existsByItn(String itn);

    void deleteByItn(String itn);

}
