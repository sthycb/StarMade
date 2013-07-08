/*   1:    */package it.unimi.dsi.fastutil.bytes;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.HashCommon;
/*   4:    */import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*   5:    */import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
/*   6:    */import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*   7:    */import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*   8:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*   9:    */import it.unimi.dsi.fastutil.objects.ObjectSet;
/*  10:    */import java.io.Serializable;
/*  11:    */import java.util.Iterator;
/*  12:    */import java.util.Map;
/*  13:    */import java.util.Map.Entry;
/*  14:    */import java.util.Set;
/*  15:    */
/*  62:    */public abstract class AbstractByte2DoubleMap
/*  63:    */  extends AbstractByte2DoubleFunction
/*  64:    */  implements Byte2DoubleMap, Serializable
/*  65:    */{
/*  66:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  67:    */  
/*  68:    */  public boolean containsValue(Object ov)
/*  69:    */  {
/*  70: 70 */    return containsValue(((Double)ov).doubleValue());
/*  71:    */  }
/*  72:    */  
/*  73:    */  public boolean containsValue(double v) {
/*  74: 74 */    return values().contains(v);
/*  75:    */  }
/*  76:    */  
/*  77:    */  public boolean containsKey(byte k) {
/*  78: 78 */    return keySet().contains(k);
/*  79:    */  }
/*  80:    */  
/*  86:    */  public void putAll(Map<? extends Byte, ? extends Double> m)
/*  87:    */  {
/*  88: 88 */    int n = m.size();
/*  89: 89 */    Iterator<? extends Map.Entry<? extends Byte, ? extends Double>> i = m.entrySet().iterator();
/*  90: 90 */    if ((m instanceof Byte2DoubleMap))
/*  91:    */    {
/*  92: 92 */      while (n-- != 0) {
/*  93: 93 */        Byte2DoubleMap.Entry e = (Byte2DoubleMap.Entry)i.next();
/*  94: 94 */        put(e.getByteKey(), e.getDoubleValue());
/*  95:    */      }
/*  96:    */      
/*  97:    */    }
/*  98:    */    else
/*  99: 99 */      while (n-- != 0) {
/* 100:100 */        Map.Entry<? extends Byte, ? extends Double> e = (Map.Entry)i.next();
/* 101:101 */        put((Byte)e.getKey(), (Double)e.getValue());
/* 102:    */      }
/* 103:    */  }
/* 104:    */  
/* 105:    */  public boolean isEmpty() {
/* 106:106 */    return size() == 0;
/* 107:    */  }
/* 108:    */  
/* 110:    */  public static class BasicEntry
/* 111:    */    implements Byte2DoubleMap.Entry
/* 112:    */  {
/* 113:    */    protected byte key;
/* 114:    */    protected double value;
/* 115:    */    
/* 116:    */    public BasicEntry(Byte key, Double value)
/* 117:    */    {
/* 118:118 */      this.key = key.byteValue();
/* 119:119 */      this.value = value.doubleValue();
/* 120:    */    }
/* 121:    */    
/* 122:122 */    public BasicEntry(byte key, double value) { this.key = key;
/* 123:123 */      this.value = value;
/* 124:    */    }
/* 125:    */    
/* 126:    */    public Byte getKey()
/* 127:    */    {
/* 128:128 */      return Byte.valueOf(this.key);
/* 129:    */    }
/* 130:    */    
/* 131:    */    public byte getByteKey()
/* 132:    */    {
/* 133:133 */      return this.key;
/* 134:    */    }
/* 135:    */    
/* 136:    */    public Double getValue()
/* 137:    */    {
/* 138:138 */      return Double.valueOf(this.value);
/* 139:    */    }
/* 140:    */    
/* 141:    */    public double getDoubleValue()
/* 142:    */    {
/* 143:143 */      return this.value;
/* 144:    */    }
/* 145:    */    
/* 146:    */    public double setValue(double value)
/* 147:    */    {
/* 148:148 */      throw new UnsupportedOperationException();
/* 149:    */    }
/* 150:    */    
/* 152:    */    public Double setValue(Double value)
/* 153:    */    {
/* 154:154 */      return Double.valueOf(setValue(value.doubleValue()));
/* 155:    */    }
/* 156:    */    
/* 158:    */    public boolean equals(Object o)
/* 159:    */    {
/* 160:160 */      if (!(o instanceof Map.Entry)) return false;
/* 161:161 */      Map.Entry<?, ?> e = (Map.Entry)o;
/* 162:    */      
/* 163:163 */      return (this.key == ((Byte)e.getKey()).byteValue()) && (this.value == ((Double)e.getValue()).doubleValue());
/* 164:    */    }
/* 165:    */    
/* 166:    */    public int hashCode() {
/* 167:167 */      return this.key ^ HashCommon.double2int(this.value);
/* 168:    */    }
/* 169:    */    
/* 170:    */    public String toString()
/* 171:    */    {
/* 172:172 */      return this.key + "->" + this.value;
/* 173:    */    }
/* 174:    */  }
/* 175:    */  
/* 189:    */  public ByteSet keySet()
/* 190:    */  {
/* 191:191 */    new AbstractByteSet()
/* 192:    */    {
/* 193:193 */      public boolean contains(byte k) { return AbstractByte2DoubleMap.this.containsKey(k); }
/* 194:    */      
/* 195:195 */      public int size() { return AbstractByte2DoubleMap.this.size(); }
/* 196:196 */      public void clear() { AbstractByte2DoubleMap.this.clear(); }
/* 197:    */      
/* 198:    */      public ByteIterator iterator() {
/* 199:199 */        new AbstractByteIterator() {
/* 200:200 */          final ObjectIterator<Map.Entry<Byte, Double>> i = AbstractByte2DoubleMap.this.entrySet().iterator();
/* 201:    */          
/* 202:202 */          public byte nextByte() { return ((Byte2DoubleMap.Entry)this.i.next()).getByteKey(); }
/* 203:    */          
/* 204:204 */          public boolean hasNext() { return this.i.hasNext(); }
/* 205:    */        };
/* 206:    */      }
/* 207:    */    };
/* 208:    */  }
/* 209:    */  
/* 222:    */  public DoubleCollection values()
/* 223:    */  {
/* 224:224 */    new AbstractDoubleCollection()
/* 225:    */    {
/* 226:226 */      public boolean contains(double k) { return AbstractByte2DoubleMap.this.containsValue(k); }
/* 227:    */      
/* 228:228 */      public int size() { return AbstractByte2DoubleMap.this.size(); }
/* 229:229 */      public void clear() { AbstractByte2DoubleMap.this.clear(); }
/* 230:    */      
/* 231:    */      public DoubleIterator iterator() {
/* 232:232 */        new AbstractDoubleIterator() {
/* 233:233 */          final ObjectIterator<Map.Entry<Byte, Double>> i = AbstractByte2DoubleMap.this.entrySet().iterator();
/* 234:    */          
/* 235:235 */          public double nextDouble() { return ((Byte2DoubleMap.Entry)this.i.next()).getDoubleValue(); }
/* 236:    */          
/* 237:237 */          public boolean hasNext() { return this.i.hasNext(); }
/* 238:    */        };
/* 239:    */      }
/* 240:    */    };
/* 241:    */  }
/* 242:    */  
/* 244:    */  public ObjectSet<Map.Entry<Byte, Double>> entrySet()
/* 245:    */  {
/* 246:246 */    return byte2DoubleEntrySet();
/* 247:    */  }
/* 248:    */  
/* 257:    */  public int hashCode()
/* 258:    */  {
/* 259:259 */    int h = 0;int n = size();
/* 260:260 */    ObjectIterator<? extends Map.Entry<Byte, Double>> i = entrySet().iterator();
/* 261:    */    
/* 262:262 */    while (n-- != 0) h += ((Map.Entry)i.next()).hashCode();
/* 263:263 */    return h;
/* 264:    */  }
/* 265:    */  
/* 266:    */  public boolean equals(Object o) {
/* 267:267 */    if (o == this) return true;
/* 268:268 */    if (!(o instanceof Map)) { return false;
/* 269:    */    }
/* 270:270 */    Map<?, ?> m = (Map)o;
/* 271:271 */    if (m.size() != size()) return false;
/* 272:272 */    return entrySet().containsAll(m.entrySet());
/* 273:    */  }
/* 274:    */  
/* 275:    */  public String toString()
/* 276:    */  {
/* 277:277 */    StringBuilder s = new StringBuilder();
/* 278:278 */    ObjectIterator<? extends Map.Entry<Byte, Double>> i = entrySet().iterator();
/* 279:279 */    int n = size();
/* 280:    */    
/* 281:281 */    boolean first = true;
/* 282:    */    
/* 283:283 */    s.append("{");
/* 284:    */    
/* 285:285 */    while (n-- != 0) {
/* 286:286 */      if (first) first = false; else {
/* 287:287 */        s.append(", ");
/* 288:    */      }
/* 289:289 */      Byte2DoubleMap.Entry e = (Byte2DoubleMap.Entry)i.next();
/* 290:    */      
/* 294:294 */      s.append(String.valueOf(e.getByteKey()));
/* 295:295 */      s.append("=>");
/* 296:    */      
/* 299:299 */      s.append(String.valueOf(e.getDoubleValue()));
/* 300:    */    }
/* 301:    */    
/* 302:302 */    s.append("}");
/* 303:303 */    return s.toString();
/* 304:    */  }
/* 305:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */