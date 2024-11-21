package com.zooSabana.demo.mensajeria.logica;

import com.zooSabana.demo.mensajeria.productores.ProductorAuditoria;
import org.springframework.stereotype.Service;

@Service
public class PublicacionService {

    private final ProductorAuditoria productorAuditoria;

    public PublicacionService(ProductorAuditoria productorAuditoria) {
        this.productorAuditoria = productorAuditoria;
    }

    public void manejarPublicacion() {
        productorAuditoria.publicarAnimalesSinRevision();
    }
}