package com.foo.lock.global;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CronTimer {

    private ILock lock;

    private String systemId;


    private final String lockName ="DEMO-LOCK";

    private HazelcastInstance hazelcastInstance;

    // TODO check this warning
    public CronTimer(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    public void setup() {
        log.warn("Starting Timer with cron mask :\"0/5 * * * * * \" ,lock Name :{}",  lockName);
        lock = hazelcastInstance.getLock(lockName);
        systemId = ManagementFactory.getRuntimeMXBean().getName();
    }

    /**
     * Cron expression is represented by six fields:
     *
     * second, minute, hour, day of month, month, day(s) of week
     */
    @Scheduled(cron = "0/5 * * * * * ")
    public void scheduleTaskUsingCronExpression() throws InterruptedException {

        if (!lock.tryLock()) {
            log.info("Jvm Name = {} Failed to obtain lock, at {}", systemId, LocalTime.now());
            return;
        }
        try {
            log.info("Jvm Name = {} Lock obtained success, at {}",systemId,  LocalTime.now());
            Thread.sleep(100);
        } finally {
            log.info("Jvm Name = {} Lock release,  success at {}",systemId,  LocalTime.now());
            lock.unlock();
        }
    }


}
