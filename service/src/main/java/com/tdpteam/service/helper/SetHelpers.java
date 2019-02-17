package com.tdpteam.service.helper;

import java.util.Iterator;

public class SetHelpers {
    public static <T> T getFirstElement(final Iterable<T> elements) {
        if (elements == null)
            return null;

        return elements.iterator().next();
    }

    public static <T> T getLastElement(final Iterable<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while(itr.hasNext()) {
            lastElement=itr.next();
        }

        return lastElement;
    }
}
