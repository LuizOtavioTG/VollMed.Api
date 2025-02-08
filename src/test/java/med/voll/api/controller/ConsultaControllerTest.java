package med.voll.api.controller;


import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.model.Especialidade;
import med.voll.api.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ConsultaAgendamentoDTO> consultaAgendamentoDTOJacksonTester;

    @Autowired
    private JacksonTester<ConsultaDetalhamentoDTO> consultaDetalhamentoDTOJacksonTester;

    @MockitoBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deveria retornar codigo http 400 quando informações estão inválidas")
    @WithMockUser
    void agendarConsultaCenario1() throws Exception {
       var response = mockMvc.perform(post("/consultas")
                       .contentType("application/json")
                       .content("{}"))
               .andReturn()
               .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Deveria retornar codigo http 200 quando informações estão válidas")
    @WithMockUser
    void agendarConsultaCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(15);
        var especialidade = Especialidade.ORTOPEDIA;

        ConsultaDetalhamentoDTO consultaDetalhamentoDTO = new ConsultaDetalhamentoDTO(null, 1L, 3L, data);
        when(consultaService.agendar(any())).thenReturn(consultaDetalhamentoDTO);

        var response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(consultaAgendamentoDTOJacksonTester.write(new ConsultaAgendamentoDTO(1L, 3L, data, especialidade))
                                .getJson())
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = consultaDetalhamentoDTOJacksonTester.write(consultaDetalhamentoDTO)
                .getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }
}