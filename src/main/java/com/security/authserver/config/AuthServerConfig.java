package com.security.authserver.config;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        var service = new InMemoryClientDetailsService();
//
//        var cd = new BaseClientDetails();
//        cd.setClientId("client");
//        cd.setClientSecret("secret");
//        cd.setScope(List.of("read"));
//        cd.setAuthorizedGrantTypes(List.of("password"));
//
//        service.setClientDetailsStore(Map.of("client", cd));
//
//        clients.withClientDetails(service);
//    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .scopes("read")
                .redirectUris("http://localhost:9090/home")
               .and()
                .withClient("client2")
                .secret("secret")
                .authorizedGrantTypes("client_credentials")
                .scopes("info")
              .and()
                .withClient("resourceserver")
                .secret("resourceserversecret")
              ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()");
    }

}
