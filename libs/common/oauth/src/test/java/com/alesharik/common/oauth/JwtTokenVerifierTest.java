package com.alesharik.common.oauth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.BadJwtException;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenVerifierTest {
    private static final JwtTokenVerifier VERIFIER = new JwtTokenVerifier("https://alesharik.com", "test");
    private static ECDSASigner SIGNER;

    @BeforeAll
    static void beforeAll() throws IOException, JOSEException {
        VERIFIER.start(new ClassPathResource("publicKey.pem"));
        var privateKeyStream = JwtTokenGeneratorTest.class.getClassLoader().getResourceAsStream("privateKey.pem");
        var privateKey = new String(privateKeyStream.readAllBytes(), StandardCharsets.UTF_8);
        var parsed = (PEMKeyPair) new PEMParser(new StringReader(privateKey)).readObject();
        SIGNER = new ECDSASigner((ECPrivateKey) new JcaPEMKeyConverter().getKeyPair(parsed).getPrivate());
    }

    @Test
    void shouldVerifyValidKey() throws JOSEException {
        var encoded = jwt(builder -> builder.issuer("https://alesharik.com")
                .claim("typ", "test")
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(Date.from(Instant.now().plusSeconds(10))));
        var decoded = VERIFIER.verify(encoded);
        assertThat(decoded.getIssuer().toString())
                .isEqualTo("https://alesharik.com");
        assertThat(decoded.<String>getClaim("typ"))
                .isEqualTo("test");
    }

    @Test
    void shouldNotValidateKeyWithWrongType() throws JOSEException {
        var encoded = jwt(builder -> builder.issuer("https://alesharik.com")
                .claim("typ", "asd")
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(Date.from(Instant.now().plusSeconds(10))));
        assertThatThrownBy(() -> VERIFIER.verify(encoded)).isInstanceOf(BadJwtException.class);
    }

    @Test
    void shouldNotValidateKeyWithWrongIssuer() throws JOSEException {
        var encoded = jwt(builder -> builder.issuer("https://hacker.alesharik.com")
                .claim("typ", "test")
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(Date.from(Instant.now().plusSeconds(10))));
        assertThatThrownBy(() -> VERIFIER.verify(encoded)).isInstanceOf(BadJwtException.class);
    }

    @Test
    void shouldNotValidateExpiredKey() throws JOSEException {
        var encoded = jwt(builder -> builder.issuer("https://alesharik.com")
                .claim("typ", "test")
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(Date.from(Instant.now().minusSeconds(10))));
        assertThatThrownBy(() -> VERIFIER.verify(encoded)).isInstanceOf(BadJwtException.class);
    }

    private String jwt(Consumer<JWTClaimsSet.Builder> modifier) throws JOSEException {
        var claims = new JWTClaimsSet.Builder();
        modifier.accept(claims);
        var jwt = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.ES256K).type(JOSEObjectType.JWT).build(),
                claims.build()
        );
        jwt.sign(SIGNER);
        return jwt.serialize();
    }
}
