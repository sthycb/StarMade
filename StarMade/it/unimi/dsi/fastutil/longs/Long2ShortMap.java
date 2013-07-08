package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.Map.Entry;

public abstract interface Long2ShortMap
  extends Long2ShortFunction, Map<Long, Short>
{
  public abstract ObjectSet<Map.Entry<Long, Short>> entrySet();
  
  public abstract ObjectSet<Entry> long2ShortEntrySet();
  
  public abstract LongSet keySet();
  
  public abstract ShortCollection values();
  
  public abstract boolean containsValue(short paramShort);
  
  public static abstract interface Entry
    extends Map.Entry<Long, Short>
  {
    public abstract long getLongKey();
    
    public abstract short setValue(short paramShort);
    
    public abstract short getShortValue();
  }
  
  public static abstract interface FastEntrySet
    extends ObjectSet<Long2ShortMap.Entry>
  {
    public abstract ObjectIterator<Long2ShortMap.Entry> fastIterator();
  }
}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.longs.Long2ShortMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */