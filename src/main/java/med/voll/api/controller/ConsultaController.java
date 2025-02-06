package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @PostMapping
    @Transactional
    public ResponseEntity<ConsultaDetalhamentoDTO> agendarConsulta(@RequestBody @Valid ConsultaAgendamentoDTO consultaAgendamentoDTO){
        System.out.println(consultaAgendamentoDTO);
        return ResponseEntity.ok(new ConsultaDetalhamentoDTO(null, null, null, null));
    }
}
