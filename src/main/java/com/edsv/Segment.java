package com.edsv;

public record Segment(int length, int openEnds) implements Comparable<Segment> {
    @Override
    public int compareTo(Segment o) {
        if (length == o.length) {
            return Integer.compare(openEnds, o.openEnds);
        }
        return Integer.compare(length, o.length);
    }
}
