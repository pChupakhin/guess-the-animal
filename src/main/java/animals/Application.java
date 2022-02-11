package animals;

public final class Application extends TextInterface implements Runnable {
    private final KnowledgeTree knowledgeTree;
    private final StorageService storageService;
    private final TreeServices treeServices;

    public Application(final StorageService storageService) {
        this.storageService = storageService;
        knowledgeTree = new KnowledgeTree();
        treeServices = new TreeServices(knowledgeTree);
    }

    @Override
    public void run() {
        printConditional("greeting");
        storageService.load(knowledgeTree);

        if (knowledgeTree.isEmpty()) {
            println();
            println("animal.wantLearn");
            println("animal.askFavorite");
            knowledgeTree.setRoot(new TreeNode<>(ask("animal")));
        }
        println("welcome");

        new LocalCli()
                .add("menu.entry.play", new GuessingGame(knowledgeTree))
                .add("menu.entry.list", treeServices::list)
                .add("menu.entry.search", treeServices::search)
                .add("menu.entry.statistics", treeServices::statistics)
                .add("menu.entry.print", treeServices::print)
                .add("menu.entry.delete", treeServices::delete)
                .addExit()
                .run();


        storageService.save(knowledgeTree);
        println("farewell");
    }

}
