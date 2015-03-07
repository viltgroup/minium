/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.web.internal.expression;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class CoercerTest {

    @Test
    public void testPrimitiveTypeCoercer() {
        // given
        PrimitiveTypeCoercer coercer  = new PrimitiveTypeCoercer();

        // then

        // handles
        assertThat(coercer.handles(null, Boolean.TYPE), is(true));
        assertThat(coercer.handles(null, Short.TYPE), is(true));
        assertThat(coercer.handles(null, Byte.TYPE), is(true));
        assertThat(coercer.handles(null, Integer.TYPE), is(true));
        assertThat(coercer.handles(null, Long.TYPE), is(true));
        assertThat(coercer.handles(null, Float.TYPE), is(true));
        assertThat(coercer.handles(null, Double.TYPE), is(true));
        assertThat(coercer.handles(null, Character.TYPE), is(true));

        assertThat(coercer.handles(null, Boolean.class), is(true));
        assertThat(coercer.handles(null, Short.class), is(true));
        assertThat(coercer.handles(null, Byte.class), is(true));
        assertThat(coercer.handles(null, Integer.class), is(true));
        assertThat(coercer.handles(null, Long.class), is(true));
        assertThat(coercer.handles(null, Float.class), is(true));
        assertThat(coercer.handles(null, Double.class), is(true));
        assertThat(coercer.handles(null, Character.class), is(true));

        assertThat(coercer.handles(null, String.class), is(false));
        assertThat(coercer.handles(null, List.class), is(false));

        // coerce

        // boolean
        assertThat(coercer.coerce(null, Boolean.TYPE), equalTo((Object) Boolean.FALSE));
        assertThat(coercer.coerce("true", Boolean.class), equalTo((Object) Boolean.TRUE));
        assertThat(coercer.coerce("foobar", Boolean.class), equalTo((Object) Boolean.FALSE));
        assertThat(coercer.coerce(1, Boolean.class), equalTo((Object) Boolean.TRUE));
        assertThat(coercer.coerce(0, Boolean.class), equalTo((Object) Boolean.FALSE));
        assertThat(coercer.coerce(0.1d, Boolean.class), equalTo((Object) Boolean.TRUE));
        assertThat(coercer.coerce(0d, Boolean.class), equalTo((Object) Boolean.FALSE));

        // short
        assertThat(coercer.coerce(null, Short.TYPE), equalTo((Object) (short) 0));
        assertThat(coercer.coerce("1", Short.class), equalTo((Object) (short) 1));
        assertThat(coercer.coerce("2", Short.class), equalTo((Object) (short) 2));
        assertThat(coercer.coerce(1, Short.class), equalTo((Object) (short) 1));
        assertThat(coercer.coerce(0, Short.class), equalTo((Object) (short) 0));
        assertThat(coercer.coerce(0.1d, Short.class), equalTo((Object) (short) 0));

        // byte
        assertThat(coercer.coerce(null, Byte.TYPE), equalTo((Object) (byte) 0));
        assertThat(coercer.coerce("1", Byte.class), equalTo((Object) (byte) 1));
        assertThat(coercer.coerce("2", Byte.class), equalTo((Object) (byte) 2));
        assertThat(coercer.coerce(1, Byte.class), equalTo((Object) (byte) 1));
        assertThat(coercer.coerce(0, Byte.class), equalTo((Object) (byte) 0));
        assertThat(coercer.coerce(0.1d, Byte.class), equalTo((Object) (byte) 0));

        // int
        assertThat(coercer.coerce(null, Integer.TYPE), equalTo((Object) 0));
        assertThat(coercer.coerce("1", Integer.class), equalTo((Object) 1));
        assertThat(coercer.coerce("2", Integer.class), equalTo((Object) 2));
        assertThat(coercer.coerce(1, Integer.class), equalTo((Object) 1));
        assertThat(coercer.coerce(0, Integer.class), equalTo((Object) 0));
        assertThat(coercer.coerce(0.1d, Integer.class), equalTo((Object) 0));

        // long
        assertThat(coercer.coerce(null, Long.TYPE), equalTo((Object) 0L));
        assertThat(coercer.coerce("1", Long.class), equalTo((Object) 1L));
        assertThat(coercer.coerce("2", Long.class), equalTo((Object) 2L));
        assertThat(coercer.coerce(1, Long.class), equalTo((Object) 1L));
        assertThat(coercer.coerce(0, Long.class), equalTo((Object) 0L));
        assertThat(coercer.coerce(0.1d, Long.class), equalTo((Object) 0L));

        // float
        assertThat(coercer.coerce(null, Float.TYPE), equalTo((Object) 0f));
        assertThat(coercer.coerce("1", Float.class), equalTo((Object) 1f));
        assertThat(coercer.coerce("2.1", Float.class), equalTo((Object) 2.1f));
        assertThat(coercer.coerce(1, Float.class), equalTo((Object) 1f));
        assertThat(coercer.coerce(0, Float.class), equalTo((Object) 0f));
        assertThat(coercer.coerce(0.1d, Float.class), equalTo((Object) 0.1f));

        // double
        assertThat(coercer.coerce(null, Double.TYPE), equalTo((Object) 0d));
        assertThat(coercer.coerce("1", Double.class), equalTo((Object) 1d));
        assertThat(coercer.coerce("2.1", Double.class), equalTo((Object) 2.1d));
        assertThat(coercer.coerce(1, Double.class), equalTo((Object) 1d));
        assertThat(coercer.coerce(0, Double.class), equalTo((Object) 0d));
        assertThat((Double) coercer.coerce(0.1f, Double.class), closeTo(0.1d, 0.0001d));

        // char
        assertThat(coercer.coerce(null, Character.TYPE), equalTo((Object) (char) 0));
        assertThat(coercer.coerce("1", Character.class), equalTo((Object) '1'));
        assertThat(coercer.coerce(64, Character.class), equalTo((Object) (char) 64));
        assertThat(coercer.coerce(0.1f, Character.class), equalTo((Object) (char) 0));
        try {
            coercer.coerce("2.1", Character.class);
            fail();
        } catch (CannotCoerceException e) {
            // ok
        }
    }

    @Test
    public void testIdentityCoercer() {
        IdentityCoercer coercer  = new IdentityCoercer();

        // then

        // handles
        assertThat(coercer.handles(null, String.class), is(true));
        assertThat(coercer.handles("a string", String.class), is(true));
        assertThat(coercer.handles(Lists.newArrayList(1, 2, 3), List.class), is(true));
        assertThat(coercer.handles(Lists.newArrayList(1, 2, 3), Set.class), is(false));

        // coerce
        assertThat(coercer.coerce(null, String.class), nullValue());
        assertThat(coercer.coerce("a string", String.class), is((Object) "a string"));
        assertThat(coercer.coerce(Lists.newArrayList(1, 2, 3), List.class), equalTo((Object) Lists.newArrayList(1, 2, 3)));
    }

    public static class Constant {
        public String name;
        public double value;

        public Constant() {
        }

        public Constant(String name, double value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Constant)) return false;
            Constant other = (Constant) obj;
            return Objects.equal(name, other.name) && Objects.equal(value, other.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name, value);
        }
    }

    @Test
    public void testJsonCoercer() {
        // given

        JsonCoercer coercer  = new JsonCoercer();

        // then

        // coerce
        assertThat(coercer.coerce("\"a string\"", String.class), is((Object) "a string"));
        assertThat(coercer.coerce("[ 1, 2, 3 ]", List.class), equalTo((Object) Lists.newArrayList(1, 2, 3)));
        assertThat(coercer.coerce("[ 1, 2, 3 ]", int[].class), equalTo((Object) new int[] { 1, 2, 3 }));
        assertThat(coercer.coerce("{ \"name\" : \"pi\", \"value\" : 3.1415 }", Constant.class), equalTo((Object) new Constant("pi", 3.1415d)));
        try {
            coercer.coerce("a string", String.class);
            fail();
        } catch (CannotCoerceException e) {
            // ok
        }
    }

    @Test
    public void testCompositeCoercer() {
        // given
        Coercer coercer1 = mock(Coercer.class);
        Coercer coercer2 = mock(Coercer.class);
        when(coercer1.handles("string 1", String.class)).thenReturn(true);
        when(coercer1.handles("string 2", String.class)).thenReturn(false);
        when(coercer2.handles("string 1", String.class)).thenReturn(true);
        when(coercer2.handles("string 2", String.class)).thenReturn(true);

        when(coercer1.coerce("string 1", String.class)).thenReturn(1);
        when(coercer2.coerce("string 1", String.class)).thenReturn(2);

        Coercer coercer  = new Coercer.Composite()
            .add(coercer1)
            .add(coercer2); // coercer2 has priority over coercer 1

        // then

        // handles
        assertThat(coercer.handles("string 1", String.class), is(true));
        assertThat(coercer.handles("string 2", String.class), is(true));
        assertThat(coercer.handles("string 3", String.class), is(false));

        // coerce
        assertThat(coercer.coerce("string 1", String.class), is((Object) 2));
    }
}
