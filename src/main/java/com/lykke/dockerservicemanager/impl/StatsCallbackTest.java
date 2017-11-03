package com.lykke.dockerservicemanager.impl;

import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

class StatsCallbackTest extends ResultCallbackTemplate<StatsCallbackTest, Statistics> {
        private final CountDownLatch countDownLatch;
    private Logger logger = LoggerFactory.getLogger(getClass());


    private Boolean gotStats = false;

        public StatsCallbackTest(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void onNext(Statistics stats) {
            logger.info("Received stats #{}: {}", countDownLatch.getCount(), stats);
            if (stats != null) {
                gotStats = true;
            }
            countDownLatch.countDown();
        }

        public Boolean gotStats() {
            return gotStats;
        }
    }
