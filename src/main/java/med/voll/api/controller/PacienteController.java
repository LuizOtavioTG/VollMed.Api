package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteListagemDTO;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public void cadastrarPaciente(@RequestBody
                                      @Valid
                                      PacienteCadastroDTO dado){
        pacienteService.cadastrarPaciente(dado);
    }
    @GetMapping()
    public Page<PacienteListagemDTO> listarTodosOsPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return pacienteService.listarTodosOsPacientes(paginacao);
    }

    @PutMapping()
    @Transactional
    public void autualizarPaciente (@RequestBody @Valid
                                    PacienteAtualizacaoDTO dadosAtualizados){
        pacienteService.autualizarPaciente(dadosAtualizados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluirPaciente (@PathVariable Long id){
        pacienteService.excluirPaciente(id);
    }
}
