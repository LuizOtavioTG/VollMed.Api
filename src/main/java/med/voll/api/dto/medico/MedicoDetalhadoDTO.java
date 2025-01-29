package med.voll.api.dto.medico;

import med.voll.api.dto.EnderecoDTO;
import med.voll.api.model.Endereco;
import med.voll.api.model.Especialidade;
import med.voll.api.model.Medico;

public record MedicoDetalhadoDTO (Long id,
                                  String nome,
                                  String email,
                                  String telefone,
                                  String crm,
                                  Especialidade especialidade,
                                  Endereco endereco) {
    public MedicoDetalhadoDTO (Medico medico){
        this(medico.getId(), medico.getNome(), medico.getCrm(), medico.getEmail(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
