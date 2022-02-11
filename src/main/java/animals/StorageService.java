package main.java.animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public enum StorageService {
    JSON(new JsonMapper()),
    YAML(new YAMLMapper()),
    XML(new XmlMapper());

    private static final String CONFIG_FILE = "application.xml";
    private static final String BASE_NAME;
    private static final String BASE_TYPE;
    private static final StorageService defaultService;

    static {
        final Properties properties = new Properties();
        try {
            properties.loadFromXML(StorageService.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch(IOException ignored) {}
        BASE_NAME = properties.getProperty("baseName");
        BASE_TYPE = properties.getProperty("type");
        defaultService = of(BASE_TYPE);
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

    private File getSavedKnowledgeFile() {
        final String language = System.getProperty("user.language", "en").toLowerCase();
        final String lnName = "en".equals(language) ? "" : "_" + language;
        return new File(BASE_NAME + lnName + "." + name().toLowerCase());
    }

    public void load(final KnowledgeTree tree){
        final File savedKnowledgeFile = getSavedKnowledgeFile();

        if(savedKnowledgeFile.exists()) {
            try {
                tree.setRoot(objectMapper.readValue(savedKnowledgeFile, TreeNode.class));
            } catch (IOException ignored) {}
        }
    }

    public void save(final KnowledgeTree tree) {
        final File savedKnowledgeFile = getSavedKnowledgeFile();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(savedKnowledgeFile, tree.getRoot());
        } catch (IOException ignored) {}
    }

}
