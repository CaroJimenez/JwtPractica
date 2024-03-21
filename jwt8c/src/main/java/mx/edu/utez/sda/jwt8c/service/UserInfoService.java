package mx.edu.utez.sda.jwt8c.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.sda.jwt8c.entity.UserInfo;
import mx.edu.utez.sda.jwt8c.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private  UserInfoRepository userInfoRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public UserInfoService() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepository.findByUsername(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(
                        ()->new UsernameNotFoundException("no encontrado")
                );
    }

    public String guardarUser(UserInfo userInfo) {
        userInfo.setPassword(
                passwordEncoder.encode(userInfo.getPassword())
        );
        userInfo.setNonLocked(true);
        userInfoRepository.save(userInfo);
        return "Usuario guardado";
    }
}