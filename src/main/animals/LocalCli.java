package main.animals;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LocalCli extends TextInterface implements Cli {
    private final Map<String, MenuEntry> menu = new LinkedHashMap<>();
    private final Map<Property, String> properties = new EnumMap<>(Property.class);
    private final MenuEntry defaultEntry = new MenuEntry("Incorrect option",
            () -> System.out.println(MessageFormat.format(get(Property.ERROR), menu.size())));
    
    private boolean isOnlyOnce;
    
    public LocalCli() {
        super();
        for (Property property : Property.values()) {
            final String key = "menu.property." + property.name().toLowerCase();
            if (resourceBundle.containsKey(key)) {
                set(property, resourceBundle.getString(key));
            }
        }
    }
    
    @Override
    public Cli set(Property property, String value) {
        properties.put(property, value);
        return this;
    }
    
    @Override
    public Cli add(String description, Runnable action) {
        return this.add(String.valueOf(menu.size() + 1), description, action);
    }
    
    @Override
    public Cli add(String key, String description, Runnable action) {
        menu.put(key, new MenuEntry(resourceBundle.getString(description), action));
        return this;
    }
    
    @Override
    public Cli disable() {
        disable(String.valueOf(menu.size()));
        return this;
    }
    
    @Override
    public Cli disable(String key) {
        menu.get(key).isEnabled = false;
        return this;
    }
    
    @Override
    public Cli enable(String key) {
        menu.get(key).isEnabled = true;
        return this;
    }
    
    @Override
    public Cli onlyOnce() {
        isOnlyOnce = true;
        return this;
    }
    
    @Override
    public Cli addExit() {
        menu.put(get(Property.EXIT_KEY),
                new MenuEntry(get(Property.EXIT), this::onlyOnce));
        return this;
    }
    
    protected String get(Property property) {
        return properties.getOrDefault(property, property.getValue());
    }
    
    @Override
    public void run() {
        do {
            println();
            println(get(Property.TITLE));
            menu.forEach((key, entry) -> {
                if (entry.isEnabled) {
                    println(get(Property.FORMAT), key, entry);
                }
            });
            final String key = readToLowerCase();
            println();
            menu.getOrDefault(key, defaultEntry).run();
        } while (!isOnlyOnce);
        
    }
    
    private static final class MenuEntry implements Runnable {
        private final String description;
        private final Runnable action;
        boolean isEnabled = true;
        
        MenuEntry(final String description, final Runnable action) {
            this.description = description;
            this.action = action;
        }
        
        @Override
        public void run() {
            action.run();
        }
    
        @Override
        public String toString() {
            return description;
        }
        
    }
    
}
