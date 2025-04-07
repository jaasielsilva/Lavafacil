package com.exemplo.controller;

import com.exemplo.model.Agendamento;
import com.exemplo.model.Veiculo;
import com.exemplo.repository.ClienteRepository;
import com.exemplo.repository.ServicoRepository;
import com.exemplo.repository.VeiculoRepository;
import com.exemplo.service.AgendamentoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agendamento")
public class AgendamentoWebController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("/formulario")
    public String exibirFormulario(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("servicos", servicoRepository.findAll());
        model.addAttribute("agendamento", new Agendamento());
        return "agendamento/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Agendamento agendamento) {
        agendamentoService.criar(agendamento);
        return "redirect:/agendamento/formulario?sucesso";
    }

    @GetMapping("/lista")
    public String listarAgendamentos(Model model) {
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/lista";
    }

    // ✅ Endpoint para carregar veículos por cliente (chamado via JavaScript)
    @GetMapping("/veiculos-do-cliente/{clienteId}")
    @ResponseBody
    public List<Veiculo> buscarVeiculosPorCliente(@PathVariable Long clienteId) {
        return veiculoRepository.findByClienteId(clienteId);
    }

}
