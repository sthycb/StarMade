/*   1:    */package it.unimi.dsi.fastutil.objects;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*   4:    */import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
/*   5:    */import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*   6:    */import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*   7:    */import java.util.Comparator;
/*   8:    */import java.util.Map.Entry;
/*   9:    */
/*  63:    */public abstract class AbstractObject2ByteSortedMap<K>
/*  64:    */  extends AbstractObject2ByteMap<K>
/*  65:    */  implements Object2ByteSortedMap<K>
/*  66:    */{
/*  67:    */  public static final long serialVersionUID = -1773560792952436569L;
/*  68:    */  
/*  69: 69 */  public ObjectSortedSet<K> keySet() { return new KeySet(); }
/*  70:    */  
/*  71:    */  protected class KeySet extends AbstractObjectSortedSet<K> { protected KeySet() {}
/*  72:    */    
/*  73: 73 */    public boolean contains(Object k) { return AbstractObject2ByteSortedMap.this.containsKey(k); }
/*  74: 74 */    public int size() { return AbstractObject2ByteSortedMap.this.size(); }
/*  75: 75 */    public void clear() { AbstractObject2ByteSortedMap.this.clear(); }
/*  76: 76 */    public Comparator<? super K> comparator() { return AbstractObject2ByteSortedMap.this.comparator(); }
/*  77: 77 */    public K first() { return AbstractObject2ByteSortedMap.this.firstKey(); }
/*  78: 78 */    public K last() { return AbstractObject2ByteSortedMap.this.lastKey(); }
/*  79: 79 */    public ObjectSortedSet<K> headSet(K to) { return AbstractObject2ByteSortedMap.this.headMap(to).keySet(); }
/*  80: 80 */    public ObjectSortedSet<K> tailSet(K from) { return AbstractObject2ByteSortedMap.this.tailMap(from).keySet(); }
/*  81: 81 */    public ObjectSortedSet<K> subSet(K from, K to) { return AbstractObject2ByteSortedMap.this.subMap(from, to).keySet(); }
/*  82: 82 */    public ObjectBidirectionalIterator<K> iterator(K from) { return new AbstractObject2ByteSortedMap.KeySetIterator(AbstractObject2ByteSortedMap.this.entrySet().iterator(new AbstractObject2ByteMap.BasicEntry(from, (byte)0))); }
/*  83: 83 */    public ObjectBidirectionalIterator<K> iterator() { return new AbstractObject2ByteSortedMap.KeySetIterator(AbstractObject2ByteSortedMap.this.entrySet().iterator()); }
/*  84:    */  }
/*  85:    */  
/*  87:    */  protected static class KeySetIterator<K>
/*  88:    */    extends AbstractObjectBidirectionalIterator<K>
/*  89:    */  {
/*  90:    */    protected final ObjectBidirectionalIterator<Map.Entry<K, Byte>> i;
/*  91:    */    
/*  93: 93 */    public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Byte>> i) { this.i = i; }
/*  94:    */    
/*  95: 95 */    public K next() { return ((Map.Entry)this.i.next()).getKey(); }
/*  96: 96 */    public K previous() { return ((Map.Entry)this.i.previous()).getKey(); }
/*  97: 97 */    public boolean hasNext() { return this.i.hasNext(); }
/*  98: 98 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/*  99:    */  }
/* 100:    */  
/* 112:112 */  public ByteCollection values() { return new ValuesCollection(); }
/* 113:    */  
/* 114:    */  protected class ValuesCollection extends AbstractByteCollection { protected ValuesCollection() {}
/* 115:    */    
/* 116:116 */    public ByteIterator iterator() { return new AbstractObject2ByteSortedMap.ValuesIterator(AbstractObject2ByteSortedMap.this.entrySet().iterator()); }
/* 117:117 */    public boolean contains(byte k) { return AbstractObject2ByteSortedMap.this.containsValue(k); }
/* 118:118 */    public int size() { return AbstractObject2ByteSortedMap.this.size(); }
/* 119:119 */    public void clear() { AbstractObject2ByteSortedMap.this.clear(); }
/* 120:    */  }
/* 121:    */  
/* 123:    */  protected static class ValuesIterator<K>
/* 124:    */    extends AbstractByteIterator
/* 125:    */  {
/* 126:    */    protected final ObjectBidirectionalIterator<Map.Entry<K, Byte>> i;
/* 127:    */    
/* 129:129 */    public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Byte>> i) { this.i = i; }
/* 130:    */    
/* 131:131 */    public byte nextByte() { return ((Byte)((Map.Entry)this.i.next()).getValue()).byteValue(); }
/* 132:132 */    public boolean hasNext() { return this.i.hasNext(); }
/* 133:    */  }
/* 134:    */  
/* 135:    */  public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 136:136 */    return object2ByteEntrySet();
/* 137:    */  }
/* 138:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.AbstractObject2ByteSortedMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */