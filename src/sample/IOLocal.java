package sample;

import javafx.collections.ObservableList;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

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
}
