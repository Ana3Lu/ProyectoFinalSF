package com.zooSabana.demo.logica;

import com.zooSabana.demo.mensajeria.logica.PublicacionService;
import com.zooSabana.demo.mensajeria.productores.ProductorAuditoria;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PublicacionServiceTest {

    @Test
    void manejarPublicacionDeberiaLlamarProductorAuditoria() {
        ProductorAuditoria productorAuditoria = Mockito.mock(ProductorAuditoria.class);

        PublicacionService publicacionService = new PublicacionService(productorAuditoria);

        publicacionService.manejarPublicacion();

        Mockito.verify(productorAuditoria, Mockito.times(1)).publicarAnimalesSinRevision();
    }
}

