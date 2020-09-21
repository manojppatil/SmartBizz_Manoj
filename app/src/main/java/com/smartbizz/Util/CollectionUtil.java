package com.smartbizz.Util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
    private CollectionUtil() {}

    /**
     * Returns true if the given list is empty, or false
     *
     * @param list The list object
     * @return true if the given list is empty, or false
     */
    public static <E> boolean isEmpty(List<E> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Returns true if the given list is empty, or false
     *
     * @param array The array of objects
     * @return true if the given list is empty, or false
     */
    public static <E> boolean isEmpty(E[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Returns the size of the given collection object
     *
     * @param array The collection object
     * @return The integer size of list
     */
    public static <E> int length(E[] array) {
        if (isEmpty(array)) {
            return 0;
        }
        return array.length;
    }

    /**
     * Returns the size of the given collection object
     *
     * @param list The list
     * @return The integer size of list
     */
    public static <E> int size(List<E> list) {
        if (isEmpty(list)) {
            return 0;
        }
        return list.size();
    }

    /**
     * Returns the size of the given collection object
     *
     * @param list The list
     * @return The integer size of list
     */
    public static <E> int indexOf(List<E> list, E e) {
        if (isEmpty(list) || e == null) {
            return 0;
        }
        return list.indexOf(e);
    }

    /**
     * Removes all elements from this List, leaving it empty.
     *
     * @param list The list object
     */
    public static <E> void clear(List<E> list) {
        if (isEmpty(list)) {
            return;
        }
        list.clear();
    }

    /**
     * Returns the element at the specified location in the given List.
     *
     * @param list the collection object from which we need to get the element.
     * @param location the index of the element to return.
     * @return the element at the specified location.
     */
    public static <E> E get(List<E> list, int location) {
        if (isEmpty(list) || location >= list.size()) {
            return null;
        }
        return list.get(location);
    }

    /**
     * Returns the element at the specified location in the given List.
     *
     * @param list the collection object from which we need to get the element.
     * @param e the element at the specified location.
     */
    public static <E> void add(List<E> list, E e) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(e);
    }

    /**
     * Returns the element at the specified location in the given List.
     *
     * @param list the collection object from which we need to get the element.
     * @param e the element at the specified location.
     */
    public static <E> void add(List<E> list, int index, E e) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (index < size(list)) {
            list.add(index, e);
        }
    }

    /**
     * Removed the element in the given index from the given collection object.
     *
     * @param list the collection object from which we need to get the element.
     * @param index the index of the element to remove.
     */
    public static <E> void remove(List<E> list, int index) {
        if (index < 0 || isEmpty(list) || index >= list.size()) {
            return;
        }
        list.remove(index);
    }

    /**
     * Removed the element in the given index from the given collection object.
     *
     * @param list the collection object from which we need to get the element.
     * @param e the element to remove.
     */
    public static <E> void remove(List<E> list, E e) {
        if (isEmpty(list)) {
            return;
        }
        list.remove(e);
    }
}
