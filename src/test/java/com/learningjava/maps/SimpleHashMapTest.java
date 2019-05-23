package com.learningjava.maps;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lyning
 */
public class SimpleHashMapTest {

    private SimpleHashMap<Integer, Integer> map;

    @BeforeEach
    public void setUp() throws Exception {
        // given
        this.map = new SimpleHashMap<>();
    }

    /************ size test start **********/
    @Test
    @DisplayName("given empty entries" +
            "when call size() " +
            "then return 0")
    public void size1() {
        // when
        int size = map.size();
        // then
        assertThat(size).isZero();
    }

    @Test
    @DisplayName("given multiple entries(contains duplicate key) " +
            "when call size() " +
            "then return correct size")
    public void size2() {
        // given
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(3, 4);
        map.put(3, 5);
        map.put(4, 4);
        map.put(5, 5);
        map.remove(1);
        map.remove(2);
        // when
        int size = map.size();
        // then
        assertThat(size).isEqualTo(3);
    }

    @Test
    @DisplayName("given multiple entries(hash conflict) " +
            "when call size() " +
            "then return correct size")
    public void size3() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(4), 4);
        map.put(new HashConflict(5), 5);
        map.remove(new HashConflict(5));
        map.remove(new HashConflict(3));
        // when
        int size = map.size();
        // then
        assertThat(size).isEqualTo(3);
    }
    /************ size test end **********/


    /************ put test start **********/
    @Test
    @DisplayName("given empty entries " +
            "when put one entry " +
            "then return size 1")
    public void put1() {
        // when
        map.put(1, 1);
        // then
        assertThat(map.size()).isOne();
    }

    @Test
    @DisplayName("given empty entries " +
            "when put two entries(duplicate key) " +
            "then return size 1")
    public void put2() {
        // when
        map.put(1, 1);
        map.put(1, 2);
        // then
        assertThat(map.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("given empty entries " +
            "when put three entries " +
            "then return size 3")
    public void put3() {
        // when
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        // then
        assertThat(map.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("should return value " +
            "when call put")
    public void put4() {
        // when
        Integer value = map.put(1, 1);
        // then
        assertThat(value).isEqualTo(1);
    }

    @Test
    @DisplayName("" +
            "given empty entries " +
            "when put multiples entries(hash conflict) " +
            "then")
    public void put5() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        // when
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(3), 4);
        map.put(new HashConflict(3), 5);
        map.put(new HashConflict(4), 4);
        map.put(new HashConflict(5), 5);
        // then
        assertThat(Lists.newArrayList(map.values())).isEqualTo(Lists.list(1, 2, 5, 4, 5));
    }

    @Test
    @DisplayName("should auto grow " +
            "when capacity exceed threshold")
    public void put6() {
        // given default threshold = 8
        // when
        for (int i = 1; i <= 20; i++) {
            map.put(i, i);
        }
        // then
        assertThat(map.size()).isEqualTo(20);
        assertThat(map.get(20)).isEqualTo(20);
    }
    /************ put test end **********/

    /************ get test start **********/
    @Test
    @DisplayName("given empty entries" +
            "when get by null key" +
            "then return null")
    public void get1() {
        // when
        Integer value = map.get(null);
        // then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("given empty entries" +
            "when get value by not exist key" +
            "then return null")
    public void get2() {
        // when
        Integer value = map.get(2);
        // then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("given entry" +
            "when get value by not exist key" +
            "then return null")
    public void get3() {
        // given
        map.put(1, 1);
        // when
        Integer value = map.get(2);
        // then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("given entry" +
            "when get value" +
            "then return value")
    public void get4() {
        // given
        map.put(1, 1);
        // when
        Integer value = map.get(1);
        // then
        assertThat(value).isEqualTo(1);
    }

    @Test
    @DisplayName("given multiple entries(hash conflict)" +
            "when get value by hash conflict key" +
            "then return value")
    public void get5() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(3), 4);
        map.put(new HashConflict(3), 5);
        map.put(new HashConflict(4), 4);
        map.put(new HashConflict(5), 5);
        // when
        Integer value = map.get(new HashConflict(3));
        // then
        assertThat(value).isEqualTo(5);
    }

    @Test
    @DisplayName("given multiple entries(hash conflict)" +
            "when get value by not exist hash conflict key" +
            "then return null")
    public void get6() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(4), 4);
        map.put(new HashConflict(5), 5);
        // when
        Integer value = map.get(new HashConflict(6));
        // then
        assertThat(value).isNull();
    }
    /************ get test end **********/


    /************ remove test start **********/
    @Test
    @DisplayName("given empty entries" +
            "when remove by null key" +
            "then return null")
    public void remove1() {
        // when
        Integer value = map.remove(null);
        // then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("given entry" +
            "when remove by null key" +
            "then return null")
    public void remove2() {
        // given
        map.put(1, 1);
        // when
        Integer value = map.remove(null);
        // then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("given entry" +
            "when remove by key" +
            "then return value")
    public void remove3() {
        // given
        map.put(1, 1);
        // when
        int value = map.remove(1);
        // then
        assertThat(value).isEqualTo(1);
    }

    @Test
    @DisplayName("given entry" +
            "when remove by not exist key" +
            "then return null")
    public void remove4() {
        // given
        map.put(1, 1);
        // when
        Integer value = map.remove(2);
        // then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("given multiple entries(hash conflict)" +
            "when remove by hash conflict key" +
            "then return value")
    public void remove5() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(4), 4);
        map.put(new HashConflict(5), 5);
        // when
        Integer value = map.remove(new HashConflict(3));
        // then
        assertThat(value).isEqualTo(3);
        assertThat(Lists.newArrayList(map.values())).isEqualTo(Lists.list(1, 2, 4, 5));
    }
    /************ remove test end **********/


    /************ values test start **********/
    @Test
    @DisplayName("given empty entries" +
            "when call values" +
            "then return empty values")
    public void values1() {
        // when
        Iterable<Integer> values = map.values();
        // then
        assertThat(values).isEmpty();
    }

    @Test
    @DisplayName("given multiple entries" +
            "when call values" +
            "then return all values")
    public void values2() {
        // given
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(3, 4);
        map.put(4, 4);
        map.remove(4);
        // when
        Iterable<Integer> values = map.values();
        // then
        assertThat(values.spliterator().estimateSize()).isEqualTo(3);
        assertThat(Lists.newArrayList(values)).isEqualTo(Lists.list(1, 2, 4));
    }
    /************ values test end **********/


    /************ containsKey test start **********/
    @Test
    @DisplayName("given entry" +
            "when key exist" +
            "then return true")
    public void contains_key1() {
        // given
        map.put(1, 1);
        // when
        boolean result = map.containsKey(1);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("given entry" +
            "when key not exist" +
            "then return false")
    public void containsKey2() {
        // given
        map.put(1, 1);
        // when
        boolean result = map.containsKey(2);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("given multiple entries(hash conflict)" +
            "when call containsKey" +
            "then return correct result")
    public void containsKey3() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(4), 4);
        map.put(new HashConflict(5), 5);
        // then
        assertThat(map.containsKey(new HashConflict(3))).isTrue();
        assertThat(map.containsKey(new HashConflict(5))).isTrue();
        assertThat(map.containsKey(new HashConflict(6))).isFalse();
    }
    /************ containsKey test end **********/


    /************ forEach test start **********/
    @Test
    @DisplayName("given multiple entries" +
            "when call forEach" +
            "then pass")
    public void forEach1() {
        // given
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        // when
        List<Integer> results = new ArrayList<>();
        map.forEach((key) -> results.add(map.get(key)));
        // then
        assertThat(results).isEqualTo(Lists.list(1, 2, 3, 4));
    }

    @Test
    @DisplayName("given multiple entries(hash conflict)" +
            "when call forEach" +
            "then pass")
    public void forEach2() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(4), 4);
        map.put(new HashConflict(5), 5);
        // when
        List<Integer> results = new ArrayList<>();
        map.forEach((key) -> results.add(map.get(key)));
        // then
        assertThat(results).isEqualTo(Lists.list(1, 2, 3, 4, 5));
    }

    /************ forEach test end **********/

    class HashConflict {
        private int field;

        HashConflict(int field) {
            this.field = field;
        }

        @Override
        public int hashCode() {
            return this.field <= 8 ? 1 : this.field;
        }

        @Override
        public boolean equals(Object obj) {
            return ((HashConflict) obj).field == this.field;
        }
    }
}