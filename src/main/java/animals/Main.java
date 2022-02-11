package animals;

public final class Main {
    
    public static void main(final String[] args) {
        final boolean isTypeSpecified = args.length > 1 && args[0].equals("-type");
        final StorageService storageService = isTypeSpecified
                ? StorageService.of(args[1])
                : StorageService.getDefaultService();
        
        new Application(storageService).run();
    }
}