package com.russelarms.offsetanimator.util;

import java.util.Collection;

public class CollectionSizeAdjuster<T> {

    private final Collection<T> collection;
    private final UnitFactory<T> unitFactory;

    public CollectionSizeAdjuster(Collection<T> collection, UnitFactory<T> unitFactory) {
        this.collection = collection;
        this.unitFactory = unitFactory;
    }

    public void adjustSizeIfNeed(int requiredNumber) {
        if (requiredNumber < 0) {
            throw new ArrayIndexOutOfBoundsException("index out of bounds");
        }
        if (requiredNumber > collection.size() - 1) {
            addUnits(requiredNumber);
        }
    }

    private void addUnits(int requiredNumber) {
        int diff = requiredNumber - (collection.size() - 1);
        for (int i = 0; i < diff; i++) {
            collection.add(unitFactory.init());
        }
    }

    public interface UnitFactory<T> {
        T init();
    }

}
