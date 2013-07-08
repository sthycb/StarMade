/*   1:    */package it.unimi.dsi.fastutil.shorts;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
/*   4:    */import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*   5:    */import it.unimi.dsi.fastutil.objects.ObjectArraySet;
/*   6:    */import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*   7:    */import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*   8:    */import it.unimi.dsi.fastutil.objects.ObjectCollections;
/*   9:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*  10:    */import java.io.IOException;
/*  11:    */import java.io.ObjectInputStream;
/*  12:    */import java.io.ObjectOutputStream;
/*  13:    */import java.io.Serializable;
/*  14:    */import java.util.Map;
/*  15:    */import java.util.Map.Entry;
/*  16:    */import java.util.NoSuchElementException;
/*  17:    */
/*  65:    */public class Short2ObjectArrayMap<V>
/*  66:    */  extends AbstractShort2ObjectMap<V>
/*  67:    */  implements Serializable, Cloneable
/*  68:    */{
/*  69:    */  private static final long serialVersionUID = 1L;
/*  70:    */  private transient short[] key;
/*  71:    */  private transient Object[] value;
/*  72:    */  private int size;
/*  73:    */  
/*  74:    */  public Short2ObjectArrayMap(short[] key, Object[] value)
/*  75:    */  {
/*  76: 76 */    this.key = key;
/*  77: 77 */    this.value = value;
/*  78: 78 */    this.size = key.length;
/*  79: 79 */    if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*  80:    */  }
/*  81:    */  
/*  82:    */  public Short2ObjectArrayMap()
/*  83:    */  {
/*  84: 84 */    this.key = ShortArrays.EMPTY_ARRAY;
/*  85: 85 */    this.value = ObjectArrays.EMPTY_ARRAY;
/*  86:    */  }
/*  87:    */  
/*  90:    */  public Short2ObjectArrayMap(int capacity)
/*  91:    */  {
/*  92: 92 */    this.key = new short[capacity];
/*  93: 93 */    this.value = new Object[capacity];
/*  94:    */  }
/*  95:    */  
/*  98:    */  public Short2ObjectArrayMap(Short2ObjectMap<V> m)
/*  99:    */  {
/* 100:100 */    this(m.size());
/* 101:101 */    putAll(m);
/* 102:    */  }
/* 103:    */  
/* 106:    */  public Short2ObjectArrayMap(Map<? extends Short, ? extends V> m)
/* 107:    */  {
/* 108:108 */    this(m.size());
/* 109:109 */    putAll(m);
/* 110:    */  }
/* 111:    */  
/* 118:    */  public Short2ObjectArrayMap(short[] key, Object[] value, int size)
/* 119:    */  {
/* 120:120 */    this.key = key;
/* 121:121 */    this.value = value;
/* 122:122 */    this.size = size;
/* 123:123 */    if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/* 124:124 */    if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
/* 125:    */  }
/* 126:    */  
/* 127:    */  private final class EntrySet extends AbstractObjectSet<Short2ObjectMap.Entry<V>> implements Short2ObjectMap.FastEntrySet<V> { private EntrySet() {}
/* 128:    */    
/* 129:129 */    public ObjectIterator<Short2ObjectMap.Entry<V>> iterator() { new AbstractObjectIterator() {
/* 130:130 */        int next = 0;
/* 131:    */        
/* 132:132 */        public boolean hasNext() { return this.next < Short2ObjectArrayMap.this.size; }
/* 133:    */        
/* 134:    */        public Short2ObjectMap.Entry<V> next()
/* 135:    */        {
/* 136:136 */          if (!hasNext()) throw new NoSuchElementException();
/* 137:137 */          return new AbstractShort2ObjectMap.BasicEntry(Short2ObjectArrayMap.this.key[this.next], Short2ObjectArrayMap.this.value[(this.next++)]);
/* 138:    */        }
/* 139:    */      }; }
/* 140:    */    
/* 141:    */    public ObjectIterator<Short2ObjectMap.Entry<V>> fastIterator() {
/* 142:142 */      new AbstractObjectIterator() {
/* 143:143 */        int next = 0;
/* 144:144 */        final AbstractShort2ObjectMap.BasicEntry<V> entry = new AbstractShort2ObjectMap.BasicEntry((short)0, null);
/* 145:    */        
/* 146:146 */        public boolean hasNext() { return this.next < Short2ObjectArrayMap.this.size; }
/* 147:    */        
/* 148:    */        public Short2ObjectMap.Entry<V> next()
/* 149:    */        {
/* 150:150 */          if (!hasNext()) throw new NoSuchElementException();
/* 151:151 */          this.entry.key = Short2ObjectArrayMap.this.key[this.next];
/* 152:152 */          this.entry.value = Short2ObjectArrayMap.this.value[(this.next++)];
/* 153:153 */          return this.entry;
/* 154:    */        }
/* 155:    */      };
/* 156:    */    }
/* 157:    */    
/* 158:158 */    public int size() { return Short2ObjectArrayMap.this.size; }
/* 159:    */    
/* 160:    */    public boolean contains(Object o)
/* 161:    */    {
/* 162:162 */      if (!(o instanceof Map.Entry)) return false;
/* 163:163 */      Map.Entry<Short, V> e = (Map.Entry)o;
/* 164:164 */      short k = ((Short)e.getKey()).shortValue();
/* 165:165 */      return (Short2ObjectArrayMap.this.containsKey(k)) && (Short2ObjectArrayMap.this.get(k) == null ? e.getValue() == null : Short2ObjectArrayMap.this.get(k).equals(e.getValue()));
/* 166:    */    }
/* 167:    */  }
/* 168:    */  
/* 169:169 */  public Short2ObjectMap.FastEntrySet<V> short2ObjectEntrySet() { return new EntrySet(null); }
/* 170:    */  
/* 171:    */  private int findKey(short k)
/* 172:    */  {
/* 173:173 */    short[] key = this.key;
/* 174:174 */    for (int i = this.size; i-- != 0;) if (key[i] == k) return i;
/* 175:175 */    return -1;
/* 176:    */  }
/* 177:    */  
/* 182:    */  public V get(short k)
/* 183:    */  {
/* 184:184 */    short[] key = this.key;
/* 185:185 */    for (int i = this.size; i-- != 0;) if (key[i] == k) return this.value[i];
/* 186:186 */    return this.defRetValue;
/* 187:    */  }
/* 188:    */  
/* 189:    */  public int size() {
/* 190:190 */    return this.size;
/* 191:    */  }
/* 192:    */  
/* 194:    */  public void clear()
/* 195:    */  {
/* 196:196 */    for (int i = this.size; i-- != 0;)
/* 197:    */    {
/* 201:201 */      this.value[i] = null;
/* 202:    */    }
/* 203:    */    
/* 205:205 */    this.size = 0;
/* 206:    */  }
/* 207:    */  
/* 208:    */  public boolean containsKey(short k)
/* 209:    */  {
/* 210:210 */    return findKey(k) != -1;
/* 211:    */  }
/* 212:    */  
/* 214:    */  public boolean containsValue(Object v)
/* 215:    */  {
/* 216:216 */    for (int i = this.size; i-- != 0; return true) label5: if (this.value[i] == null ? v != null : !this.value[i].equals(v)) break label5;
/* 217:217 */    return false;
/* 218:    */  }
/* 219:    */  
/* 220:    */  public boolean isEmpty()
/* 221:    */  {
/* 222:222 */    return this.size == 0;
/* 223:    */  }
/* 224:    */  
/* 226:    */  public V put(short k, V v)
/* 227:    */  {
/* 228:228 */    int oldKey = findKey(k);
/* 229:229 */    if (oldKey != -1) {
/* 230:230 */      V oldValue = this.value[oldKey];
/* 231:231 */      this.value[oldKey] = v;
/* 232:232 */      return oldValue;
/* 233:    */    }
/* 234:234 */    if (this.size == this.key.length) {
/* 235:235 */      short[] newKey = new short[this.size == 0 ? 2 : this.size * 2];
/* 236:236 */      Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];
/* 237:237 */      for (int i = this.size; i-- != 0;) {
/* 238:238 */        newKey[i] = this.key[i];
/* 239:239 */        newValue[i] = this.value[i];
/* 240:    */      }
/* 241:241 */      this.key = newKey;
/* 242:242 */      this.value = newValue;
/* 243:    */    }
/* 244:244 */    this.key[this.size] = k;
/* 245:245 */    this.value[this.size] = v;
/* 246:246 */    this.size += 1;
/* 247:247 */    return this.defRetValue;
/* 248:    */  }
/* 249:    */  
/* 256:    */  public V remove(short k)
/* 257:    */  {
/* 258:258 */    int oldPos = findKey(k);
/* 259:259 */    if (oldPos == -1) return this.defRetValue;
/* 260:260 */    V oldValue = this.value[oldPos];
/* 261:261 */    int tail = this.size - oldPos - 1;
/* 262:262 */    for (int i = 0; i < tail; i++) {
/* 263:263 */      this.key[(oldPos + i)] = this.key[(oldPos + i + 1)];
/* 264:264 */      this.value[(oldPos + i)] = this.value[(oldPos + i + 1)];
/* 265:    */    }
/* 266:266 */    this.size -= 1;
/* 267:    */    
/* 271:271 */    this.value[this.size] = null;
/* 272:    */    
/* 273:273 */    return oldValue;
/* 274:    */  }
/* 275:    */  
/* 278:    */  public ShortSet keySet()
/* 279:    */  {
/* 280:280 */    return new ShortArraySet(this.key, this.size);
/* 281:    */  }
/* 282:    */  
/* 283:    */  public ObjectCollection<V> values()
/* 284:    */  {
/* 285:285 */    return ObjectCollections.unmodifiable(new ObjectArraySet(this.value, this.size));
/* 286:    */  }
/* 287:    */  
/* 292:    */  public Short2ObjectArrayMap<V> clone()
/* 293:    */  {
/* 294:    */    Short2ObjectArrayMap<V> c;
/* 295:    */    
/* 298:    */    try
/* 299:    */    {
/* 300:300 */      c = (Short2ObjectArrayMap)super.clone();
/* 301:    */    }
/* 302:    */    catch (CloneNotSupportedException cantHappen) {
/* 303:303 */      throw new InternalError();
/* 304:    */    }
/* 305:305 */    c.key = ((short[])this.key.clone());
/* 306:306 */    c.value = ((Object[])this.value.clone());
/* 307:307 */    return c;
/* 308:    */  }
/* 309:    */  
/* 310:    */  private void writeObject(ObjectOutputStream s) throws IOException {
/* 311:311 */    s.defaultWriteObject();
/* 312:312 */    for (int i = 0; i < this.size; i++) {
/* 313:313 */      s.writeShort(this.key[i]);
/* 314:314 */      s.writeObject(this.value[i]);
/* 315:    */    }
/* 316:    */  }
/* 317:    */  
/* 318:    */  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
/* 319:    */  {
/* 320:320 */    s.defaultReadObject();
/* 321:321 */    this.key = new short[this.size];
/* 322:322 */    this.value = new Object[this.size];
/* 323:323 */    for (int i = 0; i < this.size; i++) {
/* 324:324 */      this.key[i] = s.readShort();
/* 325:325 */      this.value[i] = s.readObject();
/* 326:    */    }
/* 327:    */  }
/* 328:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.shorts.Short2ObjectArrayMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */