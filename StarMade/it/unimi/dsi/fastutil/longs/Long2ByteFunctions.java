/*   1:    */package it.unimi.dsi.fastutil.longs;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */
/*  53:    */public class Long2ByteFunctions
/*  54:    */{
/*  55:    */  public static class EmptyFunction
/*  56:    */    extends AbstractLong2ByteFunction
/*  57:    */    implements Serializable, Cloneable
/*  58:    */  {
/*  59:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  60:    */    
/*  61: 61 */    public byte get(long k) { return 0; }
/*  62: 62 */    public boolean containsKey(long k) { return false; }
/*  63: 63 */    public byte defaultReturnValue() { return 0; }
/*  64: 64 */    public void defaultReturnValue(byte defRetValue) { throw new UnsupportedOperationException(); }
/*  65: 65 */    public Byte get(Object k) { return null; }
/*  66: 66 */    public int size() { return 0; }
/*  67:    */    public void clear() {}
/*  68: 68 */    private Object readResolve() { return Long2ByteFunctions.EMPTY_FUNCTION; }
/*  69: 69 */    public Object clone() { return Long2ByteFunctions.EMPTY_FUNCTION; }
/*  70:    */  }
/*  71:    */  
/*  73: 73 */  public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();
/*  74:    */  
/*  76:    */  public static class Singleton
/*  77:    */    extends AbstractLong2ByteFunction
/*  78:    */    implements Serializable, Cloneable
/*  79:    */  {
/*  80:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  81:    */    protected final long key;
/*  82:    */    protected final byte value;
/*  83:    */    
/*  84:    */    protected Singleton(long key, byte value)
/*  85:    */    {
/*  86: 86 */      this.key = key;
/*  87: 87 */      this.value = value;
/*  88:    */    }
/*  89:    */    
/*  90: 90 */    public boolean containsKey(long k) { return this.key == k; }
/*  91:    */    
/*  92: 92 */    public byte get(long k) { if (this.key == k) return this.value; return this.defRetValue; }
/*  93:    */    
/*  94: 94 */    public int size() { return 1; }
/*  95:    */    
/*  96: 96 */    public Object clone() { return this; }
/*  97:    */  }
/*  98:    */  
/* 107:    */  public static Long2ByteFunction singleton(long key, byte value)
/* 108:    */  {
/* 109:109 */    return new Singleton(key, value);
/* 110:    */  }
/* 111:    */  
/* 122:    */  public static Long2ByteFunction singleton(Long key, Byte value)
/* 123:    */  {
/* 124:124 */    return new Singleton(key.longValue(), value.byteValue());
/* 125:    */  }
/* 126:    */  
/* 128:    */  public static class SynchronizedFunction
/* 129:    */    extends AbstractLong2ByteFunction
/* 130:    */    implements Serializable
/* 131:    */  {
/* 132:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 133:    */    
/* 134:    */    protected final Long2ByteFunction function;
/* 135:    */    
/* 136:    */    protected final Object sync;
/* 137:    */    
/* 138:    */    protected SynchronizedFunction(Long2ByteFunction f, Object sync)
/* 139:    */    {
/* 140:140 */      if (f == null) throw new NullPointerException();
/* 141:141 */      this.function = f;
/* 142:142 */      this.sync = sync;
/* 143:    */    }
/* 144:    */    
/* 145:    */    protected SynchronizedFunction(Long2ByteFunction f) {
/* 146:146 */      if (f == null) throw new NullPointerException();
/* 147:147 */      this.function = f;
/* 148:148 */      this.sync = this;
/* 149:    */    }
/* 150:    */    
/* 151:151 */    public int size() { synchronized (this.sync) { return this.function.size(); } }
/* 152:152 */    public boolean containsKey(long k) { synchronized (this.sync) { return this.function.containsKey(k); } }
/* 153:    */    
/* 154:154 */    public byte defaultReturnValue() { synchronized (this.sync) { return this.function.defaultReturnValue(); } }
/* 155:155 */    public void defaultReturnValue(byte defRetValue) { synchronized (this.sync) { this.function.defaultReturnValue(defRetValue); } }
/* 156:    */    
/* 157:157 */    public byte put(long k, byte v) { synchronized (this.sync) { return this.function.put(k, v); } }
/* 158:    */    
/* 159:159 */    public void clear() { synchronized (this.sync) { this.function.clear(); } }
/* 160:160 */    public String toString() { synchronized (this.sync) { return this.function.toString();
/* 161:    */      } }
/* 162:    */    
/* 163:163 */    public Byte put(Long k, Byte v) { synchronized (this.sync) { return (Byte)this.function.put(k, v); } }
/* 164:164 */    public Byte get(Object k) { synchronized (this.sync) { return (Byte)this.function.get(k); } }
/* 165:165 */    public Byte remove(Object k) { synchronized (this.sync) { return (Byte)this.function.remove(k);
/* 166:    */      }
/* 167:    */    }
/* 168:    */    
/* 169:169 */    public byte remove(long k) { synchronized (this.sync) { return this.function.remove(k); } }
/* 170:170 */    public byte get(long k) { synchronized (this.sync) { return this.function.get(k); } }
/* 171:171 */    public boolean containsKey(Object ok) { synchronized (this.sync) { return this.function.containsKey(ok);
/* 172:    */      }
/* 173:    */    }
/* 174:    */  }
/* 175:    */  
/* 185:    */  public static Long2ByteFunction synchronize(Long2ByteFunction f)
/* 186:    */  {
/* 187:187 */    return new SynchronizedFunction(f);
/* 188:    */  }
/* 189:    */  
/* 195:    */  public static Long2ByteFunction synchronize(Long2ByteFunction f, Object sync)
/* 196:    */  {
/* 197:197 */    return new SynchronizedFunction(f, sync);
/* 198:    */  }
/* 199:    */  
/* 201:    */  public static class UnmodifiableFunction
/* 202:    */    extends AbstractLong2ByteFunction
/* 203:    */    implements Serializable
/* 204:    */  {
/* 205:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 206:    */    protected final Long2ByteFunction function;
/* 207:    */    
/* 208:    */    protected UnmodifiableFunction(Long2ByteFunction f)
/* 209:    */    {
/* 210:210 */      if (f == null) throw new NullPointerException();
/* 211:211 */      this.function = f;
/* 212:    */    }
/* 213:    */    
/* 214:214 */    public int size() { return this.function.size(); }
/* 215:215 */    public boolean containsKey(long k) { return this.function.containsKey(k); }
/* 216:    */    
/* 217:217 */    public byte defaultReturnValue() { return this.function.defaultReturnValue(); }
/* 218:218 */    public void defaultReturnValue(byte defRetValue) { throw new UnsupportedOperationException(); }
/* 219:    */    
/* 220:220 */    public byte put(long k, byte v) { throw new UnsupportedOperationException(); }
/* 221:    */    
/* 222:222 */    public void clear() { throw new UnsupportedOperationException(); }
/* 223:223 */    public String toString() { return this.function.toString(); }
/* 224:    */    
/* 226:226 */    public byte remove(long k) { throw new UnsupportedOperationException(); }
/* 227:227 */    public byte get(long k) { return this.function.get(k); }
/* 228:228 */    public boolean containsKey(Object ok) { return this.function.containsKey(ok); }
/* 229:    */  }
/* 230:    */  
/* 242:    */  public static Long2ByteFunction unmodifiable(Long2ByteFunction f)
/* 243:    */  {
/* 244:244 */    return new UnmodifiableFunction(f);
/* 245:    */  }
/* 246:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.longs.Long2ByteFunctions
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */