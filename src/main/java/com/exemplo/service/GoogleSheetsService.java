package com.exemplo.service;

import com.exemplo.model.Agendamento;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class GoogleSheetsService {

    private static final String SPREADSHEET_ID = "1FXnR1oPYWkM56-84fZ9_GwmHHUkWITBqGT-02weJKoc";
    private static final String RANGE = "Agendamentos!A1"; // Começa na linha 1 para pular o cabeçalho

    @Autowired
    private Sheets sheets;

    public void registrarAgendamento(Agendamento agendamento) throws IOException {
        ValueRange appendBody = new ValueRange()
            .setValues(Arrays.asList(
                Arrays.asList(
                    agendamento.getNomeCliente(),   // Coluna A
                    agendamento.getEmail(),         // Coluna B
                    agendamento.getTelefone(),      // Coluna C
                    agendamento.getServico().getNome(), // Coluna D
                    agendamento.getData(),          // Coluna E
                    agendamento.getHora()           // Coluna F
                )
            ));

        sheets.spreadsheets().values()
            .append(SPREADSHEET_ID, RANGE, appendBody)
            .setValueInputOption("USER_ENTERED")
            .execute();
    }
}