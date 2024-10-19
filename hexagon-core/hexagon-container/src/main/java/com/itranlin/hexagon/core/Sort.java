package com.itranlin.hexagon.core;

/**
 * The interface Sort.
 */
@SuppressWarnings("unused")
public interface Sort extends Comparable<Sort> {

    /**
     * 获得 sortNo.
     *
     * @return sortNo
     */
    int getSort();

    @Override
    default int compareTo(Sort o) {
        return o.getSort() - getSort();
    }
}
