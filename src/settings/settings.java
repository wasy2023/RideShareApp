package settings;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class settings extends IOException{
    private static Properties properties;
    public settings()
    {
        properties = new Properties();
        try{
            FileInputStream file = new FileInputStream("settings.properties");
            properties.load(file);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public String propertiesTest(String key)
    {
        return properties.getProperty(key);
    }
    public void updateProperty(String key,String value)
    {
        properties.setProperty(key,value);
        try(OutputStream outputStream = new FileOutputStream("settings.properties")){
            properties.store(outputStream,"Updated properties");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }



}
