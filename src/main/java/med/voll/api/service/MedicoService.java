package med.voll.api.service;

import med.voll.api.dto.medico.MedicoAtualizacaoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoDetalhadoDTO;
import med.voll.api.dto.medico.MedicoListagemDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    @Autowired
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }


    public Medico cadastrarMedico(MedicoCadastroDTO dado) {
        Medico medico = new Medico(dado);
        medicoRepository.save(medico);
        return medico;
    }

    public Page<MedicoListagemDTO> listarTodosOsMedicos(Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(MedicoListagemDTO::new);
    }

    public Medico autualizarMedico(MedicoAtualizacaoDTO dados) {
        Medico medico = medicoRepository.getReferenceById(dados.id());
         medico.atualizarInformacoes(dados);
         return medico;
    }

    public void excluirMedico(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }

    public Medico detalharMedico(Long id) {
        return medicoRepository.getReferenceById(id);
    }
}
