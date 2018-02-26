package com.interest.auth.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;

/*
 * PBKDF2 salted password hashing.
 * Author: havoc AT defuse.ca
 * www: http://crackstation.net/hashing-security.htm
 */
public class Security
{
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";

    // The following constants may be changed without breaking existing hashes.
    public static final int SALT_BYTES = 24;
    public static final int HASH_BYTES = 24;
    public static final int PBKDF2_ITERATIONS = 10000;

    /**
     * 方法描述：生成盐值
     *
     * @author xiejieyi
     * @date 2/26/2018
     */
    public static String generateSalt(){
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);
        return toHex(salt);
    }

    /**
     * 方法描述：根据明文密码和盐值加密
     *
     * @author xiejieyi
     * @date 2/26/2018
     */
    public static String encrypt(String password, String salt)
    {
        try
        {
            byte[] saltBytes = fromHex(salt);
            // Hash the password
            byte[] hash = pbkdf2(password.toCharArray(), saltBytes, PBKDF2_ITERATIONS, HASH_BYTES);
            // format iterations:salt:hash
            return toHex(hash);
        }catch(Exception ex)
        {
            System.out.println("ERROR: " + ex);
            return password;
        }

    }

    /**
     * 方法描述：校验密码是否正确
     *
     * @author xiejieyi
     * @date 2/26/2018
     */
    public static boolean validatePassword(String password, String salt, String encryptStr)
            throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] saltBytes = fromHex(salt);
        char[] passwordChars = password.toCharArray();
        byte[] inputHash = pbkdf2(passwordChars,saltBytes,PBKDF2_ITERATIONS, HASH_BYTES);
        return slowEquals(inputHash, fromHex(encryptStr));
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param   a       the first byte array
     * @param   b       the second byte array
     * @return          true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     *  Computes the PBKDF2 hash of a password.
     *
     * @param   password    the password to hash.
     * @param   salt        the salt
     * @param   iterations  the iteration count (slowness factor)
     * @param   bytes       the length of the hash to compute in bytes
     * @return              the PBDKF2 hash of the password
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param   hex         the hex string
     * @return              the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param   array       the byte array to convert
     * @return              a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    /**
     * TODO:转移到UT
     */
    public static void main(String[] args)
    {
        try
        {
            String plain = "admin123";
            String salt = Security.generateSalt();

            String encrypt = Security.encrypt(plain, salt);
            System.out.println("encrypt=" + encrypt+",salt="+salt);
            System.out.println("result =" + Security.validatePassword(plain,salt,encrypt));
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex);
        }
    }

}
