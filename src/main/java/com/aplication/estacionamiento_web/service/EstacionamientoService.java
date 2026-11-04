package com.aplication.estacionamiento_web.service;

import com.aplication.estacionamiento_web.model.*;
import com.aplication.estacionamiento_web.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionamientoService {

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private double precioHora = 600.0;

    @PostConstruct
    public void init() {
        if (lugarRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                lugarRepository.save(new Lugar(String.format("L-%02d", i), TipoVehiculo.AUTO));
            }
            for (int i = 11; i <= 15; i++) {
                lugarRepository.save(new Lugar(String.format("L-%02d", i), TipoVehiculo.MOTO));
            }
            for (int i = 16; i <= 20; i++) {
                lugarRepository.save(new Lugar(String.format("L-%02d", i), TipoVehiculo.CAMIONETA));
            }
        }
    }

    public List<Lugar> obtenerLugares() {
        return lugarRepository.findAll();
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void actualizarPrecio(String password, double nuevoPrecio) {
        if (!"ccv".equals(password)) {
            throw new RuntimeException("Contraseña de administrador incorrecta.");
        }
        if (nuevoPrecio <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0.");
        }
        this.precioHora = nuevoPrecio;
    }

    public Lugar agregarLugar(String codigo, TipoVehiculo tipoPermitido) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new RuntimeException("El código no puede estar vacío.");
        }
        Lugar nuevo = new Lugar(codigo, tipoPermitido);
        return lugarRepository.save(nuevo);
    }

    public void eliminarLugar(int idLugar) {
        Lugar lugar = lugarRepository.findById(idLugar)
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado."));

        if (!lugar.estaDisponible()) {
            throw new RuntimeException("No se puede eliminar un lugar que está OCUPADO.");
        }

        lugarRepository.delete(lugar);
    }

    public Ticket registrarIngreso(String patente, TipoVehiculo tipoVehiculo) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new RuntimeException("La patente es obligatoria.");
        }

        List<Lugar> lugares = lugarRepository.findAll();

        Lugar lugarLibre = lugares.stream()
                .filter(l -> l.estaDisponible() && l.getTipoPermitido() == tipoVehiculo)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay lugares disponibles para este tipo de vehículo."));

        lugarLibre.ocupar();
        lugarRepository.save(lugarLibre);

        Vehiculo vehiculo = new Vehiculo(patente, tipoVehiculo);
        vehiculoRepository.save(vehiculo);

        Ticket ticket = new Ticket(vehiculo, lugarLibre);
        return ticketRepository.save(ticket);
    }

    public Ticket registrarSalida(int idTicket) {
        Ticket ticket = ticketRepository.findByIdTicketAndActivo(idTicket, true)
                .orElseThrow(() -> new RuntimeException("Ticket activo no encontrado con ID: " + idTicket));

        ticket.registrarSalida(precioHora);
        
        Lugar lugar = ticket.getLugarAsignado();
        lugar.liberar();
        lugarRepository.save(lugar);

        return ticketRepository.save(ticket);
    }
}