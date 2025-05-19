package com.exemplo.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Collections;

@Configuration
public class GoogleSheetsConfig {

    @Bean
    public Sheets sheetsService() throws Exception {
        InputStream inputStream = new ClassPathResource("/credenciais.json").getInputStream();

        GoogleCredential credential = GoogleCredential
                .fromStream(inputStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                credential.getTransport(),
                credential.getJsonFactory(),
                credential
        )
        .setApplicationName("LavaFácil Agendamentos")
        .build();
    }
}
