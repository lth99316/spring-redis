package github.lth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ECDSAUtils {

    private static final String ALGORITHM = "EC";
    private static final String CURVE = "secp256r1";

    public static ECPublicKey getPublicKey(final String strPublicKey) {
        var bytes = Base64.getDecoder().decode(strPublicKey.getBytes(StandardCharsets.UTF_8));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return (ECPublicKey) keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static ECPrivateKey getPrivateKey(final String strPrivateKey) {
        var bytes = Base64.getDecoder().decode(strPrivateKey.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return (ECPrivateKey) keyFactory.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> genToken() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
            ECGenParameterSpec spec = new ECGenParameterSpec(CURVE);
            generator.initialize(spec, new SecureRandom());

            var keyPair = generator.generateKeyPair();

            var privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            var publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());


            var ecPublicKey = getPublicKey(publicKey);
            var ecPrivateKey = getPrivateKey(privateKey);

            System.out.println("Private Key: " + privateKey);
            System.out.println("Public Key: " + publicKey);

            return List.of(privateKey, publicKey);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        genToken();
    }
}
