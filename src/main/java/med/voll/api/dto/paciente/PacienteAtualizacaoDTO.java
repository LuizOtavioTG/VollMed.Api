package med.voll.api.dto.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.EnderecoDTO;

public record PacienteAtualizacaoDTO(@NotNull
                                     Long id,
                                     String nome,
                                     String telefone,
                                     EnderecoDTO endereco) {
}
