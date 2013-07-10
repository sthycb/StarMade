/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*     */ public class Double2ByteOpenHashMap extends AbstractDouble2ByteMap
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   public static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient double[] key;
/*     */   protected transient byte[] value;
/*     */   protected transient boolean[] used;
/*     */   protected final float f;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected transient int mask;
/*     */   protected int size;
/*     */   protected volatile transient Double2ByteMap.FastEntrySet entries;
/*     */   protected volatile transient DoubleSet keys;
/*     */   protected volatile transient ByteCollection values;
/*     */ 
/*     */   public Double2ByteOpenHashMap(int expected, float f)
/*     */   {
/* 107 */     if ((f <= 0.0F) || (f > 1.0F)) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
/* 108 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative");
/* 109 */     this.f = f;
/* 110 */     this.n = HashCommon.arraySize(expected, f);
/* 111 */     this.mask = (this.n - 1);
/* 112 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 113 */     this.key = new double[this.n];
/* 114 */     this.value = new byte[this.n];
/* 115 */     this.used = new boolean[this.n];
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap(int expected)
/*     */   {
/* 122 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap()
/*     */   {
/* 128 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap(Map<? extends Double, ? extends Byte> m, float f)
/*     */   {
/* 136 */     this(m.size(), f);
/* 137 */     putAll(m);
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap(Map<? extends Double, ? extends Byte> m)
/*     */   {
/* 144 */     this(m, 0.75F);
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap(Double2ByteMap m, float f)
/*     */   {
/* 152 */     this(m.size(), f);
/* 153 */     putAll(m);
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap(Double2ByteMap m)
/*     */   {
/* 160 */     this(m, 0.75F);
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap(double[] k, byte[] v, float f)
/*     */   {
/* 170 */     this(k.length, f);
/* 171 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/* 172 */     for (int i = 0; i < k.length; i++) put(k[i], v[i]);
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap(double[] k, byte[] v)
/*     */   {
/* 181 */     this(k, v, 0.75F);
/*     */   }
/*     */ 
/*     */   public byte put(double k, byte v)
/*     */   {
/* 189 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 191 */     while (this.used[pos] != 0) {
/* 192 */       if (this.key[pos] == k) {
/* 193 */         byte oldValue = this.value[pos];
/* 194 */         this.value[pos] = v;
/* 195 */         return oldValue;
/*     */       }
/* 197 */       pos = pos + 1 & this.mask;
/*     */     }
/* 199 */     this.used[pos] = true;
/* 200 */     this.key[pos] = k;
/* 201 */     this.value[pos] = v;
/* 202 */     if (++this.size >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */ 
/* 204 */     return this.defRetValue;
/*     */   }
/*     */   public Byte put(Double ok, Byte ov) {
/* 207 */     byte v = ov.byteValue();
/* 208 */     double k = ok.doubleValue();
/*     */ 
/* 210 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 212 */     while (this.used[pos] != 0) {
/* 213 */       if (this.key[pos] == k) {
/* 214 */         Byte oldValue = Byte.valueOf(this.value[pos]);
/* 215 */         this.value[pos] = v;
/* 216 */         return oldValue;
/*     */       }
/* 218 */       pos = pos + 1 & this.mask;
/*     */     }
/* 220 */     this.used[pos] = true;
/* 221 */     this.key[pos] = k;
/* 222 */     this.value[pos] = v;
/* 223 */     if (++this.size >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */ 
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   public byte add(double k, byte incr)
/*     */   {
/* 240 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 242 */     while (this.used[pos] != 0) {
/* 243 */       if (this.key[pos] == k) {
/* 244 */         byte oldValue = this.value[pos];
/*     */         int tmp52_50 = pos;
/*     */         byte[] tmp52_47 = this.value; tmp52_47[tmp52_50] = ((byte)(tmp52_47[tmp52_50] + incr));
/* 246 */         return tmp52_50;
/*     */       }
/* 248 */       pos = pos + 1 & this.mask;
/*     */     }
/* 250 */     this.used[pos] = true;
/* 251 */     this.key[pos] = k;
/* 252 */     this.value[pos] = ((byte)(this.defRetValue + incr));
/* 253 */     if (++this.size >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */ 
/* 255 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   protected final int shiftKeys(int pos)
/*     */   {
/*     */     int last;
/*     */     while (true)
/*     */     {
/* 267 */       pos = (last = pos) + 1 & this.mask;
/* 268 */       while (this.used[pos] != 0) {
/* 269 */         int slot = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(this.key[pos])) & this.mask;
/* 270 */         if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 271 */         pos = pos + 1 & this.mask;
/*     */       }
/* 273 */       if (this.used[pos] == 0) break;
/* 274 */       this.key[last] = this.key[pos];
/* 275 */       this.value[last] = this.value[pos];
/*     */     }
/* 277 */     this.used[last] = false;
/* 278 */     return last;
/*     */   }
/*     */ 
/*     */   public byte remove(double k)
/*     */   {
/* 283 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 285 */     while (this.used[pos] != 0) {
/* 286 */       if (this.key[pos] == k) {
/* 287 */         this.size -= 1;
/* 288 */         byte v = this.value[pos];
/* 289 */         shiftKeys(pos);
/* 290 */         return v;
/*     */       }
/* 292 */       pos = pos + 1 & this.mask;
/*     */     }
/* 294 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   public Byte remove(Object ok) {
/* 298 */     double k = ((Double)ok).doubleValue();
/*     */ 
/* 300 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 302 */     while (this.used[pos] != 0) {
/* 303 */       if (this.key[pos] == k) {
/* 304 */         this.size -= 1;
/* 305 */         byte v = this.value[pos];
/* 306 */         shiftKeys(pos);
/* 307 */         return Byte.valueOf(v);
/*     */       }
/* 309 */       pos = pos + 1 & this.mask;
/*     */     }
/* 311 */     return null;
/*     */   }
/*     */   public Byte get(Double ok) {
/* 314 */     double k = ok.doubleValue();
/*     */ 
/* 316 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 318 */     while (this.used[pos] != 0) {
/* 319 */       if (this.key[pos] == k) return Byte.valueOf(this.value[pos]);
/* 320 */       pos = pos + 1 & this.mask;
/*     */     }
/* 322 */     return null;
/*     */   }
/*     */ 
/*     */   public byte get(double k)
/*     */   {
/* 327 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 329 */     while (this.used[pos] != 0) {
/* 330 */       if (this.key[pos] == k) return this.value[pos];
/* 331 */       pos = pos + 1 & this.mask;
/*     */     }
/* 333 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   public boolean containsKey(double k)
/*     */   {
/* 338 */     int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/*     */ 
/* 340 */     while (this.used[pos] != 0) {
/* 341 */       if (this.key[pos] == k) return true;
/* 342 */       pos = pos + 1 & this.mask;
/*     */     }
/* 344 */     return false;
/*     */   }
/*     */   public boolean containsValue(byte v) {
/* 347 */     byte[] value = this.value;
/* 348 */     boolean[] used = this.used;
/* 349 */     for (int i = this.n; i-- != 0; return true) label16: if ((used[i] == 0) || (value[i] != v))
/*     */         break label16; return false;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 359 */     if (this.size == 0) return;
/* 360 */     this.size = 0;
/* 361 */     BooleanArrays.fill(this.used, false);
/*     */   }
/*     */ 
/*     */   public int size() {
/* 365 */     return this.size;
/*     */   }
/*     */   public boolean isEmpty() {
/* 368 */     return this.size == 0;
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
/* 385 */     return 16;
/*     */   }
/*     */ 
/*     */   public Double2ByteMap.FastEntrySet double2ByteEntrySet()
/*     */   {
/* 591 */     if (this.entries == null) this.entries = new MapEntrySet(null);
/* 592 */     return this.entries;
/*     */   }
/*     */ 
/*     */   public DoubleSet keySet()
/*     */   {
/* 625 */     if (this.keys == null) this.keys = new KeySet(null);
/* 626 */     return this.keys;
/*     */   }
/*     */ 
/*     */   public ByteCollection values()
/*     */   {
/* 640 */     if (this.values == null) this.values = new AbstractByteCollection() {
/*     */         public ByteIterator iterator() {
/* 642 */           return new Double2ByteOpenHashMap.ValueIterator(Double2ByteOpenHashMap.this);
/*     */         }
/*     */         public int size() {
/* 645 */           return Double2ByteOpenHashMap.this.size;
/*     */         }
/*     */         public boolean contains(byte v) {
/* 648 */           return Double2ByteOpenHashMap.this.containsValue(v);
/*     */         }
/*     */         public void clear() {
/* 651 */           Double2ByteOpenHashMap.this.clear();
/*     */         }
/*     */       };
/* 654 */     return this.values;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public boolean rehash()
/*     */   {
/* 668 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean trim()
/*     */   {
/* 683 */     int l = HashCommon.arraySize(this.size, this.f);
/* 684 */     if (l >= this.n) return true; try
/*     */     {
/* 686 */       rehash(l);
/*     */     } catch (OutOfMemoryError cantDoIt) {
/* 688 */       return false;
/* 689 */     }return true;
/*     */   }
/*     */ 
/*     */   public boolean trim(int n)
/*     */   {
/* 710 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil(n / this.f));
/* 711 */     if (this.n <= l) return true; try
/*     */     {
/* 713 */       rehash(l);
/*     */     } catch (OutOfMemoryError cantDoIt) {
/* 715 */       return false;
/* 716 */     }return true;
/*     */   }
/*     */ 
/*     */   protected void rehash(int newN)
/*     */   {
/* 729 */     int i = 0;
/* 730 */     boolean[] used = this.used;
/*     */ 
/* 732 */     double[] key = this.key;
/* 733 */     byte[] value = this.value;
/* 734 */     int newMask = newN - 1;
/* 735 */     double[] newKey = new double[newN];
/* 736 */     byte[] newValue = new byte[newN];
/* 737 */     boolean[] newUsed = new boolean[newN];
/* 738 */     for (int j = this.size; j-- != 0; ) {
/* 739 */       while (used[i] == 0) i++;
/* 740 */       double k = key[i];
/* 741 */       int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & newMask;
/* 742 */       while (newUsed[pos] != 0) pos = pos + 1 & newMask;
/* 743 */       newUsed[pos] = true;
/* 744 */       newKey[pos] = k;
/* 745 */       newValue[pos] = value[i];
/* 746 */       i++;
/*     */     }
/* 748 */     this.n = newN;
/* 749 */     this.mask = newMask;
/* 750 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 751 */     this.key = newKey;
/* 752 */     this.value = newValue;
/* 753 */     this.used = newUsed;
/*     */   }
/*     */ 
/*     */   public Double2ByteOpenHashMap clone()
/*     */   {
/*     */     Double2ByteOpenHashMap c;
/*     */     try
/*     */     {
/* 766 */       c = (Double2ByteOpenHashMap)super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException cantHappen) {
/* 769 */       throw new InternalError();
/*     */     }
/* 771 */     c.keys = null;
/* 772 */     c.values = null;
/* 773 */     c.entries = null;
/* 774 */     c.key = ((double[])this.key.clone());
/* 775 */     c.value = ((byte[])this.value.clone());
/* 776 */     c.used = ((boolean[])this.used.clone());
/* 777 */     return c;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 789 */     int h = 0;
/* 790 */     int j = this.size; int i = 0; for (int t = 0; j-- != 0; ) {
/* 791 */       while (this.used[i] == 0) i++;
/* 792 */       t = HashCommon.double2int(this.key[i]);
/* 793 */       t ^= this.value[i];
/* 794 */       h += t;
/* 795 */       i++;
/*     */     }
/* 797 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 800 */     double[] key = this.key;
/* 801 */     byte[] value = this.value;
/* 802 */     MapIterator i = new MapIterator(null);
/* 803 */     s.defaultWriteObject();
/* 804 */     for (int j = this.size; j-- != 0; ) {
/* 805 */       int e = i.nextEntry();
/* 806 */       s.writeDouble(key[e]);
/* 807 */       s.writeByte(value[e]);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 812 */     s.defaultReadObject();
/* 813 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 814 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 815 */     this.mask = (this.n - 1);
/* 816 */     double[] key = this.key = new double[this.n];
/* 817 */     byte[] value = this.value = new byte[this.n];
/* 818 */     boolean[] used = this.used = new boolean[this.n];
/*     */ 
/* 821 */     int i = this.size; for (int pos = 0; i-- != 0; ) {
/* 822 */       double k = s.readDouble();
/* 823 */       byte v = s.readByte();
/* 824 */       pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 825 */       while (used[pos] != 0) pos = pos + 1 & this.mask;
/* 826 */       used[pos] = true;
/* 827 */       key[pos] = k;
/* 828 */       value[pos] = v;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkTable()
/*     */   {
/*     */   }
/*     */ 
/*     */   private final class ValueIterator extends Double2ByteOpenHashMap.MapIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     public ValueIterator()
/*     */     {
/* 635 */       super(null); } 
/* 636 */     public byte nextByte() { return Double2ByteOpenHashMap.this.value[nextEntry()]; } 
/* 637 */     public Byte next() { return Byte.valueOf(Double2ByteOpenHashMap.this.value[nextEntry()]); }
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
/* 607 */       return new Double2ByteOpenHashMap.KeyIterator(Double2ByteOpenHashMap.this);
/*     */     }
/*     */     public int size() {
/* 610 */       return Double2ByteOpenHashMap.this.size;
/*     */     }
/*     */     public boolean contains(double k) {
/* 613 */       return Double2ByteOpenHashMap.this.containsKey(k);
/*     */     }
/*     */     public boolean remove(double k) {
/* 616 */       int oldSize = Double2ByteOpenHashMap.this.size;
/* 617 */       Double2ByteOpenHashMap.this.remove(k);
/* 618 */       return Double2ByteOpenHashMap.this.size != oldSize;
/*     */     }
/*     */     public void clear() {
/* 621 */       Double2ByteOpenHashMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class KeyIterator extends Double2ByteOpenHashMap.MapIterator
/*     */     implements DoubleIterator
/*     */   {
/*     */     public KeyIterator()
/*     */     {
/* 601 */       super(null); } 
/* 602 */     public double nextDouble() { return Double2ByteOpenHashMap.this.key[nextEntry()]; } 
/* 603 */     public Double next() { return Double.valueOf(Double2ByteOpenHashMap.this.key[nextEntry()]); }
/*     */ 
/*     */   }
/*     */ 
/*     */   private final class MapEntrySet extends AbstractObjectSet<Double2ByteMap.Entry>
/*     */     implements Double2ByteMap.FastEntrySet
/*     */   {
/*     */     private MapEntrySet()
/*     */     {
/*     */     }
/*     */ 
/*     */     public ObjectIterator<Double2ByteMap.Entry> iterator()
/*     */     {
/* 547 */       return new Double2ByteOpenHashMap.EntryIterator(Double2ByteOpenHashMap.this, null);
/*     */     }
/*     */     public ObjectIterator<Double2ByteMap.Entry> fastIterator() {
/* 550 */       return new Double2ByteOpenHashMap.FastEntryIterator(Double2ByteOpenHashMap.this, null);
/*     */     }
/*     */ 
/*     */     public boolean contains(Object o) {
/* 554 */       if (!(o instanceof Map.Entry)) return false;
/* 555 */       Map.Entry e = (Map.Entry)o;
/* 556 */       double k = ((Double)e.getKey()).doubleValue();
/*     */ 
/* 558 */       int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & Double2ByteOpenHashMap.this.mask;
/*     */ 
/* 560 */       while (Double2ByteOpenHashMap.this.used[pos] != 0) {
/* 561 */         if (Double2ByteOpenHashMap.this.key[pos] == k) return Double2ByteOpenHashMap.this.value[pos] == ((Byte)e.getValue()).byteValue();
/* 562 */         pos = pos + 1 & Double2ByteOpenHashMap.this.mask;
/*     */       }
/* 564 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean remove(Object o) {
/* 568 */       if (!(o instanceof Map.Entry)) return false;
/* 569 */       Map.Entry e = (Map.Entry)o;
/* 570 */       double k = ((Double)e.getKey()).doubleValue();
/*     */ 
/* 572 */       int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & Double2ByteOpenHashMap.this.mask;
/*     */ 
/* 574 */       while (Double2ByteOpenHashMap.this.used[pos] != 0) {
/* 575 */         if (Double2ByteOpenHashMap.this.key[pos] == k) {
/* 576 */           Double2ByteOpenHashMap.this.remove(e.getKey());
/* 577 */           return true;
/*     */         }
/* 579 */         pos = pos + 1 & Double2ByteOpenHashMap.this.mask;
/*     */       }
/* 581 */       return false;
/*     */     }
/*     */     public int size() {
/* 584 */       return Double2ByteOpenHashMap.this.size;
/*     */     }
/*     */     public void clear() {
/* 587 */       Double2ByteOpenHashMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class FastEntryIterator extends Double2ByteOpenHashMap.MapIterator
/*     */     implements ObjectIterator<Double2ByteMap.Entry>
/*     */   {
/* 537 */     final AbstractDouble2ByteMap.BasicEntry entry = new AbstractDouble2ByteMap.BasicEntry(0.0D, (byte)0);
/*     */ 
/*     */     private FastEntryIterator()
/*     */     {
/* 536 */       super(null);
/*     */     }
/*     */     public AbstractDouble2ByteMap.BasicEntry next() {
/* 539 */       int e = nextEntry();
/* 540 */       this.entry.key = Double2ByteOpenHashMap.this.key[e];
/* 541 */       this.entry.value = Double2ByteOpenHashMap.this.value[e];
/* 542 */       return this.entry;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class EntryIterator extends Double2ByteOpenHashMap.MapIterator
/*     */     implements ObjectIterator<Double2ByteMap.Entry>
/*     */   {
/*     */     private Double2ByteOpenHashMap.MapEntry entry;
/*     */ 
/*     */     private EntryIterator()
/*     */     {
/* 525 */       super(null);
/*     */     }
/*     */     public Double2ByteMap.Entry next() {
/* 528 */       return this.entry = new Double2ByteOpenHashMap.MapEntry(Double2ByteOpenHashMap.this, nextEntry());
/*     */     }
/*     */ 
/*     */     public void remove() {
/* 532 */       super.remove();
/* 533 */       Double2ByteOpenHashMap.MapEntry.access$102(this.entry, -1);
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
/* 434 */       this.pos = Double2ByteOpenHashMap.this.n;
/*     */ 
/* 437 */       this.last = -1;
/*     */ 
/* 439 */       this.c = Double2ByteOpenHashMap.this.size;
/*     */ 
/* 444 */       boolean[] used = Double2ByteOpenHashMap.this.used;
/* 445 */       while ((this.c != 0) && (used[(--this.pos)] == 0));
/*     */     }
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 448 */       return this.c != 0;
/*     */     }
/*     */     public int nextEntry() {
/* 451 */       if (!hasNext()) throw new NoSuchElementException();
/* 452 */       this.c -= 1;
/*     */ 
/* 454 */       if (this.pos < 0) {
/* 455 */         double k = this.wrapped.getDouble(-(this.last = --this.pos) - 2);
/*     */ 
/* 457 */         int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & Double2ByteOpenHashMap.this.mask;
/*     */ 
/* 459 */         while (Double2ByteOpenHashMap.this.used[pos] != 0) {
/* 460 */           if (Double2ByteOpenHashMap.this.key[pos] == k) return pos;
/* 461 */           pos = pos + 1 & Double2ByteOpenHashMap.this.mask;
/*     */         }
/*     */       }
/* 464 */       this.last = this.pos;
/*     */ 
/* 466 */       if (this.c != 0) {
/* 467 */         boolean[] used = Double2ByteOpenHashMap.this.used;
/* 468 */         while ((this.pos-- != 0) && (used[this.pos] == 0));
/*     */       }
/* 471 */       return this.last;
/*     */     }
/*     */ 
/*     */     protected final int shiftKeys(int pos)
/*     */     {
/*     */       int last;
/*     */       while (true)
/*     */       {
/* 484 */         pos = (last = pos) + 1 & Double2ByteOpenHashMap.this.mask;
/* 485 */         while (Double2ByteOpenHashMap.this.used[pos] != 0) {
/* 486 */           int slot = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(Double2ByteOpenHashMap.this.key[pos])) & Double2ByteOpenHashMap.this.mask;
/* 487 */           if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 488 */           pos = pos + 1 & Double2ByteOpenHashMap.this.mask;
/*     */         }
/* 490 */         if (Double2ByteOpenHashMap.this.used[pos] == 0) break;
/* 491 */         if (pos < last)
/*     */         {
/* 493 */           if (this.wrapped == null) this.wrapped = new DoubleArrayList();
/* 494 */           this.wrapped.add(Double2ByteOpenHashMap.this.key[pos]);
/*     */         }
/* 496 */         Double2ByteOpenHashMap.this.key[last] = Double2ByteOpenHashMap.this.key[pos];
/* 497 */         Double2ByteOpenHashMap.this.value[last] = Double2ByteOpenHashMap.this.value[pos];
/*     */       }
/* 499 */       Double2ByteOpenHashMap.this.used[last] = false;
/* 500 */       return last;
/*     */     }
/*     */ 
/*     */     public void remove() {
/* 504 */       if (this.last == -1) throw new IllegalStateException();
/* 505 */       if (this.pos < -1)
/*     */       {
/* 507 */         Double2ByteOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 2));
/* 508 */         this.last = -1;
/* 509 */         return;
/*     */       }
/* 511 */       Double2ByteOpenHashMap.this.size -= 1;
/* 512 */       if ((shiftKeys(this.last) == this.pos) && (this.c > 0)) {
/* 513 */         this.c += 1;
/* 514 */         nextEntry();
/*     */       }
/* 516 */       this.last = -1;
/*     */     }
/*     */ 
/*     */     public int skip(int n) {
/* 520 */       int i = n;
/* 521 */       while ((i-- != 0) && (hasNext())) nextEntry();
/* 522 */       return n - i - 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class MapEntry
/*     */     implements Double2ByteMap.Entry, Map.Entry<Double, Byte>
/*     */   {
/*     */     private int index;
/*     */ 
/*     */     MapEntry(int index)
/*     */     {
/* 395 */       this.index = index;
/*     */     }
/*     */     public Double getKey() {
/* 398 */       return Double.valueOf(Double2ByteOpenHashMap.this.key[this.index]);
/*     */     }
/*     */     public double getDoubleKey() {
/* 401 */       return Double2ByteOpenHashMap.this.key[this.index];
/*     */     }
/*     */     public Byte getValue() {
/* 404 */       return Byte.valueOf(Double2ByteOpenHashMap.this.value[this.index]);
/*     */     }
/*     */     public byte getByteValue() {
/* 407 */       return Double2ByteOpenHashMap.this.value[this.index];
/*     */     }
/*     */     public byte setValue(byte v) {
/* 410 */       byte oldValue = Double2ByteOpenHashMap.this.value[this.index];
/* 411 */       Double2ByteOpenHashMap.this.value[this.index] = v;
/* 412 */       return oldValue;
/*     */     }
/*     */     public Byte setValue(Byte v) {
/* 415 */       return Byte.valueOf(setValue(v.byteValue()));
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o) {
/* 419 */       if (!(o instanceof Map.Entry)) return false;
/* 420 */       Map.Entry e = (Map.Entry)o;
/* 421 */       return (Double2ByteOpenHashMap.this.key[this.index] == ((Double)e.getKey()).doubleValue()) && (Double2ByteOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*     */     }
/*     */     public int hashCode() {
/* 424 */       return HashCommon.double2int(Double2ByteOpenHashMap.this.key[this.index]) ^ Double2ByteOpenHashMap.this.value[this.index];
/*     */     }
/*     */     public String toString() {
/* 427 */       return Double2ByteOpenHashMap.this.key[this.index] + "=>" + Double2ByteOpenHashMap.this.value[this.index];
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.doubles.Double2ByteOpenHashMap
 * JD-Core Version:    0.6.2
 */