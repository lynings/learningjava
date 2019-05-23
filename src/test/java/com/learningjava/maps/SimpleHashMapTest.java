package com.learningjava.maps;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lyning
 */
public class SimpleHashMapTest {

    private SimpleHashMap<Integer, Integer> map;

    @Before
    public void setUp() throws Exception {
        // given
        this.map = new SimpleHashMap<>();
    }

    /************ size test start **********/
    @Test
    public void should_return_size_0_when_init() {
        // when
        int size = map.size();
        // then
        assertThat(size).isZero();
    }

    @Test
    public void test_size_when_hash_conflict() {
        // given
        SimpleHashMap<HashConflict, Integer> map = new SimpleHashMap<>();
        map.put(new HashConflict(1), 1);
        map.put(new HashConflict(2), 2);
        map.put(new HashConflict(3), 3);
        map.put(new HashConflict(3), 4);
        map.put(new HashConflict(3), 5);
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
    public void should_grow_when_call_put_and_exceed_threshold() {
        // when
        for (int i = 0; i < 17; i++) {
            map.put(i, i);
        }
        // then
        assertThat(map.size()).isEqualTo(17);
        assertThat(map.get(1)).isEqualTo(1);
    }

    @Test
    public void should_return_size_1_when_call_once_put() {
        // when
        map.put(1, 1);
        int size = map.size();
        // then
        assertThat(size).isOne();
    }

    @Test
    public void should_return_size_1_when_call_twice_put_contains_duplicated_key() {
        // when
        map.put(1, 1);
        map.put(1, 2);
        int size = map.size();
        // then
        assertThat(size).isEqualTo(1);
    }

    @Test
    public void should_return_2_when_call_twice_put() {
        // when
        map.put(1, 1);
        map.put(2, 2);
        int size = map.size();
        // then
        assertThat(size).isEqualTo(2);
    }

    @Test
    public void should_return_value_when_call_put() {
        // when
        Integer value = map.put(1, 1);
        // then
        assertThat(value).isEqualTo(1);
    }

    @Test
    public void test_put_when_hash_conflict() {
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
    /************ put test end **********/

    /************ get test start **********/
    @Test
    public void should_return_null_when_call_get() {
        // given
        map.put(1, 1);
        // when
        Integer value = map.get(2);
        // then
        assertThat(value).isNull();
    }

    @Test
    public void should_return_value_when_call_get() {
        // given
        map.put(1, 1);
        // when
        Integer value = map.get(1);
        // then
        assertThat(value).isEqualTo(1);
    }

    @Test
    public void test_get_when_hash_conflict() {
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
    /************ get test end **********/


    /************ remove test start **********/
    @Test
    public void should_return_value_when_call_remove() {
        // given
        map.put(1, 1);
        // when
        int value = map.remove(1);
        // then
        assertThat(value).isEqualTo(1);
        assertThat(map.size()).isZero();
        assertThat(map.get(1)).isNull();
    }

    @Test
    public void should_return_null_when_remove_not_existed_key() {
        // given
        map.put(1, 1);
        // when
        Integer value = map.remove(2);
        // then
        assertThat(value).isNull();
        assertThat(map.size()).isOne();
        assertThat(map.get(1)).isEqualTo(1);
    }

    @Test
    public void test_remove_when_hash_conflict() {
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
        assertThat(map.size()).isEqualTo(4);
    }
    /************ remove test end **********/


    /************ values test start **********/
    @Test
    public void should_return_correct_values() {
        // given
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(3, 3);
        map.put(4, 4);
        map.remove(4);
        // when
        Iterable<Integer> values = map.values();
        // then
        assertThat(values.spliterator().estimateSize()).isEqualTo(3);
        assertThat(Lists.newArrayList(values)).isEqualTo(Lists.list(1, 2, 3));
    }
    /************ values test end **********/


    /************ containsKey test start **********/
    @Test
    public void should_return_true_when_key_existed() {
        // given
        map.put(1, 1);
        // when
        boolean result = map.containsKey(1);
        // then
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_false_when_key_not_exist() {
        // given
        map.put(1, 1);
        // when
        boolean result = map.containsKey(2);
        // then
        assertThat(result).isFalse();
    }
    /************ containsKey test end **********/


    /************ forEach test start **********/
    @Test
    public void test_foreach() {
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

    /************ forEach test end **********/

    class HashConflict {
        private int field;

        public HashConflict(int field) {
            this.field = field;
        }

        @Override
        public int hashCode() {
            return this.field <= 3 ? 1 : this.field;
        }

        @Override
        public boolean equals(Object obj) {
            return ((HashConflict) obj).field == this.field;
        }
    }
}