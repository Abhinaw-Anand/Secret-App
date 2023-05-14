package com.example.SpringInitial.Repository;

import com.example.SpringInitial.Model.SecretAppUserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretAppUserAuthRepository extends JpaRepository<SecretAppUserAuth, Long>
{
    SecretAppUserAuth findByEmail(@NonNull String email);

    Optional<SecretAppUserAuth> findByEmailIgnoreCase(@NonNull String email);

    Optional<SecretAppUserAuth> findByEmailIgnoreCaseAndPasswordIgnoreCase(@NonNull String email, @NonNull String password);
}
