/*   1:    */package it.unimi.dsi.fastutil.ints;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*   4:    */import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
/*   5:    */import it.unimi.dsi.fastutil.chars.CharCollection;
/*   6:    */import it.unimi.dsi.fastutil.chars.CharIterator;
/*   7:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*   8:    */import it.unimi.dsi.fastutil.objects.ObjectSet;
/*   9:    */import java.io.Serializable;
/*  10:    */import java.util.Iterator;
/*  11:    */import java.util.Map;
/*  12:    */import java.util.Map.Entry;
/*  13:    */import java.util.Set;
/*  14:    */
/*  62:    */public abstract class AbstractInt2CharMap
/*  63:    */  extends AbstractInt2CharFunction
/*  64:    */  implements Int2CharMap, Serializable
/*  65:    */{
/*  66:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  67:    */  
/*  68:    */  public boolean containsValue(Object ov)
/*  69:    */  {
/*  70: 70 */    return containsValue(((Character)ov).charValue());
/*  71:    */  }
/*  72:    */  
/*  73:    */  public boolean containsValue(char v) {
/*  74: 74 */    return values().contains(v);
/*  75:    */  }
/*  76:    */  
/*  77:    */  public boolean containsKey(int k) {
/*  78: 78 */    return keySet().contains(k);
/*  79:    */  }
/*  80:    */  
/*  86:    */  public void putAll(Map<? extends Integer, ? extends Character> m)
/*  87:    */  {
/*  88: 88 */    int n = m.size();
/*  89: 89 */    Iterator<? extends Map.Entry<? extends Integer, ? extends Character>> i = m.entrySet().iterator();
/*  90: 90 */    if ((m instanceof Int2CharMap))
/*  91:    */    {
/*  92: 92 */      while (n-- != 0) {
/*  93: 93 */        Int2CharMap.Entry e = (Int2CharMap.Entry)i.next();
/*  94: 94 */        put(e.getIntKey(), e.getCharValue());
/*  95:    */      }
/*  96:    */      
/*  97:    */    }
/*  98:    */    else
/*  99: 99 */      while (n-- != 0) {
/* 100:100 */        Map.Entry<? extends Integer, ? extends Character> e = (Map.Entry)i.next();
/* 101:101 */        put((Integer)e.getKey(), (Character)e.getValue());
/* 102:    */      }
/* 103:    */  }
/* 104:    */  
/* 105:    */  public boolean isEmpty() {
/* 106:106 */    return size() == 0;
/* 107:    */  }
/* 108:    */  
/* 110:    */  public static class BasicEntry
/* 111:    */    implements Int2CharMap.Entry
/* 112:    */  {
/* 113:    */    protected int key;
/* 114:    */    protected char value;
/* 115:    */    
/* 116:    */    public BasicEntry(Integer key, Character value)
/* 117:    */    {
/* 118:118 */      this.key = key.intValue();
/* 119:119 */      this.value = value.charValue();
/* 120:    */    }
/* 121:    */    
/* 122:122 */    public BasicEntry(int key, char value) { this.key = key;
/* 123:123 */      this.value = value;
/* 124:    */    }
/* 125:    */    
/* 126:    */    public Integer getKey()
/* 127:    */    {
/* 128:128 */      return Integer.valueOf(this.key);
/* 129:    */    }
/* 130:    */    
/* 131:    */    public int getIntKey()
/* 132:    */    {
/* 133:133 */      return this.key;
/* 134:    */    }
/* 135:    */    
/* 136:    */    public Character getValue()
/* 137:    */    {
/* 138:138 */      return Character.valueOf(this.value);
/* 139:    */    }
/* 140:    */    
/* 141:    */    public char getCharValue()
/* 142:    */    {
/* 143:143 */      return this.value;
/* 144:    */    }
/* 145:    */    
/* 146:    */    public char setValue(char value)
/* 147:    */    {
/* 148:148 */      throw new UnsupportedOperationException();
/* 149:    */    }
/* 150:    */    
/* 152:    */    public Character setValue(Character value)
/* 153:    */    {
/* 154:154 */      return Character.valueOf(setValue(value.charValue()));
/* 155:    */    }
/* 156:    */    
/* 158:    */    public boolean equals(Object o)
/* 159:    */    {
/* 160:160 */      if (!(o instanceof Map.Entry)) return false;
/* 161:161 */      Map.Entry<?, ?> e = (Map.Entry)o;
/* 162:    */      
/* 163:163 */      return (this.key == ((Integer)e.getKey()).intValue()) && (this.value == ((Character)e.getValue()).charValue());
/* 164:    */    }
/* 165:    */    
/* 166:    */    public int hashCode() {
/* 167:167 */      return this.key ^ this.value;
/* 168:    */    }
/* 169:    */    
/* 170:    */    public String toString()
/* 171:    */    {
/* 172:172 */      return this.key + "->" + this.value;
/* 173:    */    }
/* 174:    */  }
/* 175:    */  
/* 189:    */  public IntSet keySet()
/* 190:    */  {
/* 191:191 */    new AbstractIntSet()
/* 192:    */    {
/* 193:193 */      public boolean contains(int k) { return AbstractInt2CharMap.this.containsKey(k); }
/* 194:    */      
/* 195:195 */      public int size() { return AbstractInt2CharMap.this.size(); }
/* 196:196 */      public void clear() { AbstractInt2CharMap.this.clear(); }
/* 197:    */      
/* 198:    */      public IntIterator iterator() {
/* 199:199 */        new AbstractIntIterator() {
/* 200:200 */          final ObjectIterator<Map.Entry<Integer, Character>> i = AbstractInt2CharMap.this.entrySet().iterator();
/* 201:    */          
/* 202:202 */          public int nextInt() { return ((Int2CharMap.Entry)this.i.next()).getIntKey(); }
/* 203:    */          
/* 204:204 */          public boolean hasNext() { return this.i.hasNext(); }
/* 205:    */        };
/* 206:    */      }
/* 207:    */    };
/* 208:    */  }
/* 209:    */  
/* 222:    */  public CharCollection values()
/* 223:    */  {
/* 224:224 */    new AbstractCharCollection()
/* 225:    */    {
/* 226:226 */      public boolean contains(char k) { return AbstractInt2CharMap.this.containsValue(k); }
/* 227:    */      
/* 228:228 */      public int size() { return AbstractInt2CharMap.this.size(); }
/* 229:229 */      public void clear() { AbstractInt2CharMap.this.clear(); }
/* 230:    */      
/* 231:    */      public CharIterator iterator() {
/* 232:232 */        new AbstractCharIterator() {
/* 233:233 */          final ObjectIterator<Map.Entry<Integer, Character>> i = AbstractInt2CharMap.this.entrySet().iterator();
/* 234:    */          
/* 235:235 */          public char nextChar() { return ((Int2CharMap.Entry)this.i.next()).getCharValue(); }
/* 236:    */          
/* 237:237 */          public boolean hasNext() { return this.i.hasNext(); }
/* 238:    */        };
/* 239:    */      }
/* 240:    */    };
/* 241:    */  }
/* 242:    */  
/* 244:    */  public ObjectSet<Map.Entry<Integer, Character>> entrySet()
/* 245:    */  {
/* 246:246 */    return int2CharEntrySet();
/* 247:    */  }
/* 248:    */  
/* 257:    */  public int hashCode()
/* 258:    */  {
/* 259:259 */    int h = 0;int n = size();
/* 260:260 */    ObjectIterator<? extends Map.Entry<Integer, Character>> i = entrySet().iterator();
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
/* 278:278 */    ObjectIterator<? extends Map.Entry<Integer, Character>> i = entrySet().iterator();
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
/* 289:289 */      Int2CharMap.Entry e = (Int2CharMap.Entry)i.next();
/* 290:    */      
/* 294:294 */      s.append(String.valueOf(e.getIntKey()));
/* 295:295 */      s.append("=>");
/* 296:    */      
/* 299:299 */      s.append(String.valueOf(e.getCharValue()));
/* 300:    */    }
/* 301:    */    
/* 302:302 */    s.append("}");
/* 303:303 */    return s.toString();
/* 304:    */  }
/* 305:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.ints.AbstractInt2CharMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */