/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash.Strategy;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ 
/*      */ public class ShortBigArrays
/*      */ {
/*      */   public static final long ONEOVERPHI = 106039L;
/*   84 */   public static final short[][] EMPTY_BIG_ARRAY = new short[0][];
/*      */ 
/*  564 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
/*      */   private static final int SMALL = 7;
/*      */   private static final int MEDIUM = 40;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 2;
/*      */ 
/*      */   public static short get(short[][] array, long index)
/*      */   {
/*   92 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
/*      */   }
/*      */ 
/*      */   public static void set(short[][] array, long index, short value)
/*      */   {
/*  100 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
/*      */   }
/*      */ 
/*      */   public static void swap(short[][] array, long first, long second)
/*      */   {
/*  109 */     short t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  110 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  111 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
/*      */   }
/*      */ 
/*      */   public static void add(short[][] array, long index, short incr)
/*      */   {
/*      */     int tmp10_7 = BigArrays.displacement(index);
/*      */     short[] tmp10_5 = array[BigArrays.segment(index)]; tmp10_5[tmp10_7] = ((short)(tmp10_5[tmp10_7] + tmp10_7));
/*      */   }
/*      */ 
/*      */   public static void mul(short[][] array, long index, short factor)
/*      */   {
/*      */     int tmp10_7 = BigArrays.displacement(index);
/*      */     short[] tmp10_5 = array[BigArrays.segment(index)]; tmp10_5[tmp10_7] = ((short)(tmp10_5[tmp10_7] * tmp10_7));
/*      */   }
/*      */ 
/*      */   public static void incr(short[][] array, long index)
/*      */   {
/*      */     int tmp10_7 = BigArrays.displacement(index);
/*      */     short[] tmp10_5 = array[BigArrays.segment(index)]; tmp10_5[tmp10_7] = ((short)(tmp10_5[tmp10_7] + 1));
/*      */   }
/*      */ 
/*      */   public static void decr(short[][] array, long index)
/*      */   {
/*      */     int tmp10_7 = BigArrays.displacement(index);
/*      */     short[] tmp10_5 = array[BigArrays.segment(index)]; tmp10_5[tmp10_7] = ((short)(tmp10_5[tmp10_7] - 1));
/*      */   }
/*      */ 
/*      */   public static long length(short[][] array)
/*      */   {
/*  153 */     int length = array.length;
/*  154 */     return length == 0 ? 0L : BigArrays.start(length - 1) + array[(length - 1)].length;
/*      */   }
/*      */ 
/*      */   public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length)
/*      */   {
/*  166 */     if (destPos <= srcPos) {
/*  167 */       int srcSegment = BigArrays.segment(srcPos);
/*  168 */       int destSegment = BigArrays.segment(destPos);
/*  169 */       int srcDispl = BigArrays.displacement(srcPos);
/*  170 */       int destDispl = BigArrays.displacement(destPos);
/*      */ 
/*  172 */       while (length > 0L) {
/*  173 */         int l = (int)Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
/*  174 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  175 */         if (srcDispl += l == 134217728) {
/*  176 */           srcDispl = 0;
/*  177 */           srcSegment++;
/*      */         }
/*  179 */         if (destDispl += l == 134217728) {
/*  180 */           destDispl = 0;
/*  181 */           destSegment++;
/*      */         }
/*  183 */         length -= l;
/*      */       }
/*      */     }
/*      */     else {
/*  187 */       int srcSegment = BigArrays.segment(srcPos + length);
/*  188 */       int destSegment = BigArrays.segment(destPos + length);
/*  189 */       int srcDispl = BigArrays.displacement(srcPos + length);
/*  190 */       int destDispl = BigArrays.displacement(destPos + length);
/*      */ 
/*  192 */       while (length > 0L) {
/*  193 */         if (srcDispl == 0) {
/*  194 */           srcDispl = 134217728;
/*  195 */           srcSegment--;
/*      */         }
/*  197 */         if (destDispl == 0) {
/*  198 */           destDispl = 134217728;
/*  199 */           destSegment--;
/*      */         }
/*  201 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  202 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  203 */         srcDispl -= l;
/*  204 */         destDispl -= l;
/*  205 */         length -= l;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length)
/*      */   {
/*  218 */     int srcSegment = BigArrays.segment(srcPos);
/*  219 */     int srcDispl = BigArrays.displacement(srcPos);
/*      */ 
/*  221 */     while (length > 0) {
/*  222 */       int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
/*  223 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  224 */       if (srcDispl += l == 134217728) {
/*  225 */         srcDispl = 0;
/*  226 */         srcSegment++;
/*      */       }
/*  228 */       destPos += l;
/*  229 */       length -= l;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length)
/*      */   {
/*  241 */     int destSegment = BigArrays.segment(destPos);
/*  242 */     int destDispl = BigArrays.displacement(destPos);
/*      */ 
/*  244 */     while (length > 0L) {
/*  245 */       int l = (int)Math.min(destArray[destSegment].length - destDispl, length);
/*  246 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  247 */       if (destDispl += l == 134217728) {
/*  248 */         destDispl = 0;
/*  249 */         destSegment++;
/*      */       }
/*  251 */       srcPos += l;
/*  252 */       length -= l;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static short[][] newBigArray(long length)
/*      */   {
/*  261 */     if (length == 0L) return EMPTY_BIG_ARRAY;
/*  262 */     int baseLength = (int)((length + 134217727L) / 134217728L);
/*  263 */     short[][] base = new short[baseLength][];
/*  264 */     int residual = (int)(length & 0x7FFFFFF);
/*  265 */     if (residual != 0) {
/*  266 */       for (int i = 0; i < baseLength - 1; i++) base[i] = new short[134217728];
/*  267 */       base[(baseLength - 1)] = new short[residual];
/*      */     } else {
/*  269 */       for (int i = 0; i < baseLength; i++) base[i] = new short[134217728]; 
/*      */     }
/*  270 */     return base;
/*      */   }
/*      */ 
/*      */   public static short[][] wrap(short[] array)
/*      */   {
/*  280 */     if (array.length == 0) return EMPTY_BIG_ARRAY;
/*  281 */     if (array.length <= 134217728) return new short[][] { array };
/*  282 */     short[][] bigArray = newBigArray(array.length);
/*  283 */     for (int i = 0; i < bigArray.length; i++) System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, bigArray[i].length);
/*  284 */     return bigArray;
/*      */   }
/*      */ 
/*      */   public static short[][] ensureCapacity(short[][] array, long length)
/*      */   {
/*  301 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */   public static short[][] ensureCapacity(short[][] array, long length, long preserve)
/*      */   {
/*  316 */     long oldLength = length(array);
/*  317 */     if (length > oldLength) {
/*  318 */       int valid = array.length - ((array.length == 0) || ((array.length > 0) && (array[(array.length - 1)].length == 134217728)) ? 0 : 1);
/*  319 */       int baseLength = (int)((length + 134217727L) / 134217728L);
/*  320 */       short[][] base = (short[][])Arrays.copyOf(array, baseLength);
/*  321 */       int residual = (int)(length & 0x7FFFFFF);
/*  322 */       if (residual != 0) {
/*  323 */         for (int i = valid; i < baseLength - 1; i++) base[i] = new short[134217728];
/*  324 */         base[(baseLength - 1)] = new short[residual];
/*      */       } else {
/*  326 */         for (int i = valid; i < baseLength; i++) base[i] = new short[134217728]; 
/*      */       }
/*  327 */       if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L);
/*  328 */       return base;
/*      */     }
/*  330 */     return array;
/*      */   }
/*      */ 
/*      */   public static short[][] grow(short[][] array, long length)
/*      */   {
/*  351 */     long oldLength = length(array);
/*  352 */     return length > oldLength ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */   public static short[][] grow(short[][] array, long length, long preserve)
/*      */   {
/*  374 */     long oldLength = length(array);
/*  375 */     return length > oldLength ? ensureCapacity(array, Math.max(106039L * oldLength >>> 16, length), preserve) : array;
/*      */   }
/*      */ 
/*      */   public static short[][] trim(short[][] array, long length)
/*      */   {
/*  391 */     long oldLength = length(array);
/*  392 */     if (length >= oldLength) return array;
/*  393 */     int baseLength = (int)((length + 134217727L) / 134217728L);
/*  394 */     short[][] base = (short[][])Arrays.copyOf(array, baseLength);
/*  395 */     int residual = (int)(length & 0x7FFFFFF);
/*  396 */     if (residual != 0) base[(baseLength - 1)] = ShortArrays.trim(base[(baseLength - 1)], residual);
/*  397 */     return base;
/*      */   }
/*      */ 
/*      */   public static short[][] setLength(short[][] array, long length)
/*      */   {
/*  416 */     long oldLength = length(array);
/*  417 */     if (length == oldLength) return array;
/*  418 */     if (length < oldLength) return trim(array, length);
/*  419 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */   public static short[][] copy(short[][] array, long offset, long length)
/*      */   {
/*  429 */     ensureOffsetLength(array, offset, length);
/*  430 */     short[][] a = newBigArray(length);
/*      */ 
/*  432 */     copy(array, offset, a, 0L, length);
/*  433 */     return a;
/*      */   }
/*      */ 
/*      */   public static short[][] copy(short[][] array)
/*      */   {
/*  441 */     short[][] base = (short[][])array.clone();
/*  442 */     for (int i = base.length; i-- != 0; base[i] = ((short[])array[i].clone()));
/*  443 */     return base;
/*      */   }
/*      */ 
/*      */   public static void fill(short[][] array, short value)
/*      */   {
/*  454 */     for (int i = array.length; i-- != 0; ShortArrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */   public static void fill(short[][] array, long from, long to, short value)
/*      */   {
/*  468 */     long length = length(array);
/*  469 */     BigArrays.ensureFromTo(length, from, to);
/*  470 */     int fromSegment = BigArrays.segment(from);
/*  471 */     int toSegment = BigArrays.segment(to);
/*  472 */     int fromDispl = BigArrays.displacement(from);
/*  473 */     int toDispl = BigArrays.displacement(to);
/*  474 */     if (fromSegment == toSegment) {
/*  475 */       ShortArrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*  476 */       return;
/*      */     }
/*  478 */     if (toDispl != 0) ShortArrays.fill(array[toSegment], 0, toDispl, value); while (true) {
/*  479 */       toSegment--; if (toSegment <= fromSegment) break; ShortArrays.fill(array[toSegment], value);
/*  480 */     }ShortArrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */   public static boolean equals(short[][] a1, short[][] a2)
/*      */   {
/*  492 */     if (length(a1) != length(a2)) return false; short[] t;
/*      */     short[] u;
/*      */     int j;
/*      */     do { int i = a1.length;
/*      */ 
/*  499 */       while (j-- == 0)
/*      */       {
/*  495 */         if (i-- == 0) break;
/*  496 */         t = a1[i];
/*  497 */         u = a2[i];
/*  498 */         j = t.length;
/*      */       } } while (t[j] == u[j]); return false;
/*      */ 
/*  501 */     return true;
/*      */   }
/*      */ 
/*      */   public static String toString(short[][] a)
/*      */   {
/*  510 */     if (a == null) return "null";
/*  511 */     long last = length(a) - 1L;
/*  512 */     if (last == -1L) return "[]";
/*  513 */     StringBuilder b = new StringBuilder();
/*  514 */     b.append('[');
/*  515 */     for (long i = 0L; ; i += 1L) {
/*  516 */       b.append(String.valueOf(get(a, i)));
/*  517 */       if (i == last) return b.append(']').toString();
/*  518 */       b.append(", ");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void ensureFromTo(short[][] a, long from, long to)
/*      */   {
/*  532 */     BigArrays.ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */   public static void ensureOffsetLength(short[][] a, long offset, long length)
/*      */   {
/*  545 */     BigArrays.ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */   private static void vecSwap(short[][] x, long a, long b, long n)
/*      */   {
/*  568 */     for (int i = 0; i < n; b += 1L) { swap(x, a, b); i++; a += 1L; } 
/*      */   }
/*      */ 
/*  571 */   private static long med3(short[][] x, long a, long b, long c, ShortComparator comp) { int ab = comp.compare(get(x, a), get(x, b));
/*  572 */     int ac = comp.compare(get(x, a), get(x, c));
/*  573 */     int bc = comp.compare(get(x, b), get(x, c));
/*  574 */     return ac > 0 ? c : bc > 0 ? b : ab < 0 ? a : ac < 0 ? c : bc < 0 ? b : a;
/*      */   }
/*      */ 
/*      */   private static void selectionSort(short[][] a, long from, long to, ShortComparator comp)
/*      */   {
/*  579 */     for (long i = from; i < to - 1L; i += 1L) {
/*  580 */       long m = i;
/*  581 */       for (long j = i + 1L; j < to; j += 1L) if (comp.compare(get(a, j), get(a, m)) < 0) m = j;
/*  582 */       if (m != i) swap(a, i, m);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void quickSort(short[][] x, long from, long to, ShortComparator comp)
/*      */   {
/*  598 */     long len = to - from;
/*      */ 
/*  600 */     if (len < 7L) {
/*  601 */       for (long i = from; i < to; i += 1L)
/*  602 */         for (long j = i; (j > from) && (comp.compare(get(x, j - 1L), get(x, j)) > 0); j -= 1L) swap(x, j, j - 1L);
/*  603 */       return;
/*      */     }
/*      */ 
/*  606 */     long m = from + len / 2L;
/*  607 */     if (len > 7L) {
/*  608 */       long l = from;
/*  609 */       long n = to - 1L;
/*  610 */       if (len > 40L) {
/*  611 */         long s = len / 8L;
/*  612 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  613 */         m = med3(x, m - s, m, m + s, comp);
/*  614 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       }
/*  616 */       m = med3(x, l, m, n, comp);
/*      */     }
/*  618 */     short v = get(x, m);
/*      */ 
/*  620 */     long a = from; long b = a; long c = to - 1L; long d = c;
/*      */     while (true)
/*      */     {
/*      */       int comparison;
/*  623 */       if ((b <= c) && ((comparison = comp.compare(get(x, b), v)) <= 0)) {
/*  624 */         if (comparison == 0) swap(x, a++, b);
/*  625 */         b += 1L;
/*      */       }
/*      */       else
/*      */       {
/*      */         int comparison;
/*  627 */         while ((c >= b) && ((comparison = comp.compare(get(x, c), v)) >= 0)) {
/*  628 */           if (comparison == 0) swap(x, c, d--);
/*  629 */           c -= 1L;
/*      */         }
/*  631 */         if (b > c) break;
/*  632 */         swap(x, b++, c--);
/*      */       }
/*      */     }
/*  635 */     long n = to;
/*  636 */     long s = Math.min(a - from, b - a);
/*  637 */     vecSwap(x, from, b - s, s);
/*  638 */     s = Math.min(d - c, n - d - 1L);
/*  639 */     vecSwap(x, b, n - s, s);
/*      */ 
/*  641 */     if ((s = b - a) > 1L) quickSort(x, from, from + s, comp);
/*  642 */     if ((s = d - c) > 1L) quickSort(x, n - s, n, comp); 
/*      */   }
/*      */ 
/*      */   private static long med3(short[][] x, long a, long b, long c)
/*      */   {
/*  646 */     int ab = get(x, a) == get(x, b) ? 0 : get(x, a) < get(x, b) ? -1 : 1;
/*  647 */     int ac = get(x, a) == get(x, c) ? 0 : get(x, a) < get(x, c) ? -1 : 1;
/*  648 */     int bc = get(x, b) == get(x, c) ? 0 : get(x, b) < get(x, c) ? -1 : 1;
/*  649 */     return ac > 0 ? c : bc > 0 ? b : ab < 0 ? a : ac < 0 ? c : bc < 0 ? b : a;
/*      */   }
/*      */ 
/*      */   private static void selectionSort(short[][] a, long from, long to)
/*      */   {
/*  654 */     for (long i = from; i < to - 1L; i += 1L) {
/*  655 */       long m = i;
/*  656 */       for (long j = i + 1L; j < to; j += 1L) if (get(a, j) < get(a, m)) m = j;
/*  657 */       if (m != i) swap(a, i, m);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void quickSort(short[][] x, ShortComparator comp)
/*      */   {
/*  672 */     quickSort(x, 0L, length(x), comp);
/*      */   }
/*      */ 
/*      */   public static void quickSort(short[][] x, long from, long to)
/*      */   {
/*  686 */     long len = to - from;
/*      */ 
/*  688 */     if (len < 7L) {
/*  689 */       for (long i = from; i < to; i += 1L)
/*  690 */         for (long j = i; j > from; j -= 1L) { if ((get(x, j - 1L) == get(x, j) ? 0 : get(x, j - 1L) < get(x, j) ? -1 : 1) <= 0) break; swap(x, j, j - 1L); }
/*  691 */       return;
/*      */     }
/*      */ 
/*  694 */     long m = from + len / 2L;
/*  695 */     if (len > 7L) {
/*  696 */       long l = from;
/*  697 */       long n = to - 1L;
/*  698 */       if (len > 40L) {
/*  699 */         long s = len / 8L;
/*  700 */         l = med3(x, l, l + s, l + 2L * s);
/*  701 */         m = med3(x, m - s, m, m + s);
/*  702 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       }
/*  704 */       m = med3(x, l, m, n);
/*      */     }
/*  706 */     short v = get(x, m);
/*      */ 
/*  708 */     long a = from; long b = a; long c = to - 1L; long d = c;
/*      */     while (true)
/*      */     {
/*  711 */       if (b <= c)
/*      */       {
/*      */         int comparison;
/*  711 */         if ((comparison = get(x, b) == v ? 0 : get(x, b) < v ? -1 : 1) <= 0) {
/*  712 */           if (comparison == 0) swap(x, a++, b);
/*  713 */           b += 1L;
/*      */         }
/*      */       } else { while (c >= b)
/*      */         {
/*      */           int comparison;
/*  715 */           if ((comparison = get(x, c) == v ? 0 : get(x, c) < v ? -1 : 1) < 0) break;
/*  716 */           if (comparison == 0) swap(x, c, d--);
/*  717 */           c -= 1L;
/*      */         }
/*  719 */         if (b > c) break;
/*  720 */         swap(x, b++, c--);
/*      */       }
/*      */     }
/*  723 */     long n = to;
/*  724 */     long s = Math.min(a - from, b - a);
/*  725 */     vecSwap(x, from, b - s, s);
/*  726 */     s = Math.min(d - c, n - d - 1L);
/*  727 */     vecSwap(x, b, n - s, s);
/*      */ 
/*  729 */     if ((s = b - a) > 1L) quickSort(x, from, from + s);
/*  730 */     if ((s = d - c) > 1L) quickSort(x, n - s, n);
/*      */   }
/*      */ 
/*      */   public static void quickSort(short[][] x)
/*      */   {
/*  742 */     quickSort(x, 0L, length(x));
/*      */   }
/*      */ 
/*      */   public static long binarySearch(short[][] a, long from, long to, short key)
/*      */   {
/*  767 */     to -= 1L;
/*  768 */     while (from <= to) {
/*  769 */       long mid = from + to >>> 1;
/*  770 */       short midVal = get(a, mid);
/*  771 */       if (midVal < key) from = mid + 1L;
/*  772 */       else if (midVal > key) to = mid - 1L; else
/*  773 */         return mid;
/*      */     }
/*  775 */     return -(from + 1L);
/*      */   }
/*      */ 
/*      */   public static long binarySearch(short[][] a, short key)
/*      */   {
/*  796 */     return binarySearch(a, 0L, length(a), key);
/*      */   }
/*      */ 
/*      */   public static long binarySearch(short[][] a, long from, long to, short key, ShortComparator c)
/*      */   {
/*  821 */     to -= 1L;
/*  822 */     while (from <= to) {
/*  823 */       long mid = from + to >>> 1;
/*  824 */       short midVal = get(a, mid);
/*  825 */       int cmp = c.compare(midVal, key);
/*  826 */       if (cmp < 0) from = mid + 1L;
/*  827 */       else if (cmp > 0) to = mid - 1L; else
/*  828 */         return mid;
/*      */     }
/*  830 */     return -(from + 1L);
/*      */   }
/*      */ 
/*      */   public static long binarySearch(short[][] a, short key, ShortComparator c)
/*      */   {
/*  852 */     return binarySearch(a, 0L, length(a), key, c);
/*      */   }
/*      */ 
/*      */   public static void radixSort(short[][] a)
/*      */   {
/*  878 */     radixSort(a, 0L, length(a));
/*      */   }
/*      */ 
/*      */   public static void radixSort(short[][] a, long from, long to)
/*      */   {
/*  899 */     int maxLevel = 1;
/*  900 */     int stackSize = 256;
/*  901 */     long[] offsetStack = new long[256];
/*  902 */     int offsetPos = 0;
/*  903 */     long[] lengthStack = new long[256];
/*  904 */     int lengthPos = 0;
/*  905 */     int[] levelStack = new int[256];
/*  906 */     int levelPos = 0;
/*  907 */     offsetStack[(offsetPos++)] = from;
/*  908 */     lengthStack[(lengthPos++)] = (to - from);
/*  909 */     levelStack[(levelPos++)] = 0;
/*  910 */     long[] count = new long[256];
/*  911 */     long[] pos = new long[256];
/*  912 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/*  913 */     while (offsetPos > 0) {
/*  914 */       long first = offsetStack[(--offsetPos)];
/*  915 */       long length = lengthStack[(--lengthPos)];
/*  916 */       int level = levelStack[(--levelPos)];
/*  917 */       int signMask = level % 2 == 0 ? 128 : 0;
/*  918 */       if (length < 40L) {
/*  919 */         selectionSort(a, first, first + length);
/*      */       }
/*      */       else {
/*  922 */         int shift = (1 - level % 2) * 8;
/*      */ 
/*  924 */         for (long i = length; i-- != 0L; ByteBigArrays.set(digit, i, (byte)(get(a, first + i) >>> shift & 0xFF ^ signMask)));
/*  925 */         for (long i = length; i-- != 0L; count[(ByteBigArrays.get(digit, i) & 0xFF)] += 1L);
/*  927 */         int lastUsed = -1;
/*  928 */         long p = 0L;
/*  929 */         for (int i = 0; i < 256; i++) {
/*  930 */           if (count[i] != 0L) {
/*  931 */             lastUsed = i;
/*  932 */             if ((level < 1) && (count[i] > 1L))
/*      */             {
/*  934 */               offsetStack[(offsetPos++)] = (p + first);
/*  935 */               lengthStack[(lengthPos++)] = count[i];
/*  936 */               levelStack[(levelPos++)] = (level + 1);
/*      */             }
/*      */           }
/*      */           long tmp354_353 = (p + count[i]); p = tmp354_353; pos[i] = tmp354_353;
/*      */         }
/*      */ 
/*  942 */         long end = length - count[lastUsed];
/*  943 */         count[lastUsed] = 0L;
/*      */ 
/*  945 */         int c = -1;
/*  946 */         for (long i = 0L; i < end; count[c] = 0L) {
/*  947 */           short t = get(a, i + first);
/*  948 */           c = ByteBigArrays.get(digit, i) & 0xFF;
/*      */           while (true)
/*      */           {
/*      */             long d;
/*  949 */             if ((d = pos[c] -= 1L) <= i) break;
/*  950 */             short z = t;
/*  951 */             int zz = c;
/*  952 */             t = get(a, d + first);
/*  953 */             c = ByteBigArrays.get(digit, d) & 0xFF;
/*  954 */             set(a, d + first, z);
/*  955 */             ByteBigArrays.set(digit, d, (byte)zz);
/*      */           }
/*  957 */           set(a, i + first, t);
/*      */ 
/*  946 */           i += count[c];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void selectionSort(short[][] a, short[][] b, long from, long to)
/*      */   {
/*  962 */     for (long i = from; i < to - 1L; i += 1L) {
/*  963 */       long m = i;
/*  964 */       for (long j = i + 1L; j < to; j += 1L)
/*  965 */         if ((get(a, j) < get(a, m)) || ((get(a, j) == get(a, m)) && (get(b, j) < get(b, m)))) m = j;
/*  966 */       if (m != i) {
/*  967 */         short t = get(a, i);
/*  968 */         set(a, i, get(a, m));
/*  969 */         set(a, m, t);
/*  970 */         t = get(b, i);
/*  971 */         set(b, i, get(b, m));
/*  972 */         set(b, m, t);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void radixSort(short[][] a, short[][] b)
/*      */   {
/*  996 */     radixSort(a, b, 0L, length(a));
/*      */   }
/*      */ 
/*      */   public static void radixSort(short[][] a, short[][] b, long from, long to)
/*      */   {
/* 1021 */     int layers = 2;
/* 1022 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch.");
/* 1023 */     int maxLevel = 3;
/* 1024 */     int stackSize = 766;
/* 1025 */     long[] offsetStack = new long[766];
/* 1026 */     int offsetPos = 0;
/* 1027 */     long[] lengthStack = new long[766];
/* 1028 */     int lengthPos = 0;
/* 1029 */     int[] levelStack = new int[766];
/* 1030 */     int levelPos = 0;
/* 1031 */     offsetStack[(offsetPos++)] = from;
/* 1032 */     lengthStack[(lengthPos++)] = (to - from);
/* 1033 */     levelStack[(levelPos++)] = 0;
/* 1034 */     long[] count = new long[256];
/* 1035 */     long[] pos = new long[256];
/* 1036 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1037 */     while (offsetPos > 0) {
/* 1038 */       long first = offsetStack[(--offsetPos)];
/* 1039 */       long length = lengthStack[(--lengthPos)];
/* 1040 */       int level = levelStack[(--levelPos)];
/* 1041 */       int signMask = level % 2 == 0 ? 128 : 0;
/* 1042 */       if (length < 40L) {
/* 1043 */         selectionSort(a, b, first, first + length);
/*      */       }
/*      */       else {
/* 1046 */         short[][] k = level < 2 ? a : b;
/* 1047 */         int shift = (1 - level % 2) * 8;
/*      */ 
/* 1049 */         for (long i = length; i-- != 0L; ByteBigArrays.set(digit, i, (byte)(get(k, first + i) >>> shift & 0xFF ^ signMask)));
/* 1050 */         for (long i = length; i-- != 0L; count[(ByteBigArrays.get(digit, i) & 0xFF)] += 1L);
/* 1052 */         int lastUsed = -1;
/* 1053 */         long p = 0L;
/* 1054 */         for (int i = 0; i < 256; i++) {
/* 1055 */           if (count[i] != 0L) {
/* 1056 */             lastUsed = i;
/* 1057 */             if ((level < 3) && (count[i] > 1L)) {
/* 1058 */               offsetStack[(offsetPos++)] = (p + first);
/* 1059 */               lengthStack[(lengthPos++)] = count[i];
/* 1060 */               levelStack[(levelPos++)] = (level + 1);
/*      */             }
/*      */           }
/*      */           long tmp396_395 = (p + count[i]); p = tmp396_395; pos[i] = tmp396_395;
/*      */         }
/*      */ 
/* 1066 */         long end = length - count[lastUsed];
/* 1067 */         count[lastUsed] = 0L;
/*      */ 
/* 1069 */         int c = -1;
/* 1070 */         for (long i = 0L; i < end; count[c] = 0L) {
/* 1071 */           short t = get(a, i + first);
/* 1072 */           short u = get(b, i + first);
/* 1073 */           c = ByteBigArrays.get(digit, i) & 0xFF;
/*      */           while (true)
/*      */           {
/*      */             long d;
/* 1074 */             if ((d = pos[c] -= 1L) <= i) break;
/* 1075 */             short z = t;
/* 1076 */             int zz = c;
/* 1077 */             t = get(a, d + first);
/* 1078 */             set(a, d + first, z);
/* 1079 */             z = u;
/* 1080 */             u = get(b, d + first);
/* 1081 */             set(b, d + first, z);
/* 1082 */             c = ByteBigArrays.get(digit, d) & 0xFF;
/* 1083 */             ByteBigArrays.set(digit, d, (byte)zz);
/*      */           }
/* 1085 */           set(a, i + first, t);
/* 1086 */           set(b, i + first, u);
/*      */ 
/* 1070 */           i += count[c];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static short[][] shuffle(short[][] a, long from, long to, Random random)
/*      */   {
/* 1099 */     for (long i = to - from; i-- != 0L; ) {
/* 1100 */       long p = (random.nextLong() & 0xFFFFFFFF) % (i + 1L);
/* 1101 */       short t = get(a, from + i);
/* 1102 */       set(a, from + i, get(a, from + p));
/* 1103 */       set(a, from + p, t);
/*      */     }
/* 1105 */     return a;
/*      */   }
/*      */ 
/*      */   public static short[][] shuffle(short[][] a, Random random)
/*      */   {
/* 1114 */     for (long i = length(a); i-- != 0L; ) {
/* 1115 */       long p = (random.nextLong() & 0xFFFFFFFF) % (i + 1L);
/* 1116 */       short t = get(a, i);
/* 1117 */       set(a, i, get(a, p));
/* 1118 */       set(a, p, t);
/*      */     }
/* 1120 */     return a;
/*      */   }
/*      */ 
/*      */   private static final class BigArrayHashStrategy
/*      */     implements Hash.Strategy<short[][]>, Serializable
/*      */   {
/*      */     public static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */     public int hashCode(short[][] o)
/*      */     {
/*  551 */       return Arrays.deepHashCode(o);
/*      */     }
/*      */     public boolean equals(short[][] a, short[][] b) {
/*  554 */       return ShortBigArrays.equals(a, b);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.shorts.ShortBigArrays
 * JD-Core Version:    0.6.2
 */