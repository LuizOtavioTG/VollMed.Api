package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.dto.MedicoAtualizacaoDTO;
import med.voll.api.dto.MedicoCadastroDTO;
import med.voll.api.dto.MedicoListagemDTO;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;
    @Autowired
    public MedicoController(MedicoService medicoService){
        this.medicoService = medicoService;
    }

    @PostMapping()
    @Transactional
    public void cadastrarMedico(@RequestBody @Valid MedicoCadastroDTO dado){
        medicoService.cadastrarMedico(dado);
    }

    @GetMapping()
    public Page<MedicoListagemDTO> listarTodosOsMedicos(@PageableDefault(size = 10, sort ={"nome"})
                                                        Pageable paginacao){
        return medicoService.listarTodosOsMedicos(paginacao);
    }
    @PutMapping()
    @Transactional
    public void  autualizarMedico (@RequestBody @Valid MedicoAtualizacaoDTO dados){
        medicoService.autualizarMedico(dados);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void excluirMedico (@PathVariable Long id){
        medicoService.excluirMedico(id);
    }


}
