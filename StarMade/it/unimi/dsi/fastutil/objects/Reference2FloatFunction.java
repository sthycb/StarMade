package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public abstract interface Reference2FloatFunction<K>
  extends Function<K, Float>
{
  public abstract float put(K paramK, float paramFloat);
  
  public abstract float getFloat(Object paramObject);
  
  public abstract float removeFloat(Object paramObject);
  
  public abstract void defaultReturnValue(float paramFloat);
  
  public abstract float defaultReturnValue();
}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.Reference2FloatFunction
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */