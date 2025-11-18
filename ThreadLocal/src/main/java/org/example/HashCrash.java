package org.example;

/**
 * @author liushaoya
 * @since 2024-12-09 19:20
 */
public class HashCrash {
    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) {
        int hashCode = 0;
        for(int i=0; i<16; i++) {
            hashCode = i*HASH_INCREMENT+HASH_INCREMENT;
            System.out.println(hashCode & 15);
        }
    }

}
