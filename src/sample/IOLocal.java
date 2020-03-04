package sample;

import javafx.collections.ObservableList;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class IOLocal {

    static String retrieveMessage(String inputFile) throws IOException {

        byte[] inputBytes = new byte[0];
        try {
// reading medical record + stored hash
            System.out.println("Verifying hash of stored message..");
            inputBytes = FileUtils.readAllBytes(inputFile + ".txt");
            byte[] storedHashValue =
                    FileUtils.readAllBytes(inputFile + ".sha256");
// computing new hash
            MessageDigest mDigest = null;
            try {
                mDigest = MessageDigest.getInstance("SHA-256", "BC");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            mDigest.update(inputBytes);
            byte[] computedHashValue = mDigest.digest();

// verifying
            if (MessageDigest
                    .isEqual(storedHashValue,
                            computedHashValue)) {
                System.out.println("Hash values are equal");
                System.out.println("Hash stored value: " + Hex.toHexString(storedHashValue));
                System.out.println("Hash calculated value: " + Hex.toHexString(computedHashValue));
            } else {
                System.out.println("Hash values are not equal");
                System.out.println("Hash stored value: " + Hex.toHexString(storedHashValue));
                System.out.println("Hash calculated value: " + Hex.toHexString(computedHashValue));
            }
        } catch (Exception e) {
        }

        String txt = new String(inputBytes);
        //System.out.println("built str txt: " + txt);
        return txt;

    }

    static void storeMessage(String outputFile, ObservableList<CharSequence> paragraph) throws IOException {
        //need to join charsequence list with newlines in between
        byte[] textArea = String.join("\n", paragraph).getBytes();

        // hashing
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-256", "BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        mDigest.update(textArea);
        byte[] hashValue = mDigest.digest();

        String outFile = outputFile + "." + "sha256";
        FileUtils.write(outFile, hashValue);
        System.out.println("Hashvalue: " + Hex.toHexString(hashValue));

        String outTextArea = outputFile + "." + "txt";

        FileUtils.write(outTextArea, textArea);

    }

    static void storeAccount(String name, String pass) throws InvalidKeySpecException {
        //need to join charsequence list with newlines in between
        char[] nameBytes = name.toCharArray();
        char[] passBytes = pass.toCharArray();
        byte[] salt = null;
        byte[] namesalt = null;
        String stringKey = null;
        String stringName = null;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
            salt = new byte[32];
            secureRandom.nextBytes(salt);
            namesalt = new byte[32];
            secureRandom.nextBytes(namesalt);
            System.out.println("saltvalue: " + Hex.toHexString(salt));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }



    //hash pw
        if (salt!=null) {
            try {
                PBEKeySpec keySpec = new PBEKeySpec(passBytes, salt, 5000, 128);
// specifying data for key derivation
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
// specifying algorithm for key derivation
            SecretKey key = factory.generateSecret(keySpec);
// the actual key derivation with iterated hashing
// key may now be passed to Cipher.init() (which accepts instances of interface SecretKey)
                if (key != null) {stringKey = Base64.toBase64String(key.getEncoded());}
                System.out.println("key hashvalue: " + stringKey);
                System.out.println("keyhexvalue: " + Hex.toHexString(stringKey.getBytes()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }

        if (namesalt!=null) {
            try {
                PBEKeySpec keySpec = new PBEKeySpec(passBytes, namesalt, 5000, 128);
// specifying data for key derivation
                SecretKeyFactory factory =
                        SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
// specifying algorithm for key derivation
                SecretKey key = factory.generateSecret(keySpec);
// the actual key derivation with iterated hashing
// key may now be passed to Cipher.init() (which accepts instances of interface SecretKey)
                if (key != null) {stringName = Base64.toBase64String(key.getEncoded());}
                System.out.println("ame hashvalue: " + stringName);
                System.out.println("namehexvalue: " + Hex.toHexString(stringName.getBytes()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }


        String outFile = Hex.toHexString(stringName.getBytes()) + "." + "acc";
        String outString = Hex.toHexString(salt) + "," + Hex.toHexString(namesalt) + "," + Hex.toHexString(stringKey.getBytes());

        byte[] accountData = outString.getBytes();
        FileUtils.write(outFile, accountData);



    }

}
