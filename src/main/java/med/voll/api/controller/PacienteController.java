package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.PacienteCadastroDTO;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paciente")
public class PacienteController {


    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping()
    @Transactional
    public void cadastrarPaciente(@RequestBody @Valid PacienteCadastroDTO dado){
        pacienteService.cadastrarPaciente(dado);
    }

}
