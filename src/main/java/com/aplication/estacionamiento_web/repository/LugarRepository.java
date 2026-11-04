package com.aplication.estacionamiento_web.repository;

import com.aplication.estacionamiento_web.model.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Integer> {
}