package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.Map.Entry;

public abstract interface Reference2ShortMap<K> extends Reference2ShortFunction<K>, Map<K, Short>
{
  public abstract ObjectSet<Map.Entry<K, Short>> entrySet();

  public abstract ObjectSet<Entry<K>> reference2ShortEntrySet();

  public abstract ReferenceSet<K> keySet();

  public abstract ShortCollection values();

  public abstract boolean containsValue(short paramShort);

  public static abstract interface Entry<K> extends Map.Entry<K, Short>
  {
    public abstract short setValue(short paramShort);

    public abstract short getShortValue();
  }

  public static abstract interface FastEntrySet<K> extends ObjectSet<Reference2ShortMap.Entry<K>>
  {
    public abstract ObjectIterator<Reference2ShortMap.Entry<K>> fastIterator();
  }
}

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.Reference2ShortMap
 * JD-Core Version:    0.6.2
 */