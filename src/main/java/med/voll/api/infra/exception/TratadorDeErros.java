package med.voll.api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.validation.FieldError;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacao>> tratarError400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream()
                .map(ErroValidacao::new)
                .toList());
    }
    @ExceptionHandler(ValidacaoException.class)
    public  ResponseEntity<String> tratarErroRegraDeNegocio (ValidacaoException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroAutenticacao> tratarBadCredentials(BadCredentialsException exception) {
        var erro = new ErroAutenticacao("Credenciais inválidas", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroAutenticacao> tratarErroGenerico(Exception exception) {
        var erro = new ErroAutenticacao("Erro interno do servidor", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    // Classe para tratar erros de validação
    public record ErroValidacao(String campo, String mensagem) {
        public ErroValidacao(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    // Classe para tratar erros de autenticação
    public record ErroAutenticacao(String mensagem, String detalhes) {
    }
}
