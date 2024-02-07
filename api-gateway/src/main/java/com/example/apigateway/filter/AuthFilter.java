package com.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import com.example.apigateway.config.RouteValidator;
import com.example.apigateway.util.JwtUtil;


@RefreshScope
@Component
public class AuthFilter implements GatewayFilter {

  @Autowired
  private RouteValidator routeValidator;
  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    try {
      if(routeValidator.isSecured.test(request)) {
        if(this.isAuthMissing(request)) {
          return this.onError(exchange, HttpStatus.UNAUTHORIZED);
        }
        final String token = getAuthHeader(request);
        if(jwtUtil.isTokenExpired(token)) {
          return onError(exchange, HttpStatus.FORBIDDEN);
        }
  
        this.updateRequest(exchange, token);
      }
    } 
    catch(ExpiredJwtException | MalformedJwtException | SignatureException ex) {
      System.out.println(ex.getMessage()); //Change for log message
      return onError(exchange, HttpStatus.UNAUTHORIZED);
    } 
    catch(Exception ex) {
      System.out.println(ex.getMessage()); //Cahnge for log message
      return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    
    return chain.filter(exchange);
  }

  private Mono<Void> onError(ServerWebExchange exchange, HttpStatus HttpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus);
    return response.setComplete();
  }

  private String getAuthHeader(ServerHttpRequest request) {
    return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
  }

  private boolean isAuthMissing(ServerHttpRequest request) {
    return !request.getHeaders().containsKey("Authorization");
  }

  private void updateRequest(ServerWebExchange exchange, String token) {
    Claims claims = jwtUtil.extractAllClaims(token);
    exchange.getRequest().mutate()
        .header("subject", String.valueOf(claims.get("subject")))
        .build();
  }


}
