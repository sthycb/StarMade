/*  1:   */package org.jaxen.expr.iter;
/*  2:   */
/*  3:   */import java.util.Iterator;
/*  4:   */import org.jaxen.ContextSupport;
/*  5:   */import org.jaxen.Navigator;
/*  6:   */import org.jaxen.UnsupportedAxisException;
/*  7:   */
/* 58:   */public class IterableFollowingAxis
/* 59:   */  extends IterableAxis
/* 60:   */{
/* 61:   */  private static final long serialVersionUID = -7100245752300813209L;
/* 62:   */  
/* 63:   */  public IterableFollowingAxis(int value)
/* 64:   */  {
/* 65:65 */    super(value);
/* 66:   */  }
/* 67:   */  
/* 68:   */  public Iterator iterator(Object contextNode, ContextSupport support)
/* 69:   */    throws UnsupportedAxisException
/* 70:   */  {
/* 71:71 */    return support.getNavigator().getFollowingAxisIterator(contextNode);
/* 72:   */  }
/* 73:   */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.jaxen.expr.iter.IterableFollowingAxis
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */