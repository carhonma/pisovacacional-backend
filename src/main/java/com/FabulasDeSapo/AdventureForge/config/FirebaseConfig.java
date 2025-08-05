package com.FabulasDeSapo.AdventureForge.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);
    private static final String ENV_KEY = "GOOGLE_CREDENTIALS";
    private static final String LOCAL_KEY_FILE = "serviceAccountKey.json";

    @PostConstruct
    public void initFirebase() {
        if (!FirebaseApp.getApps().isEmpty()) {
            log.debug("Firebase ya estaba inicializado; se omite.");
            return;
        }

        try (InputStream serviceAccount = resolveCredentialsStream()) {

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    // .setProjectId("adventureforge-fabulasdesapo") // opcional, ya lo deduce del JSON
                    .build();

            FirebaseApp.initializeApp(options);
            log.info("✅ Firebase inicializado correctamente");

        } catch (Exception ex) {
            // Interrumpir el arranque si no se puede inicializar
            throw new IllegalStateException("❌ No se pudo inicializar Firebase", ex);
        }
    }

    /**
     * Obtiene un InputStream con las credenciales:
     * 1️⃣  GOOGLE_CREDENTIALS  (variable de entorno, p.e. en Render)
     * 2️⃣  serviceAccountKey.json  en el class‑path (para desarrollo local)
     */
    private InputStream resolveCredentialsStream() throws Exception {
        String creds = System.getenv(ENV_KEY);

        if (creds != null && !creds.isBlank()) {
            log.debug("Usando credenciales de la variable de entorno {}", ENV_KEY);
            return new ByteArrayInputStream(creds.getBytes(StandardCharsets.UTF_8));
        }

        log.debug("Usando credenciales locales de {}", LOCAL_KEY_FILE);
        return new ClassPathResource(LOCAL_KEY_FILE).getInputStream();
    }
}
