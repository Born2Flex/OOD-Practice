package com.gildedrose;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.QuietReporter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class GildedRoseApprovalTest {

    @Test
    void thirtyDaysOfFixtureOutput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream console = System.out;
        System.setOut(new PrintStream(output));
        try {
            TexttestFixture.main(new String[]{"30"});
        } finally {
            System.setOut(console);
        }

        Approvals.verify(output.toString(), new Options().withReporter(new QuietReporter()));
    }
}
