/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public abstract class AbstractReference2LongSortedMap<K> extends AbstractReference2LongMap<K>
/*     */   implements Reference2LongSortedMap<K>
/*     */ {
/*     */   public static final long serialVersionUID = -1773560792952436569L;
/*     */ 
/*     */   public ReferenceSortedSet<K> keySet()
/*     */   {
/*  69 */     return new KeySet();
/*     */   }
/*     */ 
/*     */   public LongCollection values()
/*     */   {
/* 112 */     return new ValuesCollection();
/*     */   }
/*     */ 
/*     */   public ObjectSortedSet<Map.Entry<K, Long>> entrySet()
/*     */   {
/* 136 */     return reference2LongEntrySet();
/*     */   }
/*     */ 
/*     */   protected static class ValuesIterator<K> extends AbstractLongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Map.Entry<K, Long>> i;
/*     */ 
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Long>> i)
/*     */     {
/* 129 */       this.i = i;
/*     */     }
/* 131 */     public long nextLong() { return ((Long)((Map.Entry)this.i.next()).getValue()).longValue(); } 
/* 132 */     public boolean hasNext() { return this.i.hasNext(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   protected class ValuesCollection extends AbstractLongCollection
/*     */   {
/*     */     protected ValuesCollection()
/*     */     {
/*     */     }
/*     */ 
/*     */     public LongIterator iterator()
/*     */     {
/* 116 */       return new AbstractReference2LongSortedMap.ValuesIterator(AbstractReference2LongSortedMap.this.entrySet().iterator()); } 
/* 117 */     public boolean contains(long k) { return AbstractReference2LongSortedMap.this.containsValue(k); } 
/* 118 */     public int size() { return AbstractReference2LongSortedMap.this.size(); } 
/* 119 */     public void clear() { AbstractReference2LongSortedMap.this.clear(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   protected static class KeySetIterator<K> extends AbstractObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Map.Entry<K, Long>> i;
/*     */ 
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Long>> i)
/*     */     {
/*  93 */       this.i = i;
/*     */     }
/*  95 */     public K next() { return ((Map.Entry)this.i.next()).getKey(); } 
/*  96 */     public K previous() { return ((Map.Entry)this.i.previous()).getKey(); } 
/*  97 */     public boolean hasNext() { return this.i.hasNext(); } 
/*  98 */     public boolean hasPrevious() { return this.i.hasPrevious(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   protected class KeySet extends AbstractReferenceSortedSet<K>
/*     */   {
/*     */     protected KeySet()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean contains(Object k)
/*     */     {
/*  73 */       return AbstractReference2LongSortedMap.this.containsKey(k); } 
/*  74 */     public int size() { return AbstractReference2LongSortedMap.this.size(); } 
/*  75 */     public void clear() { AbstractReference2LongSortedMap.this.clear(); } 
/*  76 */     public Comparator<? super K> comparator() { return AbstractReference2LongSortedMap.this.comparator(); } 
/*  77 */     public K first() { return AbstractReference2LongSortedMap.this.firstKey(); } 
/*  78 */     public K last() { return AbstractReference2LongSortedMap.this.lastKey(); } 
/*  79 */     public ReferenceSortedSet<K> headSet(K to) { return AbstractReference2LongSortedMap.this.headMap(to).keySet(); } 
/*  80 */     public ReferenceSortedSet<K> tailSet(K from) { return AbstractReference2LongSortedMap.this.tailMap(from).keySet(); } 
/*  81 */     public ReferenceSortedSet<K> subSet(K from, K to) { return AbstractReference2LongSortedMap.this.subMap(from, to).keySet(); } 
/*  82 */     public ObjectBidirectionalIterator<K> iterator(K from) { return new AbstractReference2LongSortedMap.KeySetIterator(AbstractReference2LongSortedMap.this.entrySet().iterator(new AbstractReference2LongMap.BasicEntry(from, 0L))); } 
/*  83 */     public ObjectBidirectionalIterator<K> iterator() { return new AbstractReference2LongSortedMap.KeySetIterator(AbstractReference2LongSortedMap.this.entrySet().iterator()); }
/*     */ 
/*     */   }
/*     */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.AbstractReference2LongSortedMap
 * JD-Core Version:    0.6.2
 */