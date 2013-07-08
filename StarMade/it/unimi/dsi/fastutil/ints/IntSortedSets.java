/*   1:    */package it.unimi.dsi.fastutil.ints;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */import java.util.NoSuchElementException;
/*   5:    */
/*  53:    */public class IntSortedSets
/*  54:    */{
/*  55:    */  public static class EmptySet
/*  56:    */    extends IntSets.EmptySet
/*  57:    */    implements IntSortedSet, Serializable, Cloneable
/*  58:    */  {
/*  59:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  60:    */    
/*  61: 61 */    public boolean remove(int ok) { throw new UnsupportedOperationException(); }
/*  62:    */    @Deprecated
/*  63: 63 */    public IntBidirectionalIterator intIterator() { return iterator(); }
/*  64:    */    
/*  65: 65 */    public IntBidirectionalIterator iterator(int from) { return IntIterators.EMPTY_ITERATOR; }
/*  66:    */    
/*  67: 67 */    public IntSortedSet subSet(int from, int to) { return IntSortedSets.EMPTY_SET; }
/*  68:    */    
/*  69: 69 */    public IntSortedSet headSet(int from) { return IntSortedSets.EMPTY_SET; }
/*  70:    */    
/*  71: 71 */    public IntSortedSet tailSet(int to) { return IntSortedSets.EMPTY_SET; }
/*  72: 72 */    public int firstInt() { throw new NoSuchElementException(); }
/*  73: 73 */    public int lastInt() { throw new NoSuchElementException(); }
/*  74: 74 */    public IntComparator comparator() { return null; }
/*  75: 75 */    public IntSortedSet subSet(Integer from, Integer to) { return IntSortedSets.EMPTY_SET; }
/*  76: 76 */    public IntSortedSet headSet(Integer from) { return IntSortedSets.EMPTY_SET; }
/*  77: 77 */    public IntSortedSet tailSet(Integer to) { return IntSortedSets.EMPTY_SET; }
/*  78: 78 */    public Integer first() { throw new NoSuchElementException(); }
/*  79: 79 */    public Integer last() { throw new NoSuchElementException(); }
/*  80: 80 */    public Object clone() { return IntSortedSets.EMPTY_SET; }
/*  81:    */    
/*  82: 82 */    private Object readResolve() { return IntSortedSets.EMPTY_SET; }
/*  83:    */  }
/*  84:    */  
/*  93: 93 */  public static final EmptySet EMPTY_SET = new EmptySet();
/*  94:    */  
/*  97:    */  public static class Singleton
/*  98:    */    extends IntSets.Singleton
/*  99:    */    implements IntSortedSet, Serializable, Cloneable
/* 100:    */  {
/* 101:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 102:    */    
/* 103:    */    final IntComparator comparator;
/* 104:    */    
/* 106:    */    private Singleton(int element, IntComparator comparator)
/* 107:    */    {
/* 108:108 */      super();
/* 109:109 */      this.comparator = comparator;
/* 110:    */    }
/* 111:    */    
/* 112:    */    private Singleton(int element) {
/* 113:113 */      this(element, null);
/* 114:    */    }
/* 115:    */    
/* 116:    */    final int compare(int k1, int k2)
/* 117:    */    {
/* 118:118 */      return this.comparator == null ? 1 : k1 == k2 ? 0 : k1 < k2 ? -1 : this.comparator.compare(k1, k2);
/* 119:    */    }
/* 120:    */    
/* 121:    */    @Deprecated
/* 122:    */    public IntBidirectionalIterator intIterator() {
/* 123:123 */      return iterator();
/* 124:    */    }
/* 125:    */    
/* 126:    */    public IntBidirectionalIterator iterator(int from) {
/* 127:127 */      IntBidirectionalIterator i = iterator();
/* 128:128 */      if (compare(this.element, from) <= 0) i.next();
/* 129:129 */      return i;
/* 130:    */    }
/* 131:    */    
/* 132:132 */    public IntComparator comparator() { return this.comparator; }
/* 133:    */    
/* 134:    */    public IntSortedSet subSet(int from, int to) {
/* 135:135 */      if ((compare(from, this.element) <= 0) && (compare(this.element, to) < 0)) return this; return IntSortedSets.EMPTY_SET;
/* 136:    */    }
/* 137:    */    
/* 138:138 */    public IntSortedSet headSet(int to) { if (compare(this.element, to) < 0) return this; return IntSortedSets.EMPTY_SET;
/* 139:    */    }
/* 140:    */    
/* 141:141 */    public IntSortedSet tailSet(int from) { if (compare(from, this.element) <= 0) return this; return IntSortedSets.EMPTY_SET; }
/* 142:    */    
/* 143:143 */    public int firstInt() { return this.element; }
/* 144:144 */    public int lastInt() { return this.element; }
/* 145:    */    
/* 147:147 */    public Integer first() { return Integer.valueOf(this.element); }
/* 148:148 */    public Integer last() { return Integer.valueOf(this.element); }
/* 149:    */    
/* 151:151 */    public IntSortedSet subSet(Integer from, Integer to) { return subSet(from.intValue(), to.intValue()); }
/* 152:152 */    public IntSortedSet headSet(Integer to) { return headSet(to.intValue()); }
/* 153:153 */    public IntSortedSet tailSet(Integer from) { return tailSet(from.intValue()); }
/* 154:    */  }
/* 155:    */  
/* 163:    */  public static IntSortedSet singleton(int element)
/* 164:    */  {
/* 165:165 */    return new Singleton(element, null);
/* 166:    */  }
/* 167:    */  
/* 174:    */  public static IntSortedSet singleton(int element, IntComparator comparator)
/* 175:    */  {
/* 176:176 */    return new Singleton(element, comparator, null);
/* 177:    */  }
/* 178:    */  
/* 186:    */  public static IntSortedSet singleton(Object element)
/* 187:    */  {
/* 188:188 */    return new Singleton(((Integer)element).intValue(), null);
/* 189:    */  }
/* 190:    */  
/* 197:    */  public static IntSortedSet singleton(Object element, IntComparator comparator)
/* 198:    */  {
/* 199:199 */    return new Singleton(((Integer)element).intValue(), comparator, null);
/* 200:    */  }
/* 201:    */  
/* 203:    */  public static class SynchronizedSortedSet
/* 204:    */    extends IntSets.SynchronizedSet
/* 205:    */    implements IntSortedSet, Serializable
/* 206:    */  {
/* 207:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 208:    */    
/* 209:    */    protected final IntSortedSet sortedSet;
/* 210:    */    
/* 211:    */    protected SynchronizedSortedSet(IntSortedSet s, Object sync)
/* 212:    */    {
/* 213:213 */      super(sync);
/* 214:214 */      this.sortedSet = s;
/* 215:    */    }
/* 216:    */    
/* 217:    */    protected SynchronizedSortedSet(IntSortedSet s) {
/* 218:218 */      super();
/* 219:219 */      this.sortedSet = s;
/* 220:    */    }
/* 221:    */    
/* 222:222 */    public IntComparator comparator() { synchronized (this.sync) { return this.sortedSet.comparator(); } }
/* 223:    */    
/* 224:224 */    public IntSortedSet subSet(int from, int to) { return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync); }
/* 225:225 */    public IntSortedSet headSet(int to) { return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync); }
/* 226:226 */    public IntSortedSet tailSet(int from) { return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync); }
/* 227:    */    
/* 228:228 */    public IntBidirectionalIterator iterator() { return this.sortedSet.iterator(); }
/* 229:229 */    public IntBidirectionalIterator iterator(int from) { return this.sortedSet.iterator(from); }
/* 230:    */    
/* 231:    */    @Deprecated
/* 232:232 */    public IntBidirectionalIterator intIterator() { return this.sortedSet.iterator(); }
/* 233:    */    
/* 234:234 */    public int firstInt() { synchronized (this.sync) { return this.sortedSet.firstInt(); } }
/* 235:235 */    public int lastInt() { synchronized (this.sync) { return this.sortedSet.lastInt();
/* 236:    */      } }
/* 237:    */    
/* 238:238 */    public Integer first() { synchronized (this.sync) { return (Integer)this.sortedSet.first(); } }
/* 239:239 */    public Integer last() { synchronized (this.sync) { return (Integer)this.sortedSet.last(); } }
/* 240:    */    
/* 241:241 */    public IntSortedSet subSet(Integer from, Integer to) { return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync); }
/* 242:242 */    public IntSortedSet headSet(Integer to) { return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync); }
/* 243:243 */    public IntSortedSet tailSet(Integer from) { return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync); }
/* 244:    */  }
/* 245:    */  
/* 252:    */  public static IntSortedSet synchronize(IntSortedSet s)
/* 253:    */  {
/* 254:254 */    return new SynchronizedSortedSet(s);
/* 255:    */  }
/* 256:    */  
/* 262:    */  public static IntSortedSet synchronize(IntSortedSet s, Object sync)
/* 263:    */  {
/* 264:264 */    return new SynchronizedSortedSet(s, sync);
/* 265:    */  }
/* 266:    */  
/* 268:    */  public static class UnmodifiableSortedSet
/* 269:    */    extends IntSets.UnmodifiableSet
/* 270:    */    implements IntSortedSet, Serializable
/* 271:    */  {
/* 272:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 273:    */    
/* 274:    */    protected final IntSortedSet sortedSet;
/* 275:    */    
/* 277:    */    protected UnmodifiableSortedSet(IntSortedSet s)
/* 278:    */    {
/* 279:279 */      super();
/* 280:280 */      this.sortedSet = s;
/* 281:    */    }
/* 282:    */    
/* 283:283 */    public IntComparator comparator() { return this.sortedSet.comparator(); }
/* 284:    */    
/* 285:285 */    public IntSortedSet subSet(int from, int to) { return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to)); }
/* 286:286 */    public IntSortedSet headSet(int to) { return new UnmodifiableSortedSet(this.sortedSet.headSet(to)); }
/* 287:287 */    public IntSortedSet tailSet(int from) { return new UnmodifiableSortedSet(this.sortedSet.tailSet(from)); }
/* 288:    */    
/* 289:289 */    public IntBidirectionalIterator iterator() { return IntIterators.unmodifiable(this.sortedSet.iterator()); }
/* 290:290 */    public IntBidirectionalIterator iterator(int from) { return IntIterators.unmodifiable(this.sortedSet.iterator(from)); }
/* 291:    */    
/* 292:    */    @Deprecated
/* 293:293 */    public IntBidirectionalIterator intIterator() { return iterator(); }
/* 294:    */    
/* 295:295 */    public int firstInt() { return this.sortedSet.firstInt(); }
/* 296:296 */    public int lastInt() { return this.sortedSet.lastInt(); }
/* 297:    */    
/* 299:299 */    public Integer first() { return (Integer)this.sortedSet.first(); }
/* 300:300 */    public Integer last() { return (Integer)this.sortedSet.last(); }
/* 301:    */    
/* 302:302 */    public IntSortedSet subSet(Integer from, Integer to) { return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to)); }
/* 303:303 */    public IntSortedSet headSet(Integer to) { return new UnmodifiableSortedSet(this.sortedSet.headSet(to)); }
/* 304:304 */    public IntSortedSet tailSet(Integer from) { return new UnmodifiableSortedSet(this.sortedSet.tailSet(from)); }
/* 305:    */  }
/* 306:    */  
/* 313:    */  public static IntSortedSet unmodifiable(IntSortedSet s)
/* 314:    */  {
/* 315:315 */    return new UnmodifiableSortedSet(s);
/* 316:    */  }
/* 317:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.ints.IntSortedSets
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */