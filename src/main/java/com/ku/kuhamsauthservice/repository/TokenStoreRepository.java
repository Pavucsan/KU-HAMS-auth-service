package com.ku.kuhamsauthservice.repository;


import com.ku.kuhamsauthservice.entity.TokenStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenStoreRepository extends JpaRepository<TokenStore, Long> {
    List<TokenStore> findByUserUsernameAndRevokedFalse(String username);
}
