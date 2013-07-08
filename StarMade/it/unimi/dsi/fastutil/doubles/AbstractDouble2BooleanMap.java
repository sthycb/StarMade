/*   1:    */package it.unimi.dsi.fastutil.doubles;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.HashCommon;
/*   4:    */import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*   5:    */import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
/*   6:    */import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*   7:    */import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*   8:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*   9:    */import it.unimi.dsi.fastutil.objects.ObjectSet;
/*  10:    */import java.io.Serializable;
/*  11:    */import java.util.Iterator;
/*  12:    */import java.util.Map;
/*  13:    */import java.util.Map.Entry;
/*  14:    */import java.util.Set;
/*  15:    */
/*  62:    */public abstract class AbstractDouble2BooleanMap
/*  63:    */  extends AbstractDouble2BooleanFunction
/*  64:    */  implements Double2BooleanMap, Serializable
/*  65:    */{
/*  66:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  67:    */  
/*  68:    */  public boolean containsValue(Object ov)
/*  69:    */  {
/*  70: 70 */    return containsValue(((Boolean)ov).booleanValue());
/*  71:    */  }
/*  72:    */  
/*  73:    */  public boolean containsValue(boolean v) {
/*  74: 74 */    return values().contains(v);
/*  75:    */  }
/*  76:    */  
/*  77:    */  public boolean containsKey(double k) {
/*  78: 78 */    return keySet().contains(k);
/*  79:    */  }
/*  80:    */  
/*  86:    */  public void putAll(Map<? extends Double, ? extends Boolean> m)
/*  87:    */  {
/*  88: 88 */    int n = m.size();
/*  89: 89 */    Iterator<? extends Map.Entry<? extends Double, ? extends Boolean>> i = m.entrySet().iterator();
/*  90: 90 */    if ((m instanceof Double2BooleanMap))
/*  91:    */    {
/*  92: 92 */      while (n-- != 0) {
/*  93: 93 */        Double2BooleanMap.Entry e = (Double2BooleanMap.Entry)i.next();
/*  94: 94 */        put(e.getDoubleKey(), e.getBooleanValue());
/*  95:    */      }
/*  96:    */      
/*  97:    */    }
/*  98:    */    else
/*  99: 99 */      while (n-- != 0) {
/* 100:100 */        Map.Entry<? extends Double, ? extends Boolean> e = (Map.Entry)i.next();
/* 101:101 */        put((Double)e.getKey(), (Boolean)e.getValue());
/* 102:    */      }
/* 103:    */  }
/* 104:    */  
/* 105:    */  public boolean isEmpty() {
/* 106:106 */    return size() == 0;
/* 107:    */  }
/* 108:    */  
/* 110:    */  public static class BasicEntry
/* 111:    */    implements Double2BooleanMap.Entry
/* 112:    */  {
/* 113:    */    protected double key;
/* 114:    */    protected boolean value;
/* 115:    */    
/* 116:    */    public BasicEntry(Double key, Boolean value)
/* 117:    */    {
/* 118:118 */      this.key = key.doubleValue();
/* 119:119 */      this.value = value.booleanValue();
/* 120:    */    }
/* 121:    */    
/* 122:122 */    public BasicEntry(double key, boolean value) { this.key = key;
/* 123:123 */      this.value = value;
/* 124:    */    }
/* 125:    */    
/* 126:    */    public Double getKey()
/* 127:    */    {
/* 128:128 */      return Double.valueOf(this.key);
/* 129:    */    }
/* 130:    */    
/* 131:    */    public double getDoubleKey()
/* 132:    */    {
/* 133:133 */      return this.key;
/* 134:    */    }
/* 135:    */    
/* 136:    */    public Boolean getValue()
/* 137:    */    {
/* 138:138 */      return Boolean.valueOf(this.value);
/* 139:    */    }
/* 140:    */    
/* 141:    */    public boolean getBooleanValue()
/* 142:    */    {
/* 143:143 */      return this.value;
/* 144:    */    }
/* 145:    */    
/* 146:    */    public boolean setValue(boolean value)
/* 147:    */    {
/* 148:148 */      throw new UnsupportedOperationException();
/* 149:    */    }
/* 150:    */    
/* 152:    */    public Boolean setValue(Boolean value)
/* 153:    */    {
/* 154:154 */      return Boolean.valueOf(setValue(value.booleanValue()));
/* 155:    */    }
/* 156:    */    
/* 158:    */    public boolean equals(Object o)
/* 159:    */    {
/* 160:160 */      if (!(o instanceof Map.Entry)) return false;
/* 161:161 */      Map.Entry<?, ?> e = (Map.Entry)o;
/* 162:    */      
/* 163:163 */      return (this.key == ((Double)e.getKey()).doubleValue()) && (this.value == ((Boolean)e.getValue()).booleanValue());
/* 164:    */    }
/* 165:    */    
/* 166:    */    public int hashCode() {
/* 167:167 */      return HashCommon.double2int(this.key) ^ (this.value ? 1231 : 1237);
/* 168:    */    }
/* 169:    */    
/* 170:    */    public String toString()
/* 171:    */    {
/* 172:172 */      return this.key + "->" + this.value;
/* 173:    */    }
/* 174:    */  }
/* 175:    */  
/* 189:    */  public DoubleSet keySet()
/* 190:    */  {
/* 191:191 */    new AbstractDoubleSet()
/* 192:    */    {
/* 193:193 */      public boolean contains(double k) { return AbstractDouble2BooleanMap.this.containsKey(k); }
/* 194:    */      
/* 195:195 */      public int size() { return AbstractDouble2BooleanMap.this.size(); }
/* 196:196 */      public void clear() { AbstractDouble2BooleanMap.this.clear(); }
/* 197:    */      
/* 198:    */      public DoubleIterator iterator() {
/* 199:199 */        new AbstractDoubleIterator() {
/* 200:200 */          final ObjectIterator<Map.Entry<Double, Boolean>> i = AbstractDouble2BooleanMap.this.entrySet().iterator();
/* 201:    */          
/* 202:202 */          public double nextDouble() { return ((Double2BooleanMap.Entry)this.i.next()).getDoubleKey(); }
/* 203:    */          
/* 204:204 */          public boolean hasNext() { return this.i.hasNext(); }
/* 205:    */        };
/* 206:    */      }
/* 207:    */    };
/* 208:    */  }
/* 209:    */  
/* 222:    */  public BooleanCollection values()
/* 223:    */  {
/* 224:224 */    new AbstractBooleanCollection()
/* 225:    */    {
/* 226:226 */      public boolean contains(boolean k) { return AbstractDouble2BooleanMap.this.containsValue(k); }
/* 227:    */      
/* 228:228 */      public int size() { return AbstractDouble2BooleanMap.this.size(); }
/* 229:229 */      public void clear() { AbstractDouble2BooleanMap.this.clear(); }
/* 230:    */      
/* 231:    */      public BooleanIterator iterator() {
/* 232:232 */        new AbstractBooleanIterator() {
/* 233:233 */          final ObjectIterator<Map.Entry<Double, Boolean>> i = AbstractDouble2BooleanMap.this.entrySet().iterator();
/* 234:    */          
/* 235:235 */          public boolean nextBoolean() { return ((Double2BooleanMap.Entry)this.i.next()).getBooleanValue(); }
/* 236:    */          
/* 237:237 */          public boolean hasNext() { return this.i.hasNext(); }
/* 238:    */        };
/* 239:    */      }
/* 240:    */    };
/* 241:    */  }
/* 242:    */  
/* 244:    */  public ObjectSet<Map.Entry<Double, Boolean>> entrySet()
/* 245:    */  {
/* 246:246 */    return double2BooleanEntrySet();
/* 247:    */  }
/* 248:    */  
/* 257:    */  public int hashCode()
/* 258:    */  {
/* 259:259 */    int h = 0;int n = size();
/* 260:260 */    ObjectIterator<? extends Map.Entry<Double, Boolean>> i = entrySet().iterator();
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
/* 278:278 */    ObjectIterator<? extends Map.Entry<Double, Boolean>> i = entrySet().iterator();
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
/* 289:289 */      Double2BooleanMap.Entry e = (Double2BooleanMap.Entry)i.next();
/* 290:    */      
/* 294:294 */      s.append(String.valueOf(e.getDoubleKey()));
/* 295:295 */      s.append("=>");
/* 296:    */      
/* 299:299 */      s.append(String.valueOf(e.getBooleanValue()));
/* 300:    */    }
/* 301:    */    
/* 302:302 */    s.append("}");
/* 303:303 */    return s.toString();
/* 304:    */  }
/* 305:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */