package mx.edu.utez.sda.jwt8c.repository;

import mx.edu.utez.sda.jwt8c.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    public Optional<UserInfo> findByUsername(String username);
}
