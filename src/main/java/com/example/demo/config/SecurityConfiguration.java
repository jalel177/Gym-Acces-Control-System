package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${spring.security.oauth2.client.provider.keycloak.jwk-set-uri}")
    private String jwkSetUri;
    @Bean
    public JwtDecoder decode() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public KeycloackConverter customKeycloackConverter() {
        return new KeycloackConverter();
    }
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(decode());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.OPTIONS, "/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/auth/forgot-password")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/auth/reset-password")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/reservation/addreservation/**")).hasAuthority("ROLE_MEMBER")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/Email/send-email")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/favoris/addfavoris/**")).hasAuthority("ROLE_MEMBER")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/favoris/deletefavoris/**")).hasAuthority("ROLE_MEMBER")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/reservation/deletereservation/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/reservation/getreservationbyusername/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/reservation/getuserreservation/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/reservation/getreservationnumbers/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/reservation/getreservationbycoursid/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/auth/signup")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/auth/signin")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/auth/logout")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/auth/getusers")).hasAuthority(("ROLE_ADMIN"))
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/users/updateuser/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/users/deleteuser/")).hasAuthority(("ROLE_ADMIN"))
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/seancecours/addseance")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/seancecours/getallseances")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/abonnement/addabo")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/file/uploadfile/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/file/download/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/abonnement/updateabo/**")).permitAll()
                        .requestMatchers(HttpMethod.POST, "/commentaires/addcom/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/commentaires/deletecom/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/seancecours/getSeancesByEntraineurs/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/abonnement/getAbosBydatedebuts/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/abonnement/getuserabo/**").permitAll()

                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/commentaires/seance/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/Email/send-email")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/abonnement/getAllabo")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/seancecours/deleteseance")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/seancecours/updateseance/{ids}")).permitAll()
                                .anyRequest().authenticated())
                        .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(decode()).jwtAuthenticationConverter(customKeycloackConverter())))

                                .sessionManagement(sessionManagement -> sessionManagement
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();}



}


