package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record MedicoAtualizacaoDTO (@NotNull
                                    Long id,
                                    String nome,
                                    String telefone,
                                    EnderecoDTO endereco) {
}
