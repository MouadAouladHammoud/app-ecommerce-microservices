package com.mouad.ecommerce.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
// NB: il n'est pas nécessaire d'injecter manuellement le FeignInterceptor dans une @Configuration pour qu'il soit utilisé (comme dans le cas de composant "RestTemplateInterceptor").
//     Dès que ajouter @EnableFeignClients dans application, Spring inclut tous les beans de type "RequestInterceptor" enregistrés dans le contexte.
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Récupérer le jeton JWT depuis le SecurityContext
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken= (JwtAuthenticationToken) authentication;
        String jwtAccessToken = jwtAuthenticationToken.getToken().getTokenValue();
        // Ajouter l'en-tête Authorization avec le token JWT
        requestTemplate.header("Authorization","Bearer " + jwtAccessToken);
    }
}
