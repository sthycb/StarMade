/*   1:    */package it.unimi.dsi.fastutil.shorts;
/*   2:    */
/*   3:    */import java.io.IOException;
/*   4:    */import java.io.ObjectInputStream;
/*   5:    */import java.io.ObjectOutputStream;
/*   6:    */import java.io.Serializable;
/*   7:    */import java.util.Collection;
/*   8:    */
/*  55:    */public class ShortArraySet
/*  56:    */  extends AbstractShortSet
/*  57:    */  implements Serializable, Cloneable
/*  58:    */{
/*  59:    */  private static final long serialVersionUID = 1L;
/*  60:    */  private transient short[] a;
/*  61:    */  private int size;
/*  62:    */  
/*  63:    */  public ShortArraySet(short[] a)
/*  64:    */  {
/*  65: 65 */    this.a = a;
/*  66: 66 */    this.size = a.length;
/*  67:    */  }
/*  68:    */  
/*  69:    */  public ShortArraySet()
/*  70:    */  {
/*  71: 71 */    this.a = ShortArrays.EMPTY_ARRAY;
/*  72:    */  }
/*  73:    */  
/*  76:    */  public ShortArraySet(int capacity)
/*  77:    */  {
/*  78: 78 */    this.a = new short[capacity];
/*  79:    */  }
/*  80:    */  
/*  82:    */  public ShortArraySet(ShortCollection c)
/*  83:    */  {
/*  84: 84 */    this(c.size());
/*  85: 85 */    addAll(c);
/*  86:    */  }
/*  87:    */  
/*  89:    */  public ShortArraySet(Collection<? extends Short> c)
/*  90:    */  {
/*  91: 91 */    this(c.size());
/*  92: 92 */    addAll(c);
/*  93:    */  }
/*  94:    */  
/* 100:    */  public ShortArraySet(short[] a, int size)
/* 101:    */  {
/* 102:102 */    this.a = a;
/* 103:103 */    this.size = size;
/* 104:104 */    if (size > a.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
/* 105:    */  }
/* 106:    */  
/* 107:107 */  private int findKey(short o) { for (int i = this.size; i-- != 0;) if (this.a[i] == o) return i;
/* 108:108 */    return -1;
/* 109:    */  }
/* 110:    */  
/* 111:    */  public ShortIterator iterator()
/* 112:    */  {
/* 113:113 */    return ShortIterators.wrap(this.a, 0, this.size);
/* 114:    */  }
/* 115:    */  
/* 116:    */  public boolean contains(short k) {
/* 117:117 */    return findKey(k) != -1;
/* 118:    */  }
/* 119:    */  
/* 120:120 */  public int size() { return this.size; }
/* 121:    */  
/* 123:    */  public boolean remove(short k)
/* 124:    */  {
/* 125:125 */    int pos = findKey(k);
/* 126:126 */    if (pos == -1) return false;
/* 127:127 */    int tail = this.size - pos - 1;
/* 128:128 */    for (int i = 0; i < tail; i++) this.a[(pos + i)] = this.a[(pos + i + 1)];
/* 129:129 */    this.size -= 1;
/* 130:130 */    return true;
/* 131:    */  }
/* 132:    */  
/* 133:    */  public boolean add(short k) {
/* 134:134 */    int pos = findKey(k);
/* 135:135 */    if (pos != -1) return false;
/* 136:136 */    if (this.size == this.a.length) {
/* 137:137 */      short[] b = new short[this.size == 0 ? 2 : this.size * 2];
/* 138:138 */      for (int i = this.size; i-- != 0; b[i] = this.a[i]) {}
/* 139:139 */      this.a = b;
/* 140:    */    }
/* 141:141 */    this.a[(this.size++)] = k;
/* 142:142 */    return true;
/* 143:    */  }
/* 144:    */  
/* 145:    */  public void clear() {
/* 146:146 */    this.size = 0;
/* 147:    */  }
/* 148:    */  
/* 149:    */  public boolean isEmpty()
/* 150:    */  {
/* 151:151 */    return this.size == 0;
/* 152:    */  }
/* 153:    */  
/* 158:    */  public ShortArraySet clone()
/* 159:    */  {
/* 160:    */    ShortArraySet c;
/* 161:    */    
/* 164:    */    try
/* 165:    */    {
/* 166:166 */      c = (ShortArraySet)super.clone();
/* 167:    */    }
/* 168:    */    catch (CloneNotSupportedException cantHappen) {
/* 169:169 */      throw new InternalError();
/* 170:    */    }
/* 171:171 */    c.a = ((short[])this.a.clone());
/* 172:172 */    return c;
/* 173:    */  }
/* 174:    */  
/* 175:    */  private void writeObject(ObjectOutputStream s) throws IOException {
/* 176:176 */    s.defaultWriteObject();
/* 177:177 */    for (int i = 0; i < this.size; i++) s.writeShort(this.a[i]);
/* 178:    */  }
/* 179:    */  
/* 180:    */  private void readObject(ObjectInputStream s)
/* 181:    */    throws IOException, ClassNotFoundException
/* 182:    */  {
/* 183:183 */    s.defaultReadObject();
/* 184:184 */    this.a = new short[this.size];
/* 185:185 */    for (int i = 0; i < this.size; i++) this.a[i] = s.readShort();
/* 186:    */  }
/* 187:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.shorts.ShortArraySet
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */