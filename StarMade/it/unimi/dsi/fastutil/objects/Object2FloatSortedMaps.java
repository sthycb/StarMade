/*   1:    */package it.unimi.dsi.fastutil.objects;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */import java.util.Comparator;
/*   5:    */import java.util.Map.Entry;
/*   6:    */import java.util.NoSuchElementException;
/*   7:    */
/*  59:    */public class Object2FloatSortedMaps
/*  60:    */{
/*  61:    */  public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<K> comparator)
/*  62:    */  {
/*  63: 63 */    new Comparator() {
/*  64:    */      public int compare(Map.Entry<K, ?> x, Map.Entry<K, ?> y) {
/*  65: 65 */        return this.val$comparator.compare(x.getKey(), y.getKey());
/*  66:    */      }
/*  67:    */    };
/*  68:    */  }
/*  69:    */  
/*  71:    */  public static class EmptySortedMap<K>
/*  72:    */    extends Object2FloatMaps.EmptyMap<K>
/*  73:    */    implements Object2FloatSortedMap<K>, Serializable, Cloneable
/*  74:    */  {
/*  75:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  76:    */    
/*  77: 77 */    public Comparator<? super K> comparator() { return null; }
/*  78:    */    
/*  79: 79 */    public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() { return ObjectSortedSets.EMPTY_SET; }
/*  80:    */    
/*  81: 81 */    public ObjectSortedSet<Map.Entry<K, Float>> entrySet() { return ObjectSortedSets.EMPTY_SET; }
/*  82:    */    
/*  83: 83 */    public ObjectSortedSet<K> keySet() { return ObjectSortedSets.EMPTY_SET; }
/*  84:    */    
/*  85: 85 */    public Object2FloatSortedMap<K> subMap(K from, K to) { return Object2FloatSortedMaps.EMPTY_MAP; }
/*  86:    */    
/*  87: 87 */    public Object2FloatSortedMap<K> headMap(K to) { return Object2FloatSortedMaps.EMPTY_MAP; }
/*  88:    */    
/*  89: 89 */    public Object2FloatSortedMap<K> tailMap(K from) { return Object2FloatSortedMaps.EMPTY_MAP; }
/*  90: 90 */    public K firstKey() { throw new NoSuchElementException(); }
/*  91: 91 */    public K lastKey() { throw new NoSuchElementException(); }
/*  92:    */  }
/*  93:    */  
/* 103:103 */  public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/* 104:    */  
/* 107:    */  public static class Singleton<K>
/* 108:    */    extends Object2FloatMaps.Singleton<K>
/* 109:    */    implements Object2FloatSortedMap<K>, Serializable, Cloneable
/* 110:    */  {
/* 111:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 112:    */    
/* 114:    */    protected final Comparator<? super K> comparator;
/* 115:    */    
/* 117:    */    protected Singleton(K key, float value, Comparator<? super K> comparator)
/* 118:    */    {
/* 119:119 */      super(value);
/* 120:120 */      this.comparator = comparator;
/* 121:    */    }
/* 122:    */    
/* 123:    */    protected Singleton(K key, float value) {
/* 124:124 */      this(key, value, null);
/* 125:    */    }
/* 126:    */    
/* 127:    */    final int compare(K k1, K k2)
/* 128:    */    {
/* 129:129 */      return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/* 130:    */    }
/* 131:    */    
/* 132:132 */    public Comparator<? super K> comparator() { return this.comparator; }
/* 133:    */    
/* 134:    */    public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
/* 135:135 */      if (this.entries == null) this.entries = ObjectSortedSets.singleton(new Object2FloatMaps.Singleton.SingletonEntry(this), Object2FloatSortedMaps.entryComparator(this.comparator)); return (ObjectSortedSet)this.entries; }
/* 136:    */    
/* 137:137 */    public ObjectSortedSet<Map.Entry<K, Float>> entrySet() { return object2FloatEntrySet(); }
/* 138:    */    
/* 139:139 */    public ObjectSortedSet<K> keySet() { if (this.keys == null) this.keys = ObjectSortedSets.singleton(this.key, this.comparator); return (ObjectSortedSet)this.keys;
/* 140:    */    }
/* 141:    */    
/* 142:142 */    public Object2FloatSortedMap<K> subMap(K from, K to) { if ((compare(from, this.key) <= 0) && (compare(this.key, to) < 0)) return this; return Object2FloatSortedMaps.EMPTY_MAP;
/* 143:    */    }
/* 144:    */    
/* 145:145 */    public Object2FloatSortedMap<K> headMap(K to) { if (compare(this.key, to) < 0) return this; return Object2FloatSortedMaps.EMPTY_MAP;
/* 146:    */    }
/* 147:    */    
/* 148:148 */    public Object2FloatSortedMap<K> tailMap(K from) { if (compare(from, this.key) <= 0) return this; return Object2FloatSortedMaps.EMPTY_MAP; }
/* 149:    */    
/* 150:150 */    public K firstKey() { return this.key; }
/* 151:151 */    public K lastKey() { return this.key; }
/* 152:    */  }
/* 153:    */  
/* 160:    */  public static <K> Object2FloatSortedMap<K> singleton(K key, Float value)
/* 161:    */  {
/* 162:162 */    return new Singleton(key, value.floatValue());
/* 163:    */  }
/* 164:    */  
/* 172:    */  public static <K> Object2FloatSortedMap<K> singleton(K key, Float value, Comparator<? super K> comparator)
/* 173:    */  {
/* 174:174 */    return new Singleton(key, value.floatValue(), comparator);
/* 175:    */  }
/* 176:    */  
/* 183:    */  public static <K> Object2FloatSortedMap<K> singleton(K key, float value)
/* 184:    */  {
/* 185:185 */    return new Singleton(key, value);
/* 186:    */  }
/* 187:    */  
/* 197:197 */  public static <K> Object2FloatSortedMap<K> singleton(K key, float value, Comparator<? super K> comparator) { return new Singleton(key, value, comparator); }
/* 198:    */  
/* 199:    */  public static class SynchronizedSortedMap<K> extends Object2FloatMaps.SynchronizedMap<K> implements Object2FloatSortedMap<K>, Serializable {
/* 200:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 201:    */    protected final Object2FloatSortedMap<K> sortedMap;
/* 202:    */    
/* 203:    */    protected SynchronizedSortedMap(Object2FloatSortedMap<K> m, Object sync) {
/* 204:204 */      super(sync);
/* 205:205 */      this.sortedMap = m;
/* 206:    */    }
/* 207:    */    
/* 208:208 */    protected SynchronizedSortedMap(Object2FloatSortedMap<K> m) { super();
/* 209:209 */      this.sortedMap = m; }
/* 210:    */    
/* 211:211 */    public Comparator<? super K> comparator() { synchronized (this.sync) { return this.sortedMap.comparator(); } }
/* 212:212 */    public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() { if (this.entries == null) this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2FloatEntrySet(), this.sync); return (ObjectSortedSet)this.entries; }
/* 213:    */    
/* 214:214 */    public ObjectSortedSet<Map.Entry<K, Float>> entrySet() { return object2FloatEntrySet(); }
/* 215:215 */    public ObjectSortedSet<K> keySet() { if (this.keys == null) this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); return (ObjectSortedSet)this.keys; }
/* 216:216 */    public Object2FloatSortedMap<K> subMap(K from, K to) { return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync); }
/* 217:217 */    public Object2FloatSortedMap<K> headMap(K to) { return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync); }
/* 218:218 */    public Object2FloatSortedMap<K> tailMap(K from) { return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync); }
/* 219:219 */    public K firstKey() { synchronized (this.sync) { return this.sortedMap.firstKey(); } }
/* 220:220 */    public K lastKey() { synchronized (this.sync) { return this.sortedMap.lastKey();
/* 221:    */      }
/* 222:    */    }
/* 223:    */  }
/* 224:    */  
/* 226:    */  public static <K> Object2FloatSortedMap<K> synchronize(Object2FloatSortedMap<K> m)
/* 227:    */  {
/* 228:228 */    return new SynchronizedSortedMap(m);
/* 229:    */  }
/* 230:    */  
/* 236:236 */  public static <K> Object2FloatSortedMap<K> synchronize(Object2FloatSortedMap<K> m, Object sync) { return new SynchronizedSortedMap(m, sync); }
/* 237:    */  
/* 238:    */  public static class UnmodifiableSortedMap<K> extends Object2FloatMaps.UnmodifiableMap<K> implements Object2FloatSortedMap<K>, Serializable {
/* 239:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 240:    */    protected final Object2FloatSortedMap<K> sortedMap;
/* 241:    */    
/* 242:242 */    protected UnmodifiableSortedMap(Object2FloatSortedMap<K> m) { super();
/* 243:243 */      this.sortedMap = m; }
/* 244:    */    
/* 245:245 */    public Comparator<? super K> comparator() { return this.sortedMap.comparator(); }
/* 246:246 */    public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() { if (this.entries == null) this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2FloatEntrySet()); return (ObjectSortedSet)this.entries; }
/* 247:    */    
/* 248:248 */    public ObjectSortedSet<Map.Entry<K, Float>> entrySet() { return object2FloatEntrySet(); }
/* 249:249 */    public ObjectSortedSet<K> keySet() { if (this.keys == null) this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); return (ObjectSortedSet)this.keys; }
/* 250:250 */    public Object2FloatSortedMap<K> subMap(K from, K to) { return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to)); }
/* 251:251 */    public Object2FloatSortedMap<K> headMap(K to) { return new UnmodifiableSortedMap(this.sortedMap.headMap(to)); }
/* 252:252 */    public Object2FloatSortedMap<K> tailMap(K from) { return new UnmodifiableSortedMap(this.sortedMap.tailMap(from)); }
/* 253:253 */    public K firstKey() { return this.sortedMap.firstKey(); }
/* 254:254 */    public K lastKey() { return this.sortedMap.lastKey(); }
/* 255:    */  }
/* 256:    */  
/* 260:    */  public static <K> Object2FloatSortedMap<K> unmodifiable(Object2FloatSortedMap<K> m)
/* 261:    */  {
/* 262:262 */    return new UnmodifiableSortedMap(m);
/* 263:    */  }
/* 264:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.Object2FloatSortedMaps
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */