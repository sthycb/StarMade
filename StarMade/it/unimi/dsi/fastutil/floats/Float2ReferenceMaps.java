/*   1:    */package it.unimi.dsi.fastutil.floats;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.HashCommon;
/*   4:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*   5:    */import it.unimi.dsi.fastutil.objects.ObjectSet;
/*   6:    */import it.unimi.dsi.fastutil.objects.ObjectSets;
/*   7:    */import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*   8:    */import it.unimi.dsi.fastutil.objects.ReferenceCollections;
/*   9:    */import it.unimi.dsi.fastutil.objects.ReferenceSets;
/*  10:    */import java.io.Serializable;
/*  11:    */import java.util.Iterator;
/*  12:    */import java.util.Map;
/*  13:    */import java.util.Map.Entry;
/*  14:    */import java.util.Set;
/*  15:    */
/*  58:    */public class Float2ReferenceMaps
/*  59:    */{
/*  60:    */  public static class EmptyMap<V>
/*  61:    */    extends Float2ReferenceFunctions.EmptyFunction<V>
/*  62:    */    implements Float2ReferenceMap<V>, Serializable, Cloneable
/*  63:    */  {
/*  64:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  65:    */    
/*  66: 66 */    public boolean containsValue(Object v) { return false; }
/*  67: 67 */    public void putAll(Map<? extends Float, ? extends V> m) { throw new UnsupportedOperationException(); }
/*  68:    */    
/*  69: 69 */    public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() { return ObjectSets.EMPTY_SET; }
/*  70:    */    
/*  71: 71 */    public FloatSet keySet() { return FloatSets.EMPTY_SET; }
/*  72:    */    
/*  73: 73 */    public ReferenceCollection<V> values() { return ReferenceSets.EMPTY_SET; }
/*  74: 74 */    private Object readResolve() { return Float2ReferenceMaps.EMPTY_MAP; }
/*  75: 75 */    public Object clone() { return Float2ReferenceMaps.EMPTY_MAP; }
/*  76:    */    
/*  77: 77 */    public boolean isEmpty() { return true; }
/*  78:    */    
/*  80: 80 */    public ObjectSet<Map.Entry<Float, V>> entrySet() { return float2ReferenceEntrySet(); }
/*  81:    */    
/*  82: 82 */    public int hashCode() { return 0; }
/*  83:    */    
/*  84:    */    public boolean equals(Object o) {
/*  85: 85 */      if (!(o instanceof Map)) { return false;
/*  86:    */      }
/*  87: 87 */      return ((Map)o).isEmpty();
/*  88:    */    }
/*  89:    */    
/*  90: 90 */    public String toString() { return "{}"; }
/*  91:    */  }
/*  92:    */  
/*  98: 98 */  public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*  99:    */  
/* 101:    */  public static class Singleton<V>
/* 102:    */    extends Float2ReferenceFunctions.Singleton<V>
/* 103:    */    implements Float2ReferenceMap<V>, Serializable, Cloneable
/* 104:    */  {
/* 105:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 106:    */    
/* 107:    */    protected volatile transient ObjectSet<Float2ReferenceMap.Entry<V>> entries;
/* 108:    */    
/* 109:    */    protected volatile transient FloatSet keys;
/* 110:    */    
/* 111:    */    protected volatile transient ReferenceCollection<V> values;
/* 112:    */    
/* 114:    */    protected Singleton(float key, V value)
/* 115:    */    {
/* 116:116 */      super(value);
/* 117:    */    }
/* 118:    */    
/* 119:119 */    public boolean containsValue(Object v) { return this.value == v; }
/* 120:    */    
/* 124:124 */    public void putAll(Map<? extends Float, ? extends V> m) { throw new UnsupportedOperationException(); }
/* 125:    */    
/* 126:126 */    public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() { if (this.entries == null) this.entries = ObjectSets.singleton(new SingletonEntry()); return this.entries; }
/* 127:127 */    public FloatSet keySet() { if (this.keys == null) this.keys = FloatSets.singleton(this.key); return this.keys; }
/* 128:128 */    public ReferenceCollection<V> values() { if (this.values == null) this.values = ReferenceSets.singleton(this.value); return this.values; }
/* 129:    */    
/* 130:    */    protected class SingletonEntry implements Float2ReferenceMap.Entry<V>, Map.Entry<Float, V> { protected SingletonEntry() {}
/* 131:131 */      public Float getKey() { return Float.valueOf(Float2ReferenceMaps.Singleton.this.key); }
/* 132:132 */      public V getValue() { return Float2ReferenceMaps.Singleton.this.value; }
/* 133:    */      
/* 134:    */      public float getFloatKey() {
/* 135:135 */        return Float2ReferenceMaps.Singleton.this.key;
/* 136:    */      }
/* 137:    */      
/* 143:143 */      public V setValue(V value) { throw new UnsupportedOperationException(); }
/* 144:    */      
/* 145:    */      public boolean equals(Object o) {
/* 146:146 */        if (!(o instanceof Map.Entry)) return false;
/* 147:147 */        Map.Entry<?, ?> e = (Map.Entry)o;
/* 148:    */        
/* 149:149 */        return (Float2ReferenceMaps.Singleton.this.key == ((Float)e.getKey()).floatValue()) && (Float2ReferenceMaps.Singleton.this.value == e.getValue());
/* 150:    */      }
/* 151:    */      
/* 152:152 */      public int hashCode() { return HashCommon.float2int(Float2ReferenceMaps.Singleton.this.key) ^ (Float2ReferenceMaps.Singleton.this.value == null ? 0 : System.identityHashCode(Float2ReferenceMaps.Singleton.this.value)); }
/* 153:153 */      public String toString() { return Float2ReferenceMaps.Singleton.this.key + "->" + Float2ReferenceMaps.Singleton.this.value; }
/* 154:    */    }
/* 155:    */    
/* 156:156 */    public boolean isEmpty() { return false; }
/* 157:    */    
/* 159:159 */    public ObjectSet<Map.Entry<Float, V>> entrySet() { return float2ReferenceEntrySet(); }
/* 160:    */    
/* 161:161 */    public int hashCode() { return HashCommon.float2int(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value)); }
/* 162:    */    
/* 163:    */    public boolean equals(Object o) {
/* 164:164 */      if (o == this) return true;
/* 165:165 */      if (!(o instanceof Map)) { return false;
/* 166:    */      }
/* 167:167 */      Map<?, ?> m = (Map)o;
/* 168:168 */      if (m.size() != 1) return false;
/* 169:169 */      return ((Map.Entry)entrySet().iterator().next()).equals(m.entrySet().iterator().next());
/* 170:    */    }
/* 171:    */    
/* 172:172 */    public String toString() { return "{" + this.key + "=>" + this.value + "}"; }
/* 173:    */  }
/* 174:    */  
/* 183:    */  public static <V> Float2ReferenceMap<V> singleton(float key, V value)
/* 184:    */  {
/* 185:185 */    return new Singleton(key, value);
/* 186:    */  }
/* 187:    */  
/* 198:    */  public static <V> Float2ReferenceMap<V> singleton(Float key, V value)
/* 199:    */  {
/* 200:200 */    return new Singleton(key.floatValue(), value);
/* 201:    */  }
/* 202:    */  
/* 204:    */  public static class SynchronizedMap<V>
/* 205:    */    extends Float2ReferenceFunctions.SynchronizedFunction<V>
/* 206:    */    implements Float2ReferenceMap<V>, Serializable
/* 207:    */  {
/* 208:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 209:    */    
/* 210:    */    protected final Float2ReferenceMap<V> map;
/* 211:    */    
/* 212:    */    protected volatile transient ObjectSet<Float2ReferenceMap.Entry<V>> entries;
/* 213:    */    
/* 214:    */    protected volatile transient FloatSet keys;
/* 215:    */    protected volatile transient ReferenceCollection<V> values;
/* 216:    */    
/* 217:    */    protected SynchronizedMap(Float2ReferenceMap<V> m, Object sync)
/* 218:    */    {
/* 219:219 */      super(sync);
/* 220:220 */      this.map = m;
/* 221:    */    }
/* 222:    */    
/* 223:    */    protected SynchronizedMap(Float2ReferenceMap<V> m) {
/* 224:224 */      super();
/* 225:225 */      this.map = m;
/* 226:    */    }
/* 227:    */    
/* 228:228 */    public int size() { synchronized (this.sync) { return this.map.size(); } }
/* 229:229 */    public boolean containsKey(float k) { synchronized (this.sync) { return this.map.containsKey(k); } }
/* 230:230 */    public boolean containsValue(Object v) { synchronized (this.sync) { return this.map.containsValue(v); } }
/* 231:    */    
/* 232:232 */    public V defaultReturnValue() { synchronized (this.sync) { return this.map.defaultReturnValue(); } }
/* 233:233 */    public void defaultReturnValue(V defRetValue) { synchronized (this.sync) { this.map.defaultReturnValue(defRetValue); } }
/* 234:    */    
/* 235:235 */    public V put(float k, V v) { synchronized (this.sync) { return this.map.put(k, v);
/* 236:    */      } }
/* 237:    */    
/* 238:238 */    public void putAll(Map<? extends Float, ? extends V> m) { synchronized (this.sync) { this.map.putAll(m); } }
/* 239:    */    
/* 240:240 */    public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() { if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.float2ReferenceEntrySet(), this.sync); return this.entries; }
/* 241:241 */    public FloatSet keySet() { if (this.keys == null) this.keys = FloatSets.synchronize(this.map.keySet(), this.sync); return this.keys; }
/* 242:242 */    public ReferenceCollection<V> values() { if (this.values == null) return ReferenceCollections.synchronize(this.map.values(), this.sync); return this.values; }
/* 243:    */    
/* 244:244 */    public void clear() { synchronized (this.sync) { this.map.clear(); } }
/* 245:245 */    public String toString() { synchronized (this.sync) { return this.map.toString();
/* 246:    */      } }
/* 247:    */    
/* 248:248 */    public V put(Float k, V v) { synchronized (this.sync) { return this.map.put(k, v);
/* 249:    */      }
/* 250:    */    }
/* 251:    */    
/* 252:252 */    public V remove(float k) { synchronized (this.sync) { return this.map.remove(k); } }
/* 253:253 */    public V get(float k) { synchronized (this.sync) { return this.map.get(k); } }
/* 254:254 */    public boolean containsKey(Object ok) { synchronized (this.sync) { return this.map.containsKey(ok); } }
/* 255:255 */    public boolean isEmpty() { synchronized (this.sync) { return this.map.isEmpty(); } }
/* 256:256 */    public ObjectSet<Map.Entry<Float, V>> entrySet() { synchronized (this.sync) { return this.map.entrySet(); } }
/* 257:257 */    public int hashCode() { synchronized (this.sync) { return this.map.hashCode(); } }
/* 258:258 */    public boolean equals(Object o) { synchronized (this.sync) { return this.map.equals(o);
/* 259:    */      }
/* 260:    */    }
/* 261:    */  }
/* 262:    */  
/* 264:    */  public static <V> Float2ReferenceMap<V> synchronize(Float2ReferenceMap<V> m)
/* 265:    */  {
/* 266:266 */    return new SynchronizedMap(m);
/* 267:    */  }
/* 268:    */  
/* 274:274 */  public static <V> Float2ReferenceMap<V> synchronize(Float2ReferenceMap<V> m, Object sync) { return new SynchronizedMap(m, sync); }
/* 275:    */  
/* 276:    */  public static class UnmodifiableMap<V> extends Float2ReferenceFunctions.UnmodifiableFunction<V> implements Float2ReferenceMap<V>, Serializable {
/* 277:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 278:    */    protected final Float2ReferenceMap<V> map;
/* 279:    */    protected volatile transient ObjectSet<Float2ReferenceMap.Entry<V>> entries;
/* 280:    */    protected volatile transient FloatSet keys;
/* 281:    */    protected volatile transient ReferenceCollection<V> values;
/* 282:    */    
/* 283:283 */    protected UnmodifiableMap(Float2ReferenceMap<V> m) { super();
/* 284:284 */      this.map = m; }
/* 285:    */    
/* 286:286 */    public int size() { return this.map.size(); }
/* 287:287 */    public boolean containsKey(float k) { return this.map.containsKey(k); }
/* 288:288 */    public boolean containsValue(Object v) { return this.map.containsValue(v); }
/* 289:289 */    public V defaultReturnValue() { throw new UnsupportedOperationException(); }
/* 290:290 */    public void defaultReturnValue(V defRetValue) { throw new UnsupportedOperationException(); }
/* 291:291 */    public V put(float k, V v) { throw new UnsupportedOperationException(); }
/* 292:    */    
/* 293:293 */    public void putAll(Map<? extends Float, ? extends V> m) { throw new UnsupportedOperationException(); }
/* 294:294 */    public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() { if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.float2ReferenceEntrySet()); return this.entries; }
/* 295:295 */    public FloatSet keySet() { if (this.keys == null) this.keys = FloatSets.unmodifiable(this.map.keySet()); return this.keys; }
/* 296:296 */    public ReferenceCollection<V> values() { if (this.values == null) return ReferenceCollections.unmodifiable(this.map.values()); return this.values; }
/* 297:297 */    public void clear() { throw new UnsupportedOperationException(); }
/* 298:298 */    public String toString() { return this.map.toString(); }
/* 299:299 */    public V remove(float k) { throw new UnsupportedOperationException(); }
/* 300:300 */    public V get(float k) { return this.map.get(k); }
/* 301:301 */    public boolean containsKey(Object ok) { return this.map.containsKey(ok); }
/* 302:302 */    public V remove(Object k) { throw new UnsupportedOperationException(); }
/* 303:303 */    public V get(Object k) { return this.map.get(k); }
/* 304:304 */    public boolean isEmpty() { return this.map.isEmpty(); }
/* 305:305 */    public ObjectSet<Map.Entry<Float, V>> entrySet() { return ObjectSets.unmodifiable(this.map.entrySet()); }
/* 306:    */  }
/* 307:    */  
/* 311:    */  public static <V> Float2ReferenceMap<V> unmodifiable(Float2ReferenceMap<V> m)
/* 312:    */  {
/* 313:313 */    return new UnmodifiableMap(m);
/* 314:    */  }
/* 315:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.floats.Float2ReferenceMaps
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */