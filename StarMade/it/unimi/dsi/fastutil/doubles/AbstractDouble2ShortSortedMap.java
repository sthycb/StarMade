/*   1:    */package it.unimi.dsi.fastutil.doubles;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*   4:    */import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*   5:    */import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*   6:    */import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
/*   7:    */import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*   8:    */import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*   9:    */import java.util.Map.Entry;
/*  10:    */
/*  51:    */public abstract class AbstractDouble2ShortSortedMap
/*  52:    */  extends AbstractDouble2ShortMap
/*  53:    */  implements Double2ShortSortedMap
/*  54:    */{
/*  55:    */  public static final long serialVersionUID = -1773560792952436569L;
/*  56:    */  
/*  57:    */  public Double2ShortSortedMap headMap(Double to)
/*  58:    */  {
/*  59: 59 */    return headMap(to.doubleValue());
/*  60:    */  }
/*  61:    */  
/*  62:    */  public Double2ShortSortedMap tailMap(Double from) {
/*  63: 63 */    return tailMap(from.doubleValue());
/*  64:    */  }
/*  65:    */  
/*  66:    */  public Double2ShortSortedMap subMap(Double from, Double to) {
/*  67: 67 */    return subMap(from.doubleValue(), to.doubleValue());
/*  68:    */  }
/*  69:    */  
/*  70:    */  public Double firstKey() {
/*  71: 71 */    return Double.valueOf(firstDoubleKey());
/*  72:    */  }
/*  73:    */  
/*  74:    */  public Double lastKey() {
/*  75: 75 */    return Double.valueOf(lastDoubleKey());
/*  76:    */  }
/*  77:    */  
/*  89: 89 */  public DoubleSortedSet keySet() { return new KeySet(); }
/*  90:    */  
/*  91:    */  protected class KeySet extends AbstractDoubleSortedSet { protected KeySet() {}
/*  92:    */    
/*  93: 93 */    public boolean contains(double k) { return AbstractDouble2ShortSortedMap.this.containsKey(k); }
/*  94: 94 */    public int size() { return AbstractDouble2ShortSortedMap.this.size(); }
/*  95: 95 */    public void clear() { AbstractDouble2ShortSortedMap.this.clear(); }
/*  96: 96 */    public DoubleComparator comparator() { return AbstractDouble2ShortSortedMap.this.comparator(); }
/*  97: 97 */    public double firstDouble() { return AbstractDouble2ShortSortedMap.this.firstDoubleKey(); }
/*  98: 98 */    public double lastDouble() { return AbstractDouble2ShortSortedMap.this.lastDoubleKey(); }
/*  99: 99 */    public DoubleSortedSet headSet(double to) { return AbstractDouble2ShortSortedMap.this.headMap(to).keySet(); }
/* 100:100 */    public DoubleSortedSet tailSet(double from) { return AbstractDouble2ShortSortedMap.this.tailMap(from).keySet(); }
/* 101:101 */    public DoubleSortedSet subSet(double from, double to) { return AbstractDouble2ShortSortedMap.this.subMap(from, to).keySet(); }
/* 102:    */    
/* 103:103 */    public DoubleBidirectionalIterator iterator(double from) { return new AbstractDouble2ShortSortedMap.KeySetIterator(AbstractDouble2ShortSortedMap.this.entrySet().iterator(new AbstractDouble2ShortMap.BasicEntry(from, (short)0))); }
/* 104:104 */    public DoubleBidirectionalIterator iterator() { return new AbstractDouble2ShortSortedMap.KeySetIterator(AbstractDouble2ShortSortedMap.this.entrySet().iterator()); }
/* 105:    */  }
/* 106:    */  
/* 109:    */  protected static class KeySetIterator
/* 110:    */    extends AbstractDoubleBidirectionalIterator
/* 111:    */  {
/* 112:    */    protected final ObjectBidirectionalIterator<Map.Entry<Double, Short>> i;
/* 113:    */    
/* 116:    */    public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Double, Short>> i)
/* 117:    */    {
/* 118:118 */      this.i = i;
/* 119:    */    }
/* 120:    */    
/* 121:121 */    public double nextDouble() { return ((Double)((Map.Entry)this.i.next()).getKey()).doubleValue(); }
/* 122:122 */    public double previousDouble() { return ((Double)((Map.Entry)this.i.previous()).getKey()).doubleValue(); }
/* 123:    */    
/* 124:124 */    public boolean hasNext() { return this.i.hasNext(); }
/* 125:125 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 126:    */  }
/* 127:    */  
/* 143:143 */  public ShortCollection values() { return new ValuesCollection(); }
/* 144:    */  
/* 145:    */  protected class ValuesCollection extends AbstractShortCollection {
/* 146:    */    protected ValuesCollection() {}
/* 147:    */    
/* 148:148 */    public ShortIterator iterator() { return new AbstractDouble2ShortSortedMap.ValuesIterator(AbstractDouble2ShortSortedMap.this.entrySet().iterator()); }
/* 149:149 */    public boolean contains(short k) { return AbstractDouble2ShortSortedMap.this.containsValue(k); }
/* 150:150 */    public int size() { return AbstractDouble2ShortSortedMap.this.size(); }
/* 151:151 */    public void clear() { AbstractDouble2ShortSortedMap.this.clear(); }
/* 152:    */  }
/* 153:    */  
/* 156:    */  protected static class ValuesIterator
/* 157:    */    extends AbstractShortIterator
/* 158:    */  {
/* 159:    */    protected final ObjectBidirectionalIterator<Map.Entry<Double, Short>> i;
/* 160:    */    
/* 163:    */    public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Double, Short>> i)
/* 164:    */    {
/* 165:165 */      this.i = i;
/* 166:    */    }
/* 167:    */    
/* 168:168 */    public short nextShort() { return ((Short)((Map.Entry)this.i.next()).getValue()).shortValue(); }
/* 169:169 */    public boolean hasNext() { return this.i.hasNext(); }
/* 170:    */  }
/* 171:    */  
/* 172:    */  public ObjectSortedSet<Map.Entry<Double, Short>> entrySet()
/* 173:    */  {
/* 174:174 */    return double2ShortEntrySet();
/* 175:    */  }
/* 176:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortSortedMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */