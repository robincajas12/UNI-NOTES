package org.uce.batch;

import redis.clients.jedis.Jedis;
import java.util.Random;

public class RedisDB {
    private Jedis jedis;

    public RedisDB(String host, int port) {
        this.jedis = new Jedis(host, port);
    }
    public int storeRandomNumberKeyValue() {
        jedis.select(1); // Select Redis DB 1

        // Generate a random number to be used as the key
        int randomNumber = new Random().nextInt(1000);  // Random number between 0 and 999

        // Store the random number as the key and set its value
        jedis.set(String.valueOf(randomNumber), String.valueOf(randomNumber)); // Store the random number as both key and value
        jedis.expire(String.valueOf(randomNumber), 60); // Set expiration time of 60 seconds

        System.out.println("Stored random number " + randomNumber + " as the key with the same value " + randomNumber + " (expires in 60 seconds)");

        // Return the random number (which is the key)
        return randomNumber;
    }

    // Check if a key exists in Redis
    public boolean doesKeyExist(int key) {
        jedis.select(1); // Select Redis DB 1
        return jedis.exists(String.valueOf(key)); // Check if the key exists
    }

}
