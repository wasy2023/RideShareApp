package settings;

import java.io.*;
import java.util.Properties;

public class UpdateSettings {
    public static final String file ="settings.properties";
    public static void main(String[] args)
    {

    }
    private static void updateProperty(String key, String value)
    {
        Properties properties = new Properties();
        try(InputStream inputStream = new FileInputStream(file)){
            properties.load(inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
    }
        properties.setProperty(key,value);
        try(OutputStream outputStream = new FileOutputStream(file)){
            properties.store(outputStream,"Updated properties");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
