package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.dto.medico.MedicoAtualizacaoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoDetalhadoDTO;
import med.voll.api.dto.medico.MedicoListagemDTO;
import med.voll.api.model.Medico;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;




@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<MedicoDetalhadoDTO> cadastrarMedico(@RequestBody
                                                                  @Valid
                                                                  MedicoCadastroDTO dado,
                                                              UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoService.cadastrarMedico(dado);
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new MedicoDetalhadoDTO(medico));
    }

    @GetMapping()
    public ResponseEntity<Page<MedicoListagemDTO>> listarTodosOsMedicos(@PageableDefault(size = 10, sort ={"nome"})
                                                        Pageable paginacao){
        var page = medicoService.listarTodosOsMedicos(paginacao);

        return ResponseEntity.ok(page);
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<MedicoDetalhadoDTO>  autualizarMedico (@RequestBody @Valid MedicoAtualizacaoDTO dados){
        Medico medico = medicoService.autualizarMedico(dados);
        return ResponseEntity.ok(new MedicoDetalhadoDTO(medico));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirMedico (@PathVariable Long id){
        medicoService.excluirMedico(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDetalhadoDTO> detalharMedico (@PathVariable Long id){
        Medico medico = medicoService.detalharMedico(id);
        return ResponseEntity.ok(new MedicoDetalhadoDTO(medico));
    }



}
