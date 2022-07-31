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
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * JWT token generator. {@link #start(Resource)} should be called before usage!
 */
public class JwtTokenGenerator {
    protected final String type;
    protected final String issuer;
    protected ECDSASigner signer;

    static {
        BouncyCastleInitializer.stub();
    }

    /**
     * Create new object
     * @param type token type
     */
    public JwtTokenGenerator(@NonNull String issuer, @NonNull String type) {
        this.issuer = issuer;
        this.type = type;
    }

    /**
     * Start generator with key
     * @param privateKey EC private key
     */
    public void start(@NonNull Resource privateKey) throws IOException, JOSEException {
        var bytes = privateKey.getInputStream().readAllBytes();
        var parsed = (PEMKeyPair) new PEMParser(new StringReader(new String(bytes, StandardCharsets.UTF_8))).readObject();
        var privateKey1 = (KeyPair) new JcaPEMKeyConverter().getKeyPair(parsed);
        signer = new ECDSASigner((ECPrivateKey) privateKey1.getPrivate());
    }

    /**
     * Generate JWT token
     * @param ttl token lifetime
     * @param tokenEnhancer callback to add custom claims
     * @return generated JWT token
     */
    @NonNull
    public String generate(@NonNull Duration ttl, @NonNull Consumer<JWTClaimsSet.Builder> tokenEnhancer) throws JOSEException {
        var claims = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .claim("typ", type)
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(Date.from(Instant.now().plus(ttl)));
        tokenEnhancer.accept(claims);
        var jwt = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.ES256K).type(JOSEObjectType.JWT).build(),
                claims.build()
        );
        jwt.sign(signer);
        return jwt.serialize();
    }
}
