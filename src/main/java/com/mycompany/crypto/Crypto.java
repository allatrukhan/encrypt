
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.crypto;

import static com.mycompany.crypto.CryptoTypes.KEY;
import static com.mycompany.crypto.CryptoTypes.SUBSTATUATION;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;

enum CryptoTypes {
            SUBSTATUATION,
            KEY
}

public class Crypto{

    public static void encrypt(CryptoTypes type, File inputFile, File outputFile){
        doCrypto(type, Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    public static void decrypt(CryptoTypes type, File inputFile, File outputFile){
        doCrypto(type, Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    public static void doCrypto(CryptoTypes type, int cipherMode, File inputFile, File outputFile) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = new byte[inputBytes.length];
            switch (type){
                case SUBSTATUATION:
                    doSubstitution(cipherMode, inputBytes, outputBytes);
                    break;
                case KEY:
                    doKeyCrypto(cipherMode, inputBytes, outputBytes);
                    break;
            }
            outputStream.write(outputBytes);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if (null != inputStream){
                    inputStream.close();
                }
                if (null != outputStream){
                    outputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void doSubstitution(int cipherMode, byte[] text, byte[] result) {
        if (cipherMode == Cipher.ENCRYPT_MODE) {
            for (int i = 0; i < text.length; i++) {
                result[i] = (byte) (text[i] + 3);
            }
        } else if (cipherMode == Cipher.DECRYPT_MODE) {
            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) (text[i] - 3);
            }
        }
    }

    public static void doKeyCrypto(int cipherMode, byte[] text, byte[] result) {
        String keyWord = "keyWord";
        byte[] keyarr = keyWord.getBytes();
        if (cipherMode == Cipher.ENCRYPT_MODE) {
            for(int i = 0; i < text.length; i++) {
                result[i] = (byte) (text[i] ^ keyarr[i % keyarr.length]);
            }
        } else if (cipherMode == Cipher.DECRYPT_MODE) {
            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) (text[i] ^ keyarr[i% keyarr.length]);
            }
        }
    }

    public static void main(String[] args) {
        File text = new File("D:\\project\\document.txt");
        File inputFile = new File("D:\\project\\encrypted.txt");
        File outputFile = new File("D:\\project\\decrypted.txt");

//            encrypt(SUBSTATUATION, text, inputFile);
//            decrypt(SUBSTATUATION, inputFile, outputFile);

            encrypt(KEY, text, inputFile);
            decrypt(KEY, inputFile, outputFile);
    }
}

