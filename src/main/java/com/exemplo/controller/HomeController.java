package com.exemplo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String exibirHome() {
        return "home";
    }

    @GetMapping("/clientes")
    public String redirecionarClientes() {
        return "redirect:/cliente/lista";
    }

    @GetMapping("/servicos")
    public String redirecionarServicos() {
        return "redirect:/servico/lista";
    }

    @GetMapping("/agendamentos")
    public String redirecionarAgendamentos() {
        return "redirect:/agendamento/lista";
    }

    @GetMapping("/agendar")
    public String redirecionarFormularioAgendamento() {
        return "redirect:/agendamento/formulario";
    }
}
