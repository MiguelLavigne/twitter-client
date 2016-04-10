package com.example.android.twitterclient;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import rx.plugins.TestRxJavaPlugins;

public class TestRunner extends BlockJUnit4ClassRunner {
    /**
     * Constructs a new instance of the default runner
     */
    public TestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        // We register immediate scheduler for all rx schedulers
        // to facilitate testing
        TestRxJavaPlugins.registerImmediateSchedulers();
    }
}
