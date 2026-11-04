package com.aplication.estacionamiento_web.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.Duration;

@Entity
@Table(name = "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTicket;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "lugar_id", nullable = false)
    private Lugar lugarAsignado;

    @Column(nullable = false)
    private LocalDateTime horaIngreso;

    private LocalDateTime horaSalida;

    private double montoTotal;

    @Column(nullable = false)
    private boolean activo;

    public Ticket() {}

    public Ticket(Vehiculo vehiculo, Lugar lugarAsignado) {
        this.vehiculo = vehiculo;
        this.lugarAsignado = lugarAsignado;
        this.horaIngreso = LocalDateTime.now();
        this.activo = true;
    }

    public void registrarSalida(double precioHora) {
        this.horaSalida = LocalDateTime.now();
        this.activo = false;
        long minutos = Duration.between(horaIngreso, horaSalida).toMinutes();
        if (minutos < 1) minutos = 1;
        double horas = minutos / 60.0;
        this.montoTotal = Math.ceil(horas * precioHora);
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Lugar getLugarAsignado() {
        return lugarAsignado;
    }

    public void setLugarAsignado(Lugar lugarAsignado) {
        this.lugarAsignado = lugarAsignado;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    
}