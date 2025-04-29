package com;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    Properties p = new Properties();
    public  ConfigLoader(String fileName)
    {
        this.p = new Properties();
        try(InputStream input = this.getClass().getResourceAsStream(fileName))
        {
            if(input == null) throw new RuntimeException();
            p.load(input);
        }catch (IOException e) {
            System.out.println(e);
        }
    }
}
