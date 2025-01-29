package med.voll.api.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.dto.EnderecoDTO;
import med.voll.api.model.Especialidade;

public record MedicoCadastroDTO(@NotBlank(message = "O nome é um campo obrigatório")
                        String nome,
                                @NotBlank
                        @Email (message = "Formato do email é inválido")
                        String email,
                                @NotBlank
                        @Pattern(regexp = "\\d{4,6}", message = "Formato do CRM é inválido")
                        String crm,
                                @NotBlank
                        String telefone,
                                @NotNull(message = "{especialidade.obrigatoria}")
                        Especialidade especialidade,
                                @NotNull(message = "{endereco.obrigatorio}")
                        @Valid
                                EnderecoDTO endereco) {
}
