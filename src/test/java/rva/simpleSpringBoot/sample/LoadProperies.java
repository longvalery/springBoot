package rva.simpleSpringBoot.sample;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperies {
    private String fileName;
    private Properties properties;

    public LoadProperies(String fileName) {
        this.fileName = fileName;
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(this.fileName)) {
            properties = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
                return;
            }
            this.properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
