package mx.edu.utez.sda.jwt8c.control;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.sda.jwt8c.entity.AuthRequest;
import mx.edu.utez.sda.jwt8c.entity.UserInfo;
import mx.edu.utez.sda.jwt8c.service.JwtService;
import mx.edu.utez.sda.jwt8c.service.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @GetMapping("/index")
    public String index(){
        return "Servicio Index";
    }

    @PostMapping("/registrame")
    public String registrame(@RequestBody UserInfo userInfo){
        return userInfoService.guardarUser(userInfo);
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String onlyAdmin(){
        return "Endpoint only admin";
    }

    @GetMapping("/user/test")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String onlyUser(){
        return "Endpoint only User and Admin";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authRequest.getUsername(),
                                    authRequest.getPassword()
                            )
                    );

            if (authentication.isAuthenticated()){
                return jwtService.generateToken(authRequest.getUsername());
            }else {
                System.out.println("no autenticado");
                throw new UsernameNotFoundException("User invalidate");
            }
        }catch (Exception e){
            System.out.println("excepcion");
            throw new UsernameNotFoundException("User invalidate");
        }
    }
}
