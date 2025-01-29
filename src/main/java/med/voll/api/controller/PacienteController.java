package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.medico.MedicoDetalhadoDTO;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteDetalhadoDTO;
import med.voll.api.dto.paciente.PacienteListagemDTO;
import med.voll.api.model.Paciente;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;



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
    public ResponseEntity<PacienteDetalhadoDTO> cadastrarPaciente(@RequestBody
                                                                      @Valid
                                                                      PacienteCadastroDTO dado,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteService.cadastrarPaciente(dado);
        var uri = uriComponentsBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteDetalhadoDTO(paciente));
    }
    @GetMapping()
    public ResponseEntity<Page<PacienteListagemDTO>> listarTodosOsPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
         var page = pacienteService.listarTodosOsPacientes(paginacao);
         return ResponseEntity.ok(page);
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<PacienteDetalhadoDTO> autualizarPaciente (@RequestBody @Valid
                                    PacienteAtualizacaoDTO dadosAtualizados){
       PacienteDetalhadoDTO pacienteDetalhadoDTO = pacienteService.autualizarPaciente(dadosAtualizados);
        return ResponseEntity.ok(pacienteDetalhadoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirPaciente (@PathVariable Long id){
        pacienteService.excluirPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDetalhadoDTO> detalharPaciente (@PathVariable Long id){
        PacienteDetalhadoDTO pacienteDetalhadoDTO = pacienteService.detalharPaciente(id);
        return ResponseEntity.ok(pacienteDetalhadoDTO);
    }

}
