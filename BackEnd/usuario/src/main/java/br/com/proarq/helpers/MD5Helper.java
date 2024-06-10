package br.com.proarq.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {
	public static String encryptToMD5(String input) {
        try {
            // Cria uma instância de MessageDigest com o algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Converte a string de entrada em um array de bytes
            byte[] messageDigest = md.digest(input.getBytes());

            // Constrói uma string hexadecimal a partir do array de bytes
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // Retorna o hash MD5 como uma string hexadecimal
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // Caso o algoritmo MD5 não seja suportado, lança uma exceção em runtime
            throw new RuntimeException("Erro ao criptografar utilizando algoritmo" + e);
        }
    }
	

}
