package com.zooSabana.demo.mensajeria.logica;

import com.zooSabana.demo.mensajeria.productores.ProductorAuditoria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PublicacionService {

    private final ProductorAuditoria productorAuditoria;

    public PublicacionService(ProductorAuditoria productorAuditoria) {
        this.productorAuditoria = productorAuditoria;
    }

    public void manejarPublicacion() {
        System.out.println("Ejecutando publicación (manual o automática)...");
        productorAuditoria.publicarAnimalesSinRevision();
    }

    @Scheduled(cron = "0 */4 * * * *") // Cada 4 minutos para ver fácilmente su ejecución
    public void manejarPublicacionAutomatica() {
        System.out.println("Ejecutando tarea programada de publicación...");
        manejarPublicacion();
    }
}
