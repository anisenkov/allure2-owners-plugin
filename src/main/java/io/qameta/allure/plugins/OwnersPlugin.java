package io.qameta.allure.plugins;

import io.qameta.allure.Aggregator;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.entity.LabelName;
import io.qameta.allure.entity.TestResult;
import io.qameta.allure.tree.TestResultTree;
import io.qameta.allure.tree.Tree;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static io.qameta.allure.entity.TestResult.comparingByTimeAsc;
import static io.qameta.allure.tree.TreeUtils.groupByLabels;

public class OwnersPlugin implements Aggregator {

    private static final String JSON_FILE_NAME = "owners.json";

    @Override
    public void aggregate(Configuration configuration, List<LaunchResults> launchesResults, Path outputDirectory) throws IOException {
        final JacksonContext jacksonContext = configuration.requireContext(JacksonContext.class);
        final Path dataFolder = Files.createDirectories(outputDirectory.resolve("data"));
        final Path dataFile = dataFolder.resolve(JSON_FILE_NAME);
        OutputStream os = Files.newOutputStream(dataFile);
        jacksonContext.getValue().writeValue(os, getData(launchesResults));
    }

    private Tree<TestResult> getData(final List<LaunchResults> launchesResults) {
        final Tree<TestResult> owners = new TestResultTree("owners",
                testResult -> groupByLabels(testResult, LabelName.OWNER)
        );

        launchesResults.stream()
                .map(LaunchResults::getResults)
                .flatMap(Collection::stream)
                .sorted(comparingByTimeAsc())
                .forEach(owners::add);

        return owners;
    }
}
