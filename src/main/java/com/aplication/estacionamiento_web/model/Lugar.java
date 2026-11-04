package com.aplication.estacionamiento_web.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lugares")
public class Lugar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLugar;
    
    @Column(nullable = false, unique = true)
    private String codigo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLugar estado;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipoPermitido;

    public Lugar() {
        this.estado = EstadoLugar.LIBRE;
    }

    public Lugar(String codigo, TipoVehiculo tipoPermitido) {
        this.codigo = codigo;
        this.tipoPermitido = tipoPermitido;
        this.estado = EstadoLugar.LIBRE;
    }

    public boolean estaDisponible() { return this.estado == EstadoLugar.LIBRE; }
    public void ocupar() { this.estado = EstadoLugar.OCUPADO; }
    public void liberar() { this.estado = EstadoLugar.LIBRE; }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public EstadoLugar getEstado() {
        return estado;
    }

    public void setEstado(EstadoLugar estado) {
        this.estado = estado;
    }

    public TipoVehiculo getTipoPermitido() {
        return tipoPermitido;
    }

    public void setTipoPermitido(TipoVehiculo tipoPermitido) {
        this.tipoPermitido = tipoPermitido;
    }
}
    