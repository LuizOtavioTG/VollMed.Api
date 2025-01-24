package med.voll.api.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.EnderecoDTO;

public record MedicoAtualizacaoDTO (@NotNull
                                    Long id,
                                    String nome,
                                    String telefone,
                                    EnderecoDTO endereco) {
}
