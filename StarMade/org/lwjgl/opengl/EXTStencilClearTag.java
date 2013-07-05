/*    */ package org.lwjgl.opengl;
/*    */ 
/*    */ import org.lwjgl.BufferChecks;
/*    */ 
/*    */ public final class EXTStencilClearTag
/*    */ {
/*    */   public static final int GL_STENCIL_TAG_BITS_EXT = 35058;
/*    */   public static final int GL_STENCIL_CLEAR_TAG_VALUE_EXT = 35059;
/*    */ 
/*    */   public static void glStencilClearTagEXT(int stencilTagBits, int stencilClearTag)
/*    */   {
/* 25 */     ContextCapabilities caps = GLContext.getCapabilities();
/* 26 */     long function_pointer = caps.glStencilClearTagEXT;
/* 27 */     BufferChecks.checkFunctionAddress(function_pointer);
/* 28 */     nglStencilClearTagEXT(stencilTagBits, stencilClearTag, function_pointer);
/*    */   }
/*    */ 
/*    */   static native void nglStencilClearTagEXT(int paramInt1, int paramInt2, long paramLong);
/*    */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.lwjgl.opengl.EXTStencilClearTag
 * JD-Core Version:    0.6.2
 */