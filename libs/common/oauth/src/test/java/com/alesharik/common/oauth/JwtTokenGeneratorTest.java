package com.alesharik.common.oauth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.proc.SingleKeyJWSKeySelector;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenGeneratorTest {
    private static final JwtTokenGenerator GENERATOR = new JwtTokenGenerator("https://alesharik.com", "test");
    private static NimbusJwtDecoder DECODER;

    @BeforeAll
    static void beforeAll() throws IOException, JOSEException {
        GENERATOR.start(new ClassPathResource("privateKey.pem"));
        var publicKeyStream = new ClassPathResource("publicKey.pem").getInputStream();
        var publicKey = new String(publicKeyStream.readAllBytes(), StandardCharsets.UTF_8);
        var parsed = (SubjectPublicKeyInfo) new PEMParser(new StringReader(publicKey)).readObject();
        DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSKeySelector(
                new SingleKeyJWSKeySelector<>(JWSAlgorithm.ES256K, new JcaPEMKeyConverter().getPublicKey(parsed))
        );
        DECODER = new NimbusJwtDecoder(jwtProcessor);
    }

    @Test
    void shouldGenerateToken() throws JOSEException {
        var jwt = GENERATOR.generate(Duration.ofSeconds(10), builder -> {
            builder.claim("test", "true");
        });
        var decoded = DECODER.decode(jwt);
        assertThat(decoded.getIssuer().toString())
                .isEqualTo("https://alesharik.com");
        assertThat(decoded.<String>getClaim("typ"))
                .isEqualTo("test");
        assertThat(decoded.<String>getClaim("test"))
                .isEqualTo("true");
    }
}
