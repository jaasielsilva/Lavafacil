package com.exemplo.service;

import com.exemplo.model.Servico;
import com.exemplo.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;

    public Servico salvar(Servico servico) {
        return repository.save(servico);
    }

    public List<Servico> listarTodos() {
        return repository.findAll();
    }

    public Optional<Servico> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}