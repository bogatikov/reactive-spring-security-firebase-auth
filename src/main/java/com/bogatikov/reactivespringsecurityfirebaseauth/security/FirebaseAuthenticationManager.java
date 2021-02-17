package com.bogatikov.reactivespringsecurityfirebaseauth.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseAuthenticationManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(authToken);
            return getAuthentication(firebaseToken);
        } catch (FirebaseAuthException | IllegalArgumentException e) {
            return Mono.empty();
        }
    }

    //TODO here we place user retrieving logic
    private Mono<Authentication> getAuthentication(FirebaseToken firebaseToken) {
        Authentication sampleAuthentication = new UsernamePasswordAuthenticationToken(
                firebaseToken.getUid(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("FEED_VIEW"))
        );
        return Mono.just(sampleAuthentication);
//        return userRepository.findByUserId(firebaseToken.getUid())
//                .switchIfEmpty(
//                        roleService.getDefaultRoles()
//                                .flatMap(roles -> userRepository.save(new User(
//                                        firebaseToken.getUid(),
//                                        firebaseToken.getEmail(),
//                                        firebaseToken.getName(),
//                                        firebaseToken.getPicture(),
//                                        roles
//                                )))
//                )
//                .map(user -> user.getRoles()
//                        .stream()
//                        .flatMap(role -> role.getAuthorities().stream()
//                                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
//                        )
//                        .collect(Collectors.toList())
//                )
//                .map(authorities -> new UsernamePasswordAuthenticationToken(
//                        firebaseToken.getUid(),
//                        null,
//                        authorities
//
//                ));
    }
}
