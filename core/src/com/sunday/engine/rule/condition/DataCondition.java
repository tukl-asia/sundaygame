package com.sunday.engine.rule.condition;

import com.sunday.engine.common.Data;
import com.sunday.engine.common.Signal;
import com.sunday.engine.databank.SystemPort;
import com.sunday.engine.rule.Condition;
import com.sunday.engine.rule.Tracer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DataCondition<T extends Data> extends Condition<T> {

    protected List<Predicate<T>> predicates = new ArrayList<>();

    public DataCondition(T t, Signal... signals) {
        setData(t);
        setSignals(signals);
    }

    protected DataCondition() {
    }

    protected void addPredicate(Predicate<T> predicate) {
        predicates.add(predicate);
    }

    @Override
    protected boolean isSatisfied() {
        List<Boolean> result = new ArrayList<>();
        result.add(true);
        predicates.forEach(predicate -> result.add(predicate.test(getData())));
        return result.stream().reduce(((aBoolean, aBoolean2) -> aBoolean & aBoolean2)).get();
    }

    @Override
    protected void bindWith(SystemPort systemPort) {
        getTracers().clear();
        getSignals().forEach(signal -> {
            Tracer tracer = new Tracer(this, getData(), signal);
            getTracers().add(tracer);
        });
        getTracers().forEach(tracer -> {
            systemPort.addConnection(getData(), tracer);
        });
        generateMainInfo();
    }
}
