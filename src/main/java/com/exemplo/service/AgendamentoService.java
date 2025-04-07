package com.exemplo.service;

import com.exemplo.model.Agendamento;
import com.exemplo.repository.AgendamentoRepository;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private static final String SPREADSHEET_ID = "1LDXM0c3eHPVE1xW2NWCMUwP-E4RC4ClhDbW3df1fCPg";
    private static final String RANGE = "Agendamentos!A:F";

    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Sheets sheetsService;

    public Agendamento criar(Agendamento agendamento) {
        try {
            formatarTelefone(agendamento);
            Agendamento salvo = repository.save(agendamento);

            enviarEmailConfirmacao(salvo);
            salvarNaPlanilha(salvo);

            return salvo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar agendamento", e);
        }
    }

    private void formatarTelefone(Agendamento agendamento) {
        String telefone = agendamento.getCliente().getTelefone().replaceAll("\\D", "");
        if (telefone.length() == 11) {
            String formatado = String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 7),
                    telefone.substring(7));
            agendamento.getCliente().setTelefone(formatado);
        }
    }

    private void enviarEmailConfirmacao(Agendamento agendamento) throws MessagingException, jakarta.mail.MessagingException {
        String assunto = "✅ Confirmação de Agendamento - LavaFácil";

        String corpoHtml = """
<!DOCTYPE html>
<html lang='pt-BR'>
<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>Confirmação de Agendamento</title>
</head>
<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f5f5f5;'>
    <table align='center' cellpadding='0' cellspacing='0' width='100%%' style='padding: 20px 0;'>
        <tr>
            <td align='center'>
                <table cellpadding='0' cellspacing='0' width='600' style='background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 5px rgba(0,0,0,0.1);'>
                    <tr>
                        <td style='text-align: center; padding-bottom: 20px;'>
                            <h2 style='color: #28a745; margin: 0;'>Olá, %s! ✨</h2>
                            <p style='font-size: 16px; color: #333;'>Seu agendamento foi confirmado com sucesso!</p>
                        </td>
                    </tr>
                    <tr><td>
                        <table width='100%%' cellpadding='10' cellspacing='0' style='font-size: 15px; color: #333;'>
                            <tr>
                                <td width='30%%'><strong>🧼 Serviço:</strong></td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td><strong>📅 Data:</strong></td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td><strong>⏰ Hora:</strong></td>
                                <td>%s</td>
                            </tr>
                        </table>
                    </td></tr>
                    <tr><td style='padding-top: 20px;'>
                        <p style='font-size: 15px; color: #333;'>
                            <strong>📍 Endereço da Loja:</strong><br>
                            Loja LavaFácil<br>Rua das Flores, 123 - Centro, São Paulo/SP
                        </p>
                    </td></tr>
                    <tr>
                        <td style='text-align: center; padding-top: 30px; font-size: 13px; color: #777;'>
                            <hr style='border: none; border-top: 1px solid #eee;' />
                            <p>Obrigado por escolher a LavaFácil!<br>Equipe LavaFácil 🚗💦</p>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>
""".formatted(
                agendamento.getCliente().getNome(),
                agendamento.getServico().getNome(),
                agendamento.getData(),
                agendamento.getHora()
        );

        emailService.enviarConfirmacao(agendamento.getCliente().getEmail(), assunto, corpoHtml);
    }

    private void salvarNaPlanilha(Agendamento agendamento) {
        try {
            ValueRange appendBody = new ValueRange()
                    .setValues(Arrays.asList(
                            Arrays.asList(
                                    safe(agendamento.getCliente().getNome()),
                                    safe(agendamento.getCliente().getEmail()),
                                    safe(agendamento.getCliente().getTelefone()),
                                    safe(agendamento.getServico().getNome()),
                                    agendamento.getData() != null ? agendamento.getData().toString() : "",
                                    agendamento.getHora() != null ? agendamento.getHora().toString() : ""
                            )
                    ));

            sheetsService.spreadsheets().values()
                    .append(SPREADSHEET_ID, RANGE, appendBody)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
        } catch (IOException e) {
            System.err.println("Erro ao salvar na planilha: " + e.getMessage());
        }
    }

    private String safe(String valor) {
        return valor != null ? valor : "";
    }

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
