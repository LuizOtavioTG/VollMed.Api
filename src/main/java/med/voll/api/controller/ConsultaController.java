package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final ConsultaService consultaService;
    @Autowired
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<ConsultaDetalhamentoDTO> agendarConsulta(@RequestBody @Valid ConsultaAgendamentoDTO consultaAgendamentoDTO){

        var consultaDetalhamentoDTO = consultaService.agendar(consultaAgendamentoDTO);
        return ResponseEntity.ok(consultaDetalhamentoDTO);
    }
}
