package com.galimagroup.back.controller;



import com.galimagroup.back.config.TestSecurityConfig;
import com.galimagroup.back.dto.ContactDto;
import com.galimagroup.back.model.Contact;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import com.galimagroup.back.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private com.galimagroup.back.repository.ContactRepository contactRepository;

    @MockBean
    private org.modelmapper.ModelMapper modelMapper;

    @Test
    void submitContact_returnsOk() throws Exception {
        ContactDto contact = new ContactDto();
        // Simuler le mapping du DTO vers l'entité
        Contact entity = new Contact();
        when(modelMapper.map(any(ContactDto.class), eq(Contact.class))).thenReturn(entity);
        when(modelMapper.map(any(Contact.class), eq(ContactDto.class))).thenReturn(contact);
        // Simuler la sauvegarde
        when(contactRepository.save(any(Contact.class))).thenReturn(entity);

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@email.com\",\"message\":\"Hello\"}"))
                .andExpect(status().isCreated());
    }
}
