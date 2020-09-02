package com.sangbango.project.ui.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangbango.project.ui.entitymodel.AgendaEntity;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long>{
	AgendaEntity findAgendaById(Long id);
	List<AgendaEntity> findAllByOrderByCreatedDateDesc();
}
