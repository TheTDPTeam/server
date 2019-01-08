package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
    Config findByKey(String key);
}
