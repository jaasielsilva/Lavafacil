package com.exemplo.controller;

import com.exemplo.model.Servico;
import com.exemplo.repository.ServicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    // Formulário para criar novo serviço
    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("servico", new Servico());
        model.addAttribute("servicos", servicoRepository.findAll());
        return "servico/formulario";
    }

    // Salvar novo serviço
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("servico") Servico servico, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "servico/formulario";
        }

        servicoRepository.save(servico);
        return "redirect:/servicos/listar?sucesso";
    }

    // Listar serviços
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("servicos", servicoRepository.findAll());
        return "servico/lista-servicos";
    }

    // Editar serviço existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        model.addAttribute("servico", servico);
        return "servico/formulario";
    }

    // Excluir serviço
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        servicoRepository.deleteById(id);
        return "redirect:/servicos/listar?excluido";
    }

    // ✅ Endpoint para buscar serviço por ID (usado no fetch JS)
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return servicoRepository.findById(id)
                .map(servico -> ResponseEntity.ok().body(servico))
                .orElse(ResponseEntity.notFound().build());
    }
}
