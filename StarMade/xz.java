/*  1:   */import org.schema.schine.graphicsengine.core.settings.StateParameterNotFoundException;
/*  2:   */
/* 20:   */public final class xz
/* 21:   */  extends xA
/* 22:   */{
/* 23:   */  public final Object a(String paramString)
/* 24:   */  {
/* 25:   */    try
/* 26:   */    {
/* 27:27 */      return Integer.valueOf(Integer.parseInt(paramString));
/* 28:28 */    } catch (NumberFormatException localNumberFormatException) { localNumberFormatException.printStackTrace();
/* 29:   */      
/* 30:30 */      throw new StateParameterNotFoundException(paramString, null);
/* 31:   */    }
/* 32:   */  }
/* 33:   */  
/* 34:   */  public final String a()
/* 35:   */  {
/* 36:36 */    return "Int";
/* 37:   */  }
/* 38:   */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     xz
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */