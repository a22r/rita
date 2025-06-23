package com.rita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rita.model.Info;

public interface InfoRepository extends JpaRepository<Info, Long> {}