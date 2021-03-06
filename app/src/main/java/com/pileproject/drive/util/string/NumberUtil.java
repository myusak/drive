/*
 * Copyright (C) 2011-2015 PILE Project, Inc. <dev@pileproject.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pileproject.drive.util.string;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {

    private NumberUtil() {
        throw new AssertionError("This class cannot be instantiated");
    }

    /**
     * Converts numerical value to string expression. This function takes locales into account and
     * uses default locale by calling {@link Locale#getDefault()}.
     * With <code>precision</code> in the arguments you can control the number of digits in fraction
     * part.
     *
     * @param value value to be converted as a string
     * @param precision number of precision.
     * @param <T> type parameter of <code>value</code>, which should be extends {@link Number}
     * @return string expression of <code>value</code> in appropriate <code>locale</code> and
     *         with <code>precision</code>
     */
    public static <T extends Number> String toString(T value, int precision) {
        return toString(Locale.getDefault(), value, precision);
    }

    /**
     * Converts numerical value to string expression. This function takes locales into account.
     * With <code>precision</code> in the arguments you can control the number of digits in fraction
     * part.
     *
     * @param locale locale in which the value will be expressed.
     * @param value value to be converted as a string
     * @param precision number of precision.
     * @param <T> type parameter of <code>value</code>, which should be extends {@link Number}
     * @return string expression of <code>value</code> in appropriate <code>locale</code> and
     *         with <code>precision</code>
     */
    public static <T extends Number> String toString(Locale locale, T value, int precision) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(locale);

        decimalFormat.setMaximumFractionDigits(precision);
        decimalFormat.setMinimumFractionDigits(precision);

        return decimalFormat.format(value);
    }
}
