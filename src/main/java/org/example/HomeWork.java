package org.example;


import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class HomeWork {

    private TreeMap<Integer, Integer> ranges = new TreeMap<>();
    private TreeMap<Integer, Integer> lengths = new TreeMap<>();

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу Step из файла contest6_tasks.pdf
     */
    @SneakyThrows
    public void stepDanceValue(InputStream in, OutputStream out) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        PrintWriter writer = new PrintWriter(out);

        String[] firstLine = reader.readLine().split(" ");
        int N = Integer.parseInt(firstLine[0]);
        int Q = Integer.parseInt(firstLine[1]);

        for (int i = 0; i < N; i++) {
            addRange(i, i + 1);
        }

        for (int i = 0; i < Q; i++) {
            int v = Integer.parseInt(reader.readLine()) - 1;

            Map.Entry<Integer, Integer> p = ranges.higherEntry(v);
            if (p == null) {
                continue;
            }

            Map.Entry<Integer, Integer> pn = ranges.higherEntry(p.getKey());
            Map.Entry<Integer, Integer> pp = ranges.lowerEntry(p.getKey());

            int a = p.getValue();
            int b = p.getKey();
            int nexta = a, nextb = b;
            if (a != 0) {
                nexta = ranges.get(a);
            }
            if (b != N) {
                nextb = pn != null ? pn.getKey() : N;
            }

            removeRange(p);

            if (a != 0 && v == a) {
                removeRange(pp);
            }
            if (b != N && v + 1 == b) {
                removeRange(pn);
            }

            if (v == a && v + 1 == b) {
                addRange(nexta, nextb);
            } else if (v == a) {
                addRange(nexta, v + 1);
                addRange(v + 1, b);
            } else if (v + 1 == b) {
                addRange(a, v);
                addRange(v, nextb);
            } else {
                addRange(a, v);
                addRange(v, v + 1);
                addRange(v + 1, b);
            }

            writer.println(lengths.lastKey());
        }

        writer.flush();
    }

    private void removeRange(Map.Entry<Integer, Integer> entry) {
        int length = entry.getKey() - entry.getValue();
        int count = lengths.get(length);
        if (count == 1) {
            lengths.remove(length);
        } else {
            lengths.put(length, count - 1);
        }
        ranges.remove(entry.getKey());
    }

    private void addRange(int a, int b) {
        int length = b - a;
        lengths.put(length, lengths.getOrDefault(length, 0) + 1);
        ranges.put(b, a);
    }
}