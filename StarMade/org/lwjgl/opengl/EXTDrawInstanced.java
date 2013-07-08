/*  1:   */package org.lwjgl.opengl;
/*  2:   */
/*  3:   */import java.nio.ByteBuffer;
/*  4:   */import java.nio.IntBuffer;
/*  5:   */import java.nio.ShortBuffer;
/*  6:   */import org.lwjgl.BufferChecks;
/*  7:   */import org.lwjgl.MemoryUtil;
/*  8:   */
/*  9:   */public final class EXTDrawInstanced
/* 10:   */{
/* 11:   */  public static void glDrawArraysInstancedEXT(int mode, int first, int count, int primcount)
/* 12:   */  {
/* 13:13 */    ContextCapabilities caps = GLContext.getCapabilities();
/* 14:14 */    long function_pointer = caps.glDrawArraysInstancedEXT;
/* 15:15 */    BufferChecks.checkFunctionAddress(function_pointer);
/* 16:16 */    nglDrawArraysInstancedEXT(mode, first, count, primcount, function_pointer);
/* 17:   */  }
/* 18:   */  
/* 19:   */  static native void nglDrawArraysInstancedEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
/* 20:   */  
/* 21:21 */  public static void glDrawElementsInstancedEXT(int mode, ByteBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
/* 22:22 */    long function_pointer = caps.glDrawElementsInstancedEXT;
/* 23:23 */    BufferChecks.checkFunctionAddress(function_pointer);
/* 24:24 */    GLChecks.ensureElementVBOdisabled(caps);
/* 25:25 */    BufferChecks.checkDirect(indices);
/* 26:26 */    nglDrawElementsInstancedEXT(mode, indices.remaining(), 5121, MemoryUtil.getAddress(indices), primcount, function_pointer);
/* 27:   */  }
/* 28:   */  
/* 29:29 */  public static void glDrawElementsInstancedEXT(int mode, IntBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
/* 30:30 */    long function_pointer = caps.glDrawElementsInstancedEXT;
/* 31:31 */    BufferChecks.checkFunctionAddress(function_pointer);
/* 32:32 */    GLChecks.ensureElementVBOdisabled(caps);
/* 33:33 */    BufferChecks.checkDirect(indices);
/* 34:34 */    nglDrawElementsInstancedEXT(mode, indices.remaining(), 5125, MemoryUtil.getAddress(indices), primcount, function_pointer);
/* 35:   */  }
/* 36:   */  
/* 37:37 */  public static void glDrawElementsInstancedEXT(int mode, ShortBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
/* 38:38 */    long function_pointer = caps.glDrawElementsInstancedEXT;
/* 39:39 */    BufferChecks.checkFunctionAddress(function_pointer);
/* 40:40 */    GLChecks.ensureElementVBOdisabled(caps);
/* 41:41 */    BufferChecks.checkDirect(indices);
/* 42:42 */    nglDrawElementsInstancedEXT(mode, indices.remaining(), 5123, MemoryUtil.getAddress(indices), primcount, function_pointer); }
/* 43:   */  
/* 44:   */  static native void nglDrawElementsInstancedEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2);
/* 45:   */  
/* 46:46 */  public static void glDrawElementsInstancedEXT(int mode, int indices_count, int type, long indices_buffer_offset, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
/* 47:47 */    long function_pointer = caps.glDrawElementsInstancedEXT;
/* 48:48 */    BufferChecks.checkFunctionAddress(function_pointer);
/* 49:49 */    GLChecks.ensureElementVBOenabled(caps);
/* 50:50 */    nglDrawElementsInstancedEXTBO(mode, indices_count, type, indices_buffer_offset, primcount, function_pointer);
/* 51:   */  }
/* 52:   */  
/* 53:   */  static native void nglDrawElementsInstancedEXTBO(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2);
/* 54:   */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.lwjgl.opengl.EXTDrawInstanced
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */