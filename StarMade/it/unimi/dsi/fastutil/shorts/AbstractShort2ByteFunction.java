/*   1:    */package it.unimi.dsi.fastutil.shorts;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */
/*  64:    */public abstract class AbstractShort2ByteFunction
/*  65:    */  implements Short2ByteFunction, Serializable
/*  66:    */{
/*  67:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  68:    */  protected byte defRetValue;
/*  69:    */  
/*  70:    */  public void defaultReturnValue(byte rv)
/*  71:    */  {
/*  72: 72 */    this.defRetValue = rv;
/*  73:    */  }
/*  74:    */  
/*  75: 75 */  public byte defaultReturnValue() { return this.defRetValue; }
/*  76:    */  
/*  77:    */  public byte put(short key, byte value) {
/*  78: 78 */    throw new UnsupportedOperationException();
/*  79:    */  }
/*  80:    */  
/*  81: 81 */  public byte remove(short key) { throw new UnsupportedOperationException(); }
/*  82:    */  
/*  83:    */  public void clear() {
/*  84: 84 */    throw new UnsupportedOperationException();
/*  85:    */  }
/*  86:    */  
/*  87: 87 */  public boolean containsKey(Object ok) { return containsKey(((Short)ok).shortValue()); }
/*  88:    */  
/*  93:    */  public Byte get(Object ok)
/*  94:    */  {
/*  95: 95 */    short k = ((Short)ok).shortValue();
/*  96: 96 */    return containsKey(k) ? Byte.valueOf(get(k)) : null;
/*  97:    */  }
/*  98:    */  
/* 102:    */  public Byte put(Short ok, Byte ov)
/* 103:    */  {
/* 104:104 */    short k = ok.shortValue();
/* 105:105 */    boolean containsKey = containsKey(k);
/* 106:106 */    byte v = put(k, ov.byteValue());
/* 107:107 */    return containsKey ? Byte.valueOf(v) : null;
/* 108:    */  }
/* 109:    */  
/* 113:    */  public Byte remove(Object ok)
/* 114:    */  {
/* 115:115 */    short k = ((Short)ok).shortValue();
/* 116:116 */    boolean containsKey = containsKey(k);
/* 117:117 */    byte v = remove(k);
/* 118:118 */    return containsKey ? Byte.valueOf(v) : null;
/* 119:    */  }
/* 120:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.shorts.AbstractShort2ByteFunction
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */