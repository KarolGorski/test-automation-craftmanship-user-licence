package pl.kg.domain.utils;

import java.util.concurrent.atomic.AtomicLong;

public class TestUtils {
    public static AtomicLong baseId = new AtomicLong();

    public static long generateId() {
        return baseId.addAndGet(1);
    }
}
