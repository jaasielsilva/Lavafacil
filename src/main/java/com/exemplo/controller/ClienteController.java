package com.exemplo.controller;

import com.exemplo.model.Agendamento;
import com.exemplo.model.Cliente;
import com.exemplo.model.Veiculo;
import com.exemplo.repository.ClienteRepository;
import com.exemplo.repository.ServicoRepository;
import com.exemplo.repository.VeiculoRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    // ✅ Formulário de novo cliente com pelo menos 1 veículo
    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        Cliente cliente = new Cliente();
        cliente.getVeiculos().add(new Veiculo());
        model.addAttribute("cliente", cliente);
        return "cliente/formulario"; // View: templates/cliente/formulario.html
    }

    // ✅ Tela de agendamento, listando clientes e veículos
    @GetMapping("/agendar")
    public String novoAgendamento(Model model) {
        model.addAttribute("agendamento", new Agendamento());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        model.addAttribute("servicos", servicoRepository.findAll());

        return "agendamento/formulario";
    }

    // ✅ Salvar cliente com veículos vinculados
    @PostMapping("/salvar")
public String salvarCliente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model) {
    if (result.hasErrors()) {
        model.addAttribute("cliente", cliente);
        return "cliente/formulario";
    }

    // Essa linha é ESSENCIAL para associar corretamente os veículos ao cliente
    cliente.getVeiculos().forEach(v -> v.setCliente(cliente));
    
    clienteRepository.save(cliente);

    return "redirect:/"; // ou para onde quiser
}


    // ✅ Editar cliente existente
    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: ID = " + id));
        model.addAttribute("cliente", cliente);
        return "cliente/formulario"; // reutiliza o mesmo formulário para editar
    }

    // ✅ Excluir cliente
    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable("id") Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado para exclusão: ID = " + id);
        }

        clienteRepository.deleteById(id);
        return "redirect:/clientes/lista?excluido";
    }

    // ✅ Listar clientes
    @GetMapping("/lista")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "cliente/lista";
    }
}
