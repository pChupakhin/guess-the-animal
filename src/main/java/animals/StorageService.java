package animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum StorageService {
    JSON(new JsonMapper()),
    YAML(new YAMLMapper()),
    XML(new XmlMapper());
    
    private static final String CONFIG_FILE = "application.xml";
    private static final String DEFAULT_NAME = "animals";
    private static final String DEFAULT_TYPE = "yaml";
    private static final String BASE_NAME;
    private static final StorageService defaultService;
    
    static {
        final Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {}
        BASE_NAME = properties.getProperty("baseName", DEFAULT_NAME);
        defaultService = of(properties.getProperty("type", DEFAULT_TYPE));
    }
    
    private final ObjectMapper objectMapper;
    
    StorageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public static StorageService of(final String type) {
        return valueOf(type.toUpperCase());
    }
    
    public static StorageService getDefaultService() {
        return defaultService;
    }
    
    private File getFile() {
        final String language = System.getProperty("user.language", "en").toLowerCase();
        final String lnName = "en".equals(language) ? "" : "_" + language;
        return new File(BASE_NAME + lnName + "." + name().toLowerCase());
    }
    
    public void load(final KnowledgeTree tree) throws IOException{
        final File file = getFile();
        
        tree.setRoot(objectMapper.readValue(file, TreeNode.class));
    }
    
    public void save(final KnowledgeTree tree) throws IOException{
        final File file = getFile();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, tree.getRoot());
    }

}
