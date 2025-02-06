package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.AutenticacaoDTO;
import med.voll.api.dto.TokenJWTDTO;
import med.voll.api.model.Usuario;
import med.voll.api.service.TokenJWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class AutenticacaoController {


    private final AuthenticationManager authenticationManager;

    private final TokenJWTService tokenJWTService;
    @Autowired
    public AutenticacaoController(AuthenticationManager authenticationManager, TokenJWTService tokenJWTService) {
        this.authenticationManager = authenticationManager;
        this.tokenJWTService = tokenJWTService;
    }

    @PostMapping
    public ResponseEntity<TokenJWTDTO> efetuarLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO){
        var authenticationToken = new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenJWTService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWTDTO (tokenJWT));
    }


}
