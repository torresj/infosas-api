package com.torresj.infosas.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class MetricsControllerAspect {

    private final MeterRegistry meterRegistry;

    @Before(
            value = "execution(* com.torresj.infosas.services.StaffService.getStaffsBySurname(..))"
    )
    public void updateStaffsEndpointCounter(){
        Counter counter = Counter.builder("infosas")
                .description("a number of requests to infosas")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.infosas.services.StaffService.getStaffById(..))"
    )
    public void updateStaffEndpointCounter(){
        Counter counter = Counter.builder("infosas")
                .description("a number of requests to infosas")
                .register(meterRegistry);
        counter.increment();
    }
}
