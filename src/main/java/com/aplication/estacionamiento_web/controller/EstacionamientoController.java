package com.aplication.estacionamiento_web.controller;

import com.aplication.estacionamiento_web.model.Ticket;
import com.aplication.estacionamiento_web.model.TipoVehiculo;
import com.aplication.estacionamiento_web.service.EstacionamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EstacionamientoController {

    @Autowired
    private EstacionamientoService service;

    @GetMapping("/")
    public String verPaginaPrincipal(Model model) {
        model.addAttribute("lugares", service.obtenerLugares());
        model.addAttribute("precioHora", service.getPrecioHora());
        return "index";
    }

    @PostMapping("/ingreso")
    public String registrarIngreso(@RequestParam String patente, 
                                   @RequestParam TipoVehiculo tipoVehiculo, 
                                   RedirectAttributes attributes) {
        try {
            Ticket ticket = service.registrarIngreso(patente, tipoVehiculo);
            attributes.addFlashAttribute("mensajeExito", "¡Ingreso exitoso! Ticket ID: " + ticket.getIdTicket() + " - Lugar: " + ticket.getLugarAsignado().getCodigo());
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/salida")
    public String registrarSalida(@RequestParam int idTicket, RedirectAttributes attributes) {
        try {
            Ticket ticket = service.registrarSalida(idTicket);
            attributes.addFlashAttribute("mensajeExito", "Salida registrada. Total a abonar: $" + ticket.getMontoTotal());
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/lugar/agregar")
    public String agregarLugar(@RequestParam String codigo, 
                               @RequestParam TipoVehiculo tipoPermitido, 
                               RedirectAttributes attributes) {
        try {
            service.agregarLugar(codigo, tipoPermitido);
            attributes.addFlashAttribute("mensajeExito", "¡Lugar " + codigo + " creado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/lugar/eliminar")
    public String eliminarLugar(@RequestParam int idLugar, RedirectAttributes attributes) {
        try {
            service.eliminarLugar(idLugar);
            attributes.addFlashAttribute("mensajeExito", "¡Lugar eliminado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/precio/actualizar")
    public String actualizarPrecio(@RequestParam double nuevoPrecio, 
                                   @RequestParam String password, 
                                   RedirectAttributes attributes) {
        try {
            service.actualizarPrecio(password, nuevoPrecio);
            attributes.addFlashAttribute("mensajeExito", "¡Tarifa actualizada a $" + nuevoPrecio + " con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/";
    }
}

