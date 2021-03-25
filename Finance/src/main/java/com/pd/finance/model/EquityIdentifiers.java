package com.pd.finance.model;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class EquityIdentifiers {

    private Set<EquityIdentifier> identifiers = new HashSet<>();

    public Set<EquityIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Set<EquityIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public EquityIdentifier getEquityIdentifier(String source){
        AtomicReference<EquityIdentifier> reference = new AtomicReference<>();
        Optional<EquityIdentifier> first = identifiers.stream().filter(x -> x.getSource().equals(source)).findFirst();
        first.ifPresent(identifier -> reference.set(identifier));

        return reference.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquityIdentifiers that = (EquityIdentifiers) o;
        return identifiers.equals(that.identifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiers);
    }

    @Override
    public String toString() {
        return "EquityIdentifiers{" +
                "identifiers=" + identifiers +
                '}';
    }
}
