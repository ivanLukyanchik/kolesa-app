package by.kolesa.backend.security;

import by.kolesa.backend.exception.LoadingKeystoreException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
@Data
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private long jwtExpiration;

    @PostConstruct
    public void init() throws LoadingKeystoreException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/kolesa.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new LoadingKeystoreException("Exception occurred while loading keystore");
        }
    }

    @SneakyThrows
    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpiration)))
                .signWith(getPrivateKey())
                .compact();
    }

    @SneakyThrows
    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpiration)))
                .signWith(getPrivateKey())
                .compact();
    }


    private PrivateKey getPrivateKey() throws LoadingKeystoreException {
        try {
            return (PrivateKey) keyStore.getKey("kolesa", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new LoadingKeystoreException("Exception occurred while retrieving public key from keystore");
        }
    }

    @SneakyThrows
    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() throws LoadingKeystoreException {
        try {
            return keyStore.getCertificate("kolesa").getPublicKey();
        } catch (KeyStoreException e) {
            throw new LoadingKeystoreException("Exception occurred while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token) throws LoadingKeystoreException {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
