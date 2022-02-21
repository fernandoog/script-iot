package com.fernandoog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fernandoog.model.Script;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

}
