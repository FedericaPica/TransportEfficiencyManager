package com.java.tem.model.programmacorseservice.entity.risorseservice;

import com.java.tem.model.accountservice.entity.Utente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class RisorseServiceTest {

    @InjectMocks
    private RisorseService risorseService;
    @InjectMocks
    private Mezzo mezzo = new Mezzo();
    @InjectMocks
    private Linea linea = new Linea();
    @InjectMocks
    private Conducente conducente = new Conducente();
    @InjectMocks
    private Utente utente = new Utente();

    @Test
    @WithMockUser
    void checkOwnershipIfMezzo() {
        mezzo.setAzienda(utente);
        assertTrue(risorseService.checkOwnership(mezzo, utente));
    }

    @Test
    @WithMockUser
    void checkOwnershipIfConducente() {
        conducente.setAzienda(utente);
        assertTrue(risorseService.checkOwnership(conducente, utente));
    }

    @Test
    @WithMockUser
    void checkOwnershipIfLinea() {
        linea.setAzienda(utente);
        assertTrue(risorseService.checkOwnership(linea, utente));
    }

    @Test
    @WithMockUser
    void checkOwnershipIfNotResource() {
        assertFalse(risorseService.checkOwnership(null, utente));
    }

    @Test
    @WithMockUser
    void isBoundIfMezzo() {

    }

    @Test
    @WithMockUser
    void isBoundIfConducente() {

    }

    @Test
    @WithMockUser
    void isBoundIfLinea() {

    }
}