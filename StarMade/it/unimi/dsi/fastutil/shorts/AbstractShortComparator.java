/*    */ package it.unimi.dsi.fastutil.shorts;
/*    */ 
/*    */ public abstract class AbstractShortComparator
/*    */   implements ShortComparator
/*    */ {
/*    */   public int compare(Short ok1, Short ok2)
/*    */   {
/* 57 */     return compare(ok1.shortValue(), ok2.shortValue());
/*    */   }
/*    */ 
/*    */   public abstract int compare(short paramShort1, short paramShort2);
/*    */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.shorts.AbstractShortComparator
 * JD-Core Version:    0.6.2
 */