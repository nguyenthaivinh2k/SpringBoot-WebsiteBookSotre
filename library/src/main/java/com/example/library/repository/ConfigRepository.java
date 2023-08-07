package com.example.library.repository;

import com.example.library.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository  extends JpaRepository<Config, Long> {
}
