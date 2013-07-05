/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class Double2IntOpenCustomHashMap extends AbstractDouble2IntMap
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   public static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient double[] key;
/*     */   protected transient int[] value;
/*     */   protected transient boolean[] used;
/*     */   protected final float f;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected transient int mask;
/*     */   protected int size;
/*     */   protected volatile transient Double2IntMap.FastEntrySet entries;
/*     */   protected volatile transient DoubleSet keys;
/*     */   protected volatile transient IntCollection values;
/*     */   protected DoubleHash.Strategy strategy;
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy)
/*     */   {
/* 111 */     this.strategy = strategy;
/* 112 */     if ((f <= 0.0F) || (f > 1.0F)) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
/* 113 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative");
/* 114 */     this.f = f;
/* 115 */     this.n = HashCommon.arraySize(expected, f);
/* 116 */     this.mask = (this.n - 1);
/* 117 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 118 */     this.key = new double[this.n];
/* 119 */     this.value = new int[this.n];
/* 120 */     this.used = new boolean[this.n];
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(int expected, DoubleHash.Strategy strategy)
/*     */   {
/* 128 */     this(expected, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(DoubleHash.Strategy strategy)
/*     */   {
/* 135 */     this(16, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(Map<? extends Double, ? extends Integer> m, float f, DoubleHash.Strategy strategy)
/*     */   {
/* 144 */     this(m.size(), f, strategy);
/* 145 */     putAll(m);
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(Map<? extends Double, ? extends Integer> m, DoubleHash.Strategy strategy)
/*     */   {
/* 153 */     this(m, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(Double2IntMap m, float f, DoubleHash.Strategy strategy)
/*     */   {
/* 162 */     this(m.size(), f, strategy);
/* 163 */     putAll(m);
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(Double2IntMap m, DoubleHash.Strategy strategy)
/*     */   {
/* 171 */     this(m, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(double[] k, int[] v, float f, DoubleHash.Strategy strategy)
/*     */   {
/* 182 */     this(k.length, f, strategy);
/* 183 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/* 184 */     for (int i = 0; i < k.length; i++) put(k[i], v[i]);
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap(double[] k, int[] v, DoubleHash.Strategy strategy)
/*     */   {
/* 194 */     this(k, v, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */   public DoubleHash.Strategy strategy()
/*     */   {
/* 201 */     return this.strategy;
/*     */   }
/*     */ 
/*     */   public int put(double k, int v)
/*     */   {
/* 209 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 211 */     while (this.used[pos] != 0) {
/* 212 */       if (this.strategy.equals(this.key[pos], k)) {
/* 213 */         int oldValue = this.value[pos];
/* 214 */         this.value[pos] = v;
/* 215 */         return oldValue;
/*     */       }
/* 217 */       pos = pos + 1 & this.mask;
/*     */     }
/* 219 */     this.used[pos] = true;
/* 220 */     this.key[pos] = k;
/* 221 */     this.value[pos] = v;
/* 222 */     if (++this.size >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */ 
/* 224 */     return this.defRetValue;
/*     */   }
/*     */   public Integer put(Double ok, Integer ov) {
/* 227 */     int v = ov.intValue();
/* 228 */     double k = ok.doubleValue();
/*     */ 
/* 230 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 232 */     while (this.used[pos] != 0) {
/* 233 */       if (this.strategy.equals(this.key[pos], k)) {
/* 234 */         Integer oldValue = Integer.valueOf(this.value[pos]);
/* 235 */         this.value[pos] = v;
/* 236 */         return oldValue;
/*     */       }
/* 238 */       pos = pos + 1 & this.mask;
/*     */     }
/* 240 */     this.used[pos] = true;
/* 241 */     this.key[pos] = k;
/* 242 */     this.value[pos] = v;
/* 243 */     if (++this.size >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */ 
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   public int add(double k, int incr)
/*     */   {
/* 260 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 262 */     while (this.used[pos] != 0) {
/* 263 */       if (this.strategy.equals(this.key[pos], k)) {
/* 264 */         int oldValue = this.value[pos];
/* 265 */         this.value[pos] += incr;
/* 266 */         return oldValue;
/*     */       }
/* 268 */       pos = pos + 1 & this.mask;
/*     */     }
/* 270 */     this.used[pos] = true;
/* 271 */     this.key[pos] = k;
/* 272 */     this.value[pos] = (this.defRetValue + incr);
/* 273 */     if (++this.size >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */ 
/* 275 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   protected final int shiftKeys(int pos)
/*     */   {
/*     */     int last;
/*     */     while (true)
/*     */     {
/* 287 */       pos = (last = pos) + 1 & this.mask;
/* 288 */       while (this.used[pos] != 0) {
/* 289 */         int slot = HashCommon.murmurHash3(this.strategy.hashCode(this.key[pos])) & this.mask;
/* 290 */         if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 291 */         pos = pos + 1 & this.mask;
/*     */       }
/* 293 */       if (this.used[pos] == 0) break;
/* 294 */       this.key[last] = this.key[pos];
/* 295 */       this.value[last] = this.value[pos];
/*     */     }
/* 297 */     this.used[last] = false;
/* 298 */     return last;
/*     */   }
/*     */ 
/*     */   public int remove(double k)
/*     */   {
/* 303 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 305 */     while (this.used[pos] != 0) {
/* 306 */       if (this.strategy.equals(this.key[pos], k)) {
/* 307 */         this.size -= 1;
/* 308 */         int v = this.value[pos];
/* 309 */         shiftKeys(pos);
/* 310 */         return v;
/*     */       }
/* 312 */       pos = pos + 1 & this.mask;
/*     */     }
/* 314 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   public Integer remove(Object ok) {
/* 318 */     double k = ((Double)ok).doubleValue();
/*     */ 
/* 320 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 322 */     while (this.used[pos] != 0) {
/* 323 */       if (this.strategy.equals(this.key[pos], k)) {
/* 324 */         this.size -= 1;
/* 325 */         int v = this.value[pos];
/* 326 */         shiftKeys(pos);
/* 327 */         return Integer.valueOf(v);
/*     */       }
/* 329 */       pos = pos + 1 & this.mask;
/*     */     }
/* 331 */     return null;
/*     */   }
/*     */   public Integer get(Double ok) {
/* 334 */     double k = ok.doubleValue();
/*     */ 
/* 336 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 338 */     while (this.used[pos] != 0) {
/* 339 */       if (this.strategy.equals(this.key[pos], k)) return Integer.valueOf(this.value[pos]);
/* 340 */       pos = pos + 1 & this.mask;
/*     */     }
/* 342 */     return null;
/*     */   }
/*     */ 
/*     */   public int get(double k)
/*     */   {
/* 347 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 349 */     while (this.used[pos] != 0) {
/* 350 */       if (this.strategy.equals(this.key[pos], k)) return this.value[pos];
/* 351 */       pos = pos + 1 & this.mask;
/*     */     }
/* 353 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   public boolean containsKey(double k)
/*     */   {
/* 358 */     int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/*     */ 
/* 360 */     while (this.used[pos] != 0) {
/* 361 */       if (this.strategy.equals(this.key[pos], k)) return true;
/* 362 */       pos = pos + 1 & this.mask;
/*     */     }
/* 364 */     return false;
/*     */   }
/*     */   public boolean containsValue(int v) {
/* 367 */     int[] value = this.value;
/* 368 */     boolean[] used = this.used;
/* 369 */     for (int i = this.n; i-- != 0; return true) label16: if ((used[i] == 0) || (value[i] != v))
/*     */         break label16; return false;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 379 */     if (this.size == 0) return;
/* 380 */     this.size = 0;
/* 381 */     BooleanArrays.fill(this.used, false);
/*     */   }
/*     */ 
/*     */   public int size() {
/* 385 */     return this.size;
/*     */   }
/*     */   public boolean isEmpty() {
/* 388 */     return this.size == 0;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void growthFactor(int growthFactor)
/*     */   {
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public int growthFactor()
/*     */   {
/* 405 */     return 16;
/*     */   }
/*     */ 
/*     */   public Double2IntMap.FastEntrySet double2IntEntrySet()
/*     */   {
/* 611 */     if (this.entries == null) this.entries = new MapEntrySet(null);
/* 612 */     return this.entries;
/*     */   }
/*     */ 
/*     */   public DoubleSet keySet()
/*     */   {
/* 645 */     if (this.keys == null) this.keys = new KeySet(null);
/* 646 */     return this.keys;
/*     */   }
/*     */ 
/*     */   public IntCollection values()
/*     */   {
/* 660 */     if (this.values == null) this.values = new AbstractIntCollection() {
/*     */         public IntIterator iterator() {
/* 662 */           return new Double2IntOpenCustomHashMap.ValueIterator(Double2IntOpenCustomHashMap.this);
/*     */         }
/*     */         public int size() {
/* 665 */           return Double2IntOpenCustomHashMap.this.size;
/*     */         }
/*     */         public boolean contains(int v) {
/* 668 */           return Double2IntOpenCustomHashMap.this.containsValue(v);
/*     */         }
/*     */         public void clear() {
/* 671 */           Double2IntOpenCustomHashMap.this.clear();
/*     */         }
/*     */       };
/* 674 */     return this.values;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public boolean rehash()
/*     */   {
/* 688 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean trim()
/*     */   {
/* 703 */     int l = HashCommon.arraySize(this.size, this.f);
/* 704 */     if (l >= this.n) return true; try
/*     */     {
/* 706 */       rehash(l);
/*     */     } catch (OutOfMemoryError cantDoIt) {
/* 708 */       return false;
/* 709 */     }return true;
/*     */   }
/*     */ 
/*     */   public boolean trim(int n)
/*     */   {
/* 730 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil(n / this.f));
/* 731 */     if (this.n <= l) return true; try
/*     */     {
/* 733 */       rehash(l);
/*     */     } catch (OutOfMemoryError cantDoIt) {
/* 735 */       return false;
/* 736 */     }return true;
/*     */   }
/*     */ 
/*     */   protected void rehash(int newN)
/*     */   {
/* 749 */     int i = 0;
/* 750 */     boolean[] used = this.used;
/*     */ 
/* 752 */     double[] key = this.key;
/* 753 */     int[] value = this.value;
/* 754 */     int newMask = newN - 1;
/* 755 */     double[] newKey = new double[newN];
/* 756 */     int[] newValue = new int[newN];
/* 757 */     boolean[] newUsed = new boolean[newN];
/* 758 */     for (int j = this.size; j-- != 0; ) {
/* 759 */       while (used[i] == 0) i++;
/* 760 */       double k = key[i];
/* 761 */       int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & newMask;
/* 762 */       while (newUsed[pos] != 0) pos = pos + 1 & newMask;
/* 763 */       newUsed[pos] = true;
/* 764 */       newKey[pos] = k;
/* 765 */       newValue[pos] = value[i];
/* 766 */       i++;
/*     */     }
/* 768 */     this.n = newN;
/* 769 */     this.mask = newMask;
/* 770 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 771 */     this.key = newKey;
/* 772 */     this.value = newValue;
/* 773 */     this.used = newUsed;
/*     */   }
/*     */ 
/*     */   public Double2IntOpenCustomHashMap clone()
/*     */   {
/*     */     Double2IntOpenCustomHashMap c;
/*     */     try
/*     */     {
/* 786 */       c = (Double2IntOpenCustomHashMap)super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException cantHappen) {
/* 789 */       throw new InternalError();
/*     */     }
/* 791 */     c.keys = null;
/* 792 */     c.values = null;
/* 793 */     c.entries = null;
/* 794 */     c.key = ((double[])this.key.clone());
/* 795 */     c.value = ((int[])this.value.clone());
/* 796 */     c.used = ((boolean[])this.used.clone());
/* 797 */     c.strategy = this.strategy;
/* 798 */     return c;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 810 */     int h = 0;
/* 811 */     int j = this.size; int i = 0; for (int t = 0; j-- != 0; ) {
/* 812 */       while (this.used[i] == 0) i++;
/* 813 */       t = this.strategy.hashCode(this.key[i]);
/* 814 */       t ^= this.value[i];
/* 815 */       h += t;
/* 816 */       i++;
/*     */     }
/* 818 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 821 */     double[] key = this.key;
/* 822 */     int[] value = this.value;
/* 823 */     MapIterator i = new MapIterator(null);
/* 824 */     s.defaultWriteObject();
/* 825 */     for (int j = this.size; j-- != 0; ) {
/* 826 */       int e = i.nextEntry();
/* 827 */       s.writeDouble(key[e]);
/* 828 */       s.writeInt(value[e]);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 833 */     s.defaultReadObject();
/* 834 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 835 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 836 */     this.mask = (this.n - 1);
/* 837 */     double[] key = this.key = new double[this.n];
/* 838 */     int[] value = this.value = new int[this.n];
/* 839 */     boolean[] used = this.used = new boolean[this.n];
/*     */ 
/* 842 */     int i = this.size; for (int pos = 0; i-- != 0; ) {
/* 843 */       double k = s.readDouble();
/* 844 */       int v = s.readInt();
/* 845 */       pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 846 */       while (used[pos] != 0) pos = pos + 1 & this.mask;
/* 847 */       used[pos] = true;
/* 848 */       key[pos] = k;
/* 849 */       value[pos] = v;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkTable()
/*     */   {
/*     */   }
/*     */ 
/*     */   private final class ValueIterator extends Double2IntOpenCustomHashMap.MapIterator
/*     */     implements IntIterator
/*     */   {
/*     */     public ValueIterator()
/*     */     {
/* 655 */       super(null); } 
/* 656 */     public int nextInt() { return Double2IntOpenCustomHashMap.this.value[nextEntry()]; } 
/* 657 */     public Integer next() { return Integer.valueOf(Double2IntOpenCustomHashMap.this.value[nextEntry()]); }
/*     */ 
/*     */   }
/*     */ 
/*     */   private final class KeySet extends AbstractDoubleSet
/*     */   {
/*     */     private KeySet()
/*     */     {
/*     */     }
/*     */ 
/*     */     public DoubleIterator iterator()
/*     */     {
/* 627 */       return new Double2IntOpenCustomHashMap.KeyIterator(Double2IntOpenCustomHashMap.this);
/*     */     }
/*     */     public int size() {
/* 630 */       return Double2IntOpenCustomHashMap.this.size;
/*     */     }
/*     */     public boolean contains(double k) {
/* 633 */       return Double2IntOpenCustomHashMap.this.containsKey(k);
/*     */     }
/*     */     public boolean remove(double k) {
/* 636 */       int oldSize = Double2IntOpenCustomHashMap.this.size;
/* 637 */       Double2IntOpenCustomHashMap.this.remove(k);
/* 638 */       return Double2IntOpenCustomHashMap.this.size != oldSize;
/*     */     }
/*     */     public void clear() {
/* 641 */       Double2IntOpenCustomHashMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class KeyIterator extends Double2IntOpenCustomHashMap.MapIterator
/*     */     implements DoubleIterator
/*     */   {
/*     */     public KeyIterator()
/*     */     {
/* 621 */       super(null); } 
/* 622 */     public double nextDouble() { return Double2IntOpenCustomHashMap.this.key[nextEntry()]; } 
/* 623 */     public Double next() { return Double.valueOf(Double2IntOpenCustomHashMap.this.key[nextEntry()]); }
/*     */ 
/*     */   }
/*     */ 
/*     */   private final class MapEntrySet extends AbstractObjectSet<Double2IntMap.Entry>
/*     */     implements Double2IntMap.FastEntrySet
/*     */   {
/*     */     private MapEntrySet()
/*     */     {
/*     */     }
/*     */ 
/*     */     public ObjectIterator<Double2IntMap.Entry> iterator()
/*     */     {
/* 567 */       return new Double2IntOpenCustomHashMap.EntryIterator(Double2IntOpenCustomHashMap.this, null);
/*     */     }
/*     */     public ObjectIterator<Double2IntMap.Entry> fastIterator() {
/* 570 */       return new Double2IntOpenCustomHashMap.FastEntryIterator(Double2IntOpenCustomHashMap.this, null);
/*     */     }
/*     */ 
/*     */     public boolean contains(Object o) {
/* 574 */       if (!(o instanceof Map.Entry)) return false;
/* 575 */       Map.Entry e = (Map.Entry)o;
/* 576 */       double k = ((Double)e.getKey()).doubleValue();
/*     */ 
/* 578 */       int pos = HashCommon.murmurHash3(Double2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Double2IntOpenCustomHashMap.this.mask;
/*     */ 
/* 580 */       while (Double2IntOpenCustomHashMap.this.used[pos] != 0) {
/* 581 */         if (Double2IntOpenCustomHashMap.this.strategy.equals(Double2IntOpenCustomHashMap.this.key[pos], k)) return Double2IntOpenCustomHashMap.this.value[pos] == ((Integer)e.getValue()).intValue();
/* 582 */         pos = pos + 1 & Double2IntOpenCustomHashMap.this.mask;
/*     */       }
/* 584 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean remove(Object o) {
/* 588 */       if (!(o instanceof Map.Entry)) return false;
/* 589 */       Map.Entry e = (Map.Entry)o;
/* 590 */       double k = ((Double)e.getKey()).doubleValue();
/*     */ 
/* 592 */       int pos = HashCommon.murmurHash3(Double2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Double2IntOpenCustomHashMap.this.mask;
/*     */ 
/* 594 */       while (Double2IntOpenCustomHashMap.this.used[pos] != 0) {
/* 595 */         if (Double2IntOpenCustomHashMap.this.strategy.equals(Double2IntOpenCustomHashMap.this.key[pos], k)) {
/* 596 */           Double2IntOpenCustomHashMap.this.remove(e.getKey());
/* 597 */           return true;
/*     */         }
/* 599 */         pos = pos + 1 & Double2IntOpenCustomHashMap.this.mask;
/*     */       }
/* 601 */       return false;
/*     */     }
/*     */     public int size() {
/* 604 */       return Double2IntOpenCustomHashMap.this.size;
/*     */     }
/*     */     public void clear() {
/* 607 */       Double2IntOpenCustomHashMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class FastEntryIterator extends Double2IntOpenCustomHashMap.MapIterator
/*     */     implements ObjectIterator<Double2IntMap.Entry>
/*     */   {
/* 557 */     final AbstractDouble2IntMap.BasicEntry entry = new AbstractDouble2IntMap.BasicEntry(0.0D, 0);
/*     */ 
/*     */     private FastEntryIterator()
/*     */     {
/* 556 */       super(null);
/*     */     }
/*     */     public AbstractDouble2IntMap.BasicEntry next() {
/* 559 */       int e = nextEntry();
/* 560 */       this.entry.key = Double2IntOpenCustomHashMap.this.key[e];
/* 561 */       this.entry.value = Double2IntOpenCustomHashMap.this.value[e];
/* 562 */       return this.entry;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class EntryIterator extends Double2IntOpenCustomHashMap.MapIterator
/*     */     implements ObjectIterator<Double2IntMap.Entry>
/*     */   {
/*     */     private Double2IntOpenCustomHashMap.MapEntry entry;
/*     */ 
/*     */     private EntryIterator()
/*     */     {
/* 545 */       super(null);
/*     */     }
/*     */     public Double2IntMap.Entry next() {
/* 548 */       return this.entry = new Double2IntOpenCustomHashMap.MapEntry(Double2IntOpenCustomHashMap.this, nextEntry());
/*     */     }
/*     */ 
/*     */     public void remove() {
/* 552 */       super.remove();
/* 553 */       Double2IntOpenCustomHashMap.MapEntry.access$102(this.entry, -1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MapIterator
/*     */   {
/*     */     int pos;
/*     */     int last;
/*     */     int c;
/*     */     DoubleArrayList wrapped;
/*     */ 
/*     */     private MapIterator()
/*     */     {
/* 454 */       this.pos = Double2IntOpenCustomHashMap.this.n;
/*     */ 
/* 457 */       this.last = -1;
/*     */ 
/* 459 */       this.c = Double2IntOpenCustomHashMap.this.size;
/*     */ 
/* 464 */       boolean[] used = Double2IntOpenCustomHashMap.this.used;
/* 465 */       while ((this.c != 0) && (used[(--this.pos)] == 0));
/*     */     }
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 468 */       return this.c != 0;
/*     */     }
/*     */     public int nextEntry() {
/* 471 */       if (!hasNext()) throw new NoSuchElementException();
/* 472 */       this.c -= 1;
/*     */ 
/* 474 */       if (this.pos < 0) {
/* 475 */         double k = this.wrapped.getDouble(-(this.last = --this.pos) - 2);
/*     */ 
/* 477 */         int pos = HashCommon.murmurHash3(Double2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Double2IntOpenCustomHashMap.this.mask;
/*     */ 
/* 479 */         while (Double2IntOpenCustomHashMap.this.used[pos] != 0) {
/* 480 */           if (Double2IntOpenCustomHashMap.this.strategy.equals(Double2IntOpenCustomHashMap.this.key[pos], k)) return pos;
/* 481 */           pos = pos + 1 & Double2IntOpenCustomHashMap.this.mask;
/*     */         }
/*     */       }
/* 484 */       this.last = this.pos;
/*     */ 
/* 486 */       if (this.c != 0) {
/* 487 */         boolean[] used = Double2IntOpenCustomHashMap.this.used;
/* 488 */         while ((this.pos-- != 0) && (used[this.pos] == 0));
/*     */       }
/* 491 */       return this.last;
/*     */     }
/*     */ 
/*     */     protected final int shiftKeys(int pos)
/*     */     {
/*     */       int last;
/*     */       while (true)
/*     */       {
/* 504 */         pos = (last = pos) + 1 & Double2IntOpenCustomHashMap.this.mask;
/* 505 */         while (Double2IntOpenCustomHashMap.this.used[pos] != 0) {
/* 506 */           int slot = HashCommon.murmurHash3(Double2IntOpenCustomHashMap.this.strategy.hashCode(Double2IntOpenCustomHashMap.this.key[pos])) & Double2IntOpenCustomHashMap.this.mask;
/* 507 */           if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 508 */           pos = pos + 1 & Double2IntOpenCustomHashMap.this.mask;
/*     */         }
/* 510 */         if (Double2IntOpenCustomHashMap.this.used[pos] == 0) break;
/* 511 */         if (pos < last)
/*     */         {
/* 513 */           if (this.wrapped == null) this.wrapped = new DoubleArrayList();
/* 514 */           this.wrapped.add(Double2IntOpenCustomHashMap.this.key[pos]);
/*     */         }
/* 516 */         Double2IntOpenCustomHashMap.this.key[last] = Double2IntOpenCustomHashMap.this.key[pos];
/* 517 */         Double2IntOpenCustomHashMap.this.value[last] = Double2IntOpenCustomHashMap.this.value[pos];
/*     */       }
/* 519 */       Double2IntOpenCustomHashMap.this.used[last] = false;
/* 520 */       return last;
/*     */     }
/*     */ 
/*     */     public void remove() {
/* 524 */       if (this.last == -1) throw new IllegalStateException();
/* 525 */       if (this.pos < -1)
/*     */       {
/* 527 */         Double2IntOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 2));
/* 528 */         this.last = -1;
/* 529 */         return;
/*     */       }
/* 531 */       Double2IntOpenCustomHashMap.this.size -= 1;
/* 532 */       if ((shiftKeys(this.last) == this.pos) && (this.c > 0)) {
/* 533 */         this.c += 1;
/* 534 */         nextEntry();
/*     */       }
/* 536 */       this.last = -1;
/*     */     }
/*     */ 
/*     */     public int skip(int n) {
/* 540 */       int i = n;
/* 541 */       while ((i-- != 0) && (hasNext())) nextEntry();
/* 542 */       return n - i - 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class MapEntry
/*     */     implements Double2IntMap.Entry, Map.Entry<Double, Integer>
/*     */   {
/*     */     private int index;
/*     */ 
/*     */     MapEntry(int index)
/*     */     {
/* 415 */       this.index = index;
/*     */     }
/*     */     public Double getKey() {
/* 418 */       return Double.valueOf(Double2IntOpenCustomHashMap.this.key[this.index]);
/*     */     }
/*     */     public double getDoubleKey() {
/* 421 */       return Double2IntOpenCustomHashMap.this.key[this.index];
/*     */     }
/*     */     public Integer getValue() {
/* 424 */       return Integer.valueOf(Double2IntOpenCustomHashMap.this.value[this.index]);
/*     */     }
/*     */     public int getIntValue() {
/* 427 */       return Double2IntOpenCustomHashMap.this.value[this.index];
/*     */     }
/*     */     public int setValue(int v) {
/* 430 */       int oldValue = Double2IntOpenCustomHashMap.this.value[this.index];
/* 431 */       Double2IntOpenCustomHashMap.this.value[this.index] = v;
/* 432 */       return oldValue;
/*     */     }
/*     */     public Integer setValue(Integer v) {
/* 435 */       return Integer.valueOf(setValue(v.intValue()));
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o) {
/* 439 */       if (!(o instanceof Map.Entry)) return false;
/* 440 */       Map.Entry e = (Map.Entry)o;
/* 441 */       return (Double2IntOpenCustomHashMap.this.strategy.equals(Double2IntOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue())) && (Double2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*     */     }
/*     */     public int hashCode() {
/* 444 */       return Double2IntOpenCustomHashMap.this.strategy.hashCode(Double2IntOpenCustomHashMap.this.key[this.index]) ^ Double2IntOpenCustomHashMap.this.value[this.index];
/*     */     }
/*     */     public String toString() {
/* 447 */       return Double2IntOpenCustomHashMap.this.key[this.index] + "=>" + Double2IntOpenCustomHashMap.this.value[this.index];
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.doubles.Double2IntOpenCustomHashMap
 * JD-Core Version:    0.6.2
 */