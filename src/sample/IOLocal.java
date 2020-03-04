package sample;

import javafx.collections.ObservableList;
import org.bouncycastle.util.encoders.Hex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOLocal {

    static String retrieveMessage(String inputFile) throws IOException {

        byte[] inputBytes = new byte[0];
        try {
// reading medical record + stored hash
            System.out.println("Verifying hash of medical record..");
            //  String inFile = "MedicalRecordNielsJ.pdf";
// String inFile = "TamperedMedicalRecord.pdf";
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
        System.out.println("built str txt: " + txt);
        return txt;

    }

    static void storeMessage(String outputFile, ObservableList<CharSequence> paragraph) throws IOException {
        //Iterator<CharSequence> iter = paragraph.iterator();
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

        String txt = String.join("\n", paragraph);
       // String txt = new String(textArea);
        System.out.println("built str txt: " + txt);
      //  StringBuilder sb = new StringBuilder();
      //  for (byte[] bytes : Arrays.asList(textArea)) {
      //      sb.append(bytes);
      //  }
      //  System.out.println(sb.toString());
        FileUtils.write(outTextArea, txt.getBytes());


       // BufferedWriter bf = new BufferedWriter(new FileWriter(new File(outputFile + "." + "txt")));
      //  while (iter.hasNext()) {
        //    CharSequence seq = iter.next();
        //    System.out.println(seq);
         //   bf.append(seq);
          //  bf.newLine();

       // }

       // bf.flush();
       // bf.close();

    }
}
