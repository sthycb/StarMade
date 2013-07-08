/*   1:    */package it.unimi.dsi.fastutil.chars;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */
/*  64:    */public abstract class AbstractChar2ShortFunction
/*  65:    */  implements Char2ShortFunction, Serializable
/*  66:    */{
/*  67:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  68:    */  protected short defRetValue;
/*  69:    */  
/*  70:    */  public void defaultReturnValue(short rv)
/*  71:    */  {
/*  72: 72 */    this.defRetValue = rv;
/*  73:    */  }
/*  74:    */  
/*  75: 75 */  public short defaultReturnValue() { return this.defRetValue; }
/*  76:    */  
/*  77:    */  public short put(char key, short value) {
/*  78: 78 */    throw new UnsupportedOperationException();
/*  79:    */  }
/*  80:    */  
/*  81: 81 */  public short remove(char key) { throw new UnsupportedOperationException(); }
/*  82:    */  
/*  83:    */  public void clear() {
/*  84: 84 */    throw new UnsupportedOperationException();
/*  85:    */  }
/*  86:    */  
/*  87: 87 */  public boolean containsKey(Object ok) { return containsKey(((Character)ok).charValue()); }
/*  88:    */  
/*  93:    */  public Short get(Object ok)
/*  94:    */  {
/*  95: 95 */    char k = ((Character)ok).charValue();
/*  96: 96 */    return containsKey(k) ? Short.valueOf(get(k)) : null;
/*  97:    */  }
/*  98:    */  
/* 102:    */  public Short put(Character ok, Short ov)
/* 103:    */  {
/* 104:104 */    char k = ok.charValue();
/* 105:105 */    boolean containsKey = containsKey(k);
/* 106:106 */    short v = put(k, ov.shortValue());
/* 107:107 */    return containsKey ? Short.valueOf(v) : null;
/* 108:    */  }
/* 109:    */  
/* 113:    */  public Short remove(Object ok)
/* 114:    */  {
/* 115:115 */    char k = ((Character)ok).charValue();
/* 116:116 */    boolean containsKey = containsKey(k);
/* 117:117 */    short v = remove(k);
/* 118:118 */    return containsKey ? Short.valueOf(v) : null;
/* 119:    */  }
/* 120:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.chars.AbstractChar2ShortFunction
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */