package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.LongCollection;
import java.util.Map;
import java.util.Map.Entry;

public abstract interface Reference2LongMap<K> extends Reference2LongFunction<K>, Map<K, Long>
{
  public abstract ObjectSet<Map.Entry<K, Long>> entrySet();

  public abstract ObjectSet<Entry<K>> reference2LongEntrySet();

  public abstract ReferenceSet<K> keySet();

  public abstract LongCollection values();

  public abstract boolean containsValue(long paramLong);

  public static abstract interface Entry<K> extends Map.Entry<K, Long>
  {
    public abstract long setValue(long paramLong);

    public abstract long getLongValue();
  }

  public static abstract interface FastEntrySet<K> extends ObjectSet<Reference2LongMap.Entry<K>>
  {
    public abstract ObjectIterator<Reference2LongMap.Entry<K>> fastIterator();
  }
}

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.Reference2LongMap
 * JD-Core Version:    0.6.2
 */