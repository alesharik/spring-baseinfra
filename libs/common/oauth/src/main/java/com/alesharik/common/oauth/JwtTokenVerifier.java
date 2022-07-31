package com.alesharik.common.oauth;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.proc.SingleKeyJWSKeySelector;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class verifies JWT tokens. {@link #start(Resource)} should be called before usage!
 */
public class JwtTokenVerifier {
    protected final String type;
    protected final String issuer;
    protected NimbusJwtDecoder jwtDecoder;

    static {
        BouncyCastleInitializer.stub();
    }

    /**
     * Create new object
     * @param type token type
     */
    public JwtTokenVerifier(@NonNull String issuer, @NonNull String type) {
        this.issuer = issuer;
        this.type = type;
    }

    /**
     * Start verifier with public key
     * @param publicKey EC public key
     */
    public void start(@NonNull Resource publicKey) throws IOException {
        var parsed = (SubjectPublicKeyInfo) new PEMParser(new InputStreamReader(publicKey.getInputStream())).readObject();
        var publicKey1 = new JcaPEMKeyConverter().getPublicKey(parsed);
        JWSKeySelector<SecurityContext> jwsKeySelector = new SingleKeyJWSKeySelector<>(JWSAlgorithm.ES256K, publicKey1);
        DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSKeySelector(jwsKeySelector);
        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
            if (!issuer.equals(claims.getIssuer()))
                throw new BadJWTException("Issuer is invalid");
            if (!type.equals(claims.getClaim("typ")))
                throw new BadJWTException("Token type is invalid");
        });
        jwtDecoder = new NimbusJwtDecoder(jwtProcessor);
    }

    /**
     * Verify token
     * @param token jwt token string
     * @return decoded and verified JWT token
     * @throws org.springframework.security.oauth2.jwt.BadJwtException if token is invalid
     */
    @NonNull
    public Jwt verify(@NonNull String token) {
        return jwtDecoder.decode(token);
    }
}
