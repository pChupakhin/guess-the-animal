package main.animals;

public final class GuessingGame extends TextInterface implements Runnable {
    private final KnowledgeTree db;

    public GuessingGame(final KnowledgeTree db) {
        this.db = db;
    }

    @Override
    public void run() {
        do {
            println("game.letsPlay");
            println("game.think");
            println("game.enter");
            readToLowerCase();
            db.reset();

            while (db.isStatement()) {
                db.next(askQuestion());
            }

            if (askQuestion()) {
                println("game.win");
            } else {
                giveUp();
            }

            print("game.thanks");

        } while (askYesNo("game.again"));
    }

    private void giveUp() {
        println("game.giveUp");
        final String animal = ask("animal");
        final String guessedAnimal = db.getCurrent().getData();
        final String statement = ask("statement", guessedAnimal, animal);
        final boolean isCorrect = askYesNo("game.isCorrect", animal);

        db.addAnimal(animal, statement, isCorrect);

        println("game.learned");
        final String negative = applyRules("negative", statement);
        final String fact1 = applyRules("animalFact", isCorrect ? statement : negative);
        final String fact2 = applyRules("animalFact", isCorrect ? negative : statement);

        printFact(fact1, animal);
        printFact(fact2, guessedAnimal);
        println("game.distinguish");
        println(" - {0}",capitalize(LanguageRules.applyRules("question", statement)));
        println();
        print("animal.nice");
        println("animal.learnedMuch");
    }

    private boolean askQuestion() {
        final String question  = applyRules(db.isAnimal() ? "guessAnimal" : "question",
                db.getCurrent().getData());
        return askYesNo(capitalize(question));
    }

    private void printFact(final String fact, final String animal) {
        println(" - {0}.", capitalize(String.format(fact, applyRules("definite", animal))));
    }
    
}
