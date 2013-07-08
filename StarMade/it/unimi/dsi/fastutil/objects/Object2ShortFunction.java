package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public abstract interface Object2ShortFunction<K>
  extends Function<K, Short>
{
  public abstract short put(K paramK, short paramShort);
  
  public abstract short getShort(Object paramObject);
  
  public abstract short removeShort(Object paramObject);
  
  public abstract void defaultReturnValue(short paramShort);
  
  public abstract short defaultReturnValue();
}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.Object2ShortFunction
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */