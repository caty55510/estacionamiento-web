package com.aplication.estacionamiento_web.repository;

import com.aplication.estacionamiento_web.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findByIdTicketAndActivo(int idTicket, boolean activo);
}
