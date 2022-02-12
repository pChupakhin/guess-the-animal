package animals;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Map;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public final class TreeServices extends TextInterface {
    private static final UnaryOperator<String> toName = animal ->
            applyRules("animalName", animal);

    private final KnowledgeTree knowledgeTree;
    private final Map<String, List<String>> animals = new HashMap<>();

    TreeServices(final KnowledgeTree knowledgeTree) {
        this.knowledgeTree = knowledgeTree;
    }

    void list() {
        println("tree.list.animals");
        getAnimals().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        printf("tree.list.printf", entry.getKey(), entry.getValue().size()));
    }

    void search() {
        final String animal = ask("tree.search");
        final List<String> facts = getAnimals().getOrDefault(animal, Collections.emptyList());
        final String feedback = facts.isEmpty() ? "tree.search.noFacts" : "tree.search.facts";
        println(feedback, animal);
        facts.forEach(fact -> printf("tree.search.printf", fact));
    }

    void statistics() {
        final IntSummaryStatistics stats = getStatistics();
        println("tree.stats.title");
        println("tree.stats.root", knowledgeTree.getRoot().getData());
        println("tree.stats.nodes", stats.getCount() * 2 - 1);
        println("tree.stats.animals", stats.getCount());
        println("tree.stats.statements", stats.getCount() - 1);
        println("tree.stats.height", stats.getMax());
        println("tree.stats.minimum", stats.getMin());
        println("tree.stats.average", stats.getAverage());
    }

    private IntSummaryStatistics getStatistics() {
        return getAnimals().values().stream().collect(Collectors.summarizingInt(List::size));
    }

    void print() {
        printNode(knowledgeTree.getRoot(), false, " ");
    }

    private void printNode(final TreeNode node, final boolean isYes, String prefix) {
        if (node.isLeaf()) {
            printf("tree.print.printf",
                    prefix, getLine(isYes),
                    node.getData());
            return;
        }
        printf("tree.print.printf", prefix,
                getLine(isYes),
                applyRules("question", node.getData()));
        
        prefix += isYes ? resourceBundle.getString("tree.print.vertical") : " ";
        
        printNode(node.getYes(), true, prefix);
        printNode(node.getNo(), false, prefix);
    }

    private String getLine(final boolean isBranch) {
        return resourceBundle.getString(isBranch ? "tree.print.branch" : "tree.print.corner");
    }

    private Map<String, List<String>> getAnimals() {
        animals.clear();
        collectAnimals(knowledgeTree.getRoot(), new LinkedList<>());
        return animals;
    }

    private void collectAnimals(final TreeNode node, final Deque<String> facts) {
        if (node.isLeaf()) {
            animals.put(toName.apply(node.getData()), List.copyOf(facts));
            return;
        }
        final String statement = node.getData();
        facts.add(statement);
        collectAnimals(node.getYes(), facts);
        facts.removeLast();
        facts.add(applyRules("negative", statement));
        collectAnimals(node.getNo(), facts);
        facts.removeLast();
    }
    
}
