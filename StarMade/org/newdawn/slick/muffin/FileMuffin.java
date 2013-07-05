/*    */ package org.newdawn.slick.muffin;
/*    */ 
/*    */ import java.io.EOFException;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.util.HashMap;
/*    */ import org.newdawn.slick.util.Log;
/*    */ 
/*    */ public class FileMuffin
/*    */   implements Muffin
/*    */ {
/*    */   public void saveFile(HashMap scoreMap, String fileName)
/*    */     throws IOException
/*    */   {
/* 27 */     String userHome = System.getProperty("user.home");
/* 28 */     File file = new File(userHome);
/* 29 */     file = new File(file, ".java");
/* 30 */     if (!file.exists()) {
/* 31 */       file.mkdir();
/*    */     }
/*    */ 
/* 34 */     file = new File(file, fileName);
/* 35 */     FileOutputStream fos = new FileOutputStream(file);
/* 36 */     ObjectOutputStream oos = new ObjectOutputStream(fos);
/*    */ 
/* 39 */     oos.writeObject(scoreMap);
/*    */ 
/* 41 */     oos.close();
/*    */   }
/*    */ 
/*    */   public HashMap loadFile(String fileName)
/*    */     throws IOException
/*    */   {
/* 48 */     HashMap hashMap = new HashMap();
/* 49 */     String userHome = System.getProperty("user.home");
/*    */ 
/* 51 */     File file = new File(userHome);
/* 52 */     file = new File(file, ".java");
/* 53 */     file = new File(file, fileName);
/*    */ 
/* 55 */     if (file.exists()) {
/*    */       try {
/* 57 */         FileInputStream fis = new FileInputStream(file);
/* 58 */         ObjectInputStream ois = new ObjectInputStream(fis);
/*    */ 
/* 60 */         hashMap = (HashMap)ois.readObject();
/*    */ 
/* 62 */         ois.close();
/*    */       }
/*    */       catch (EOFException e) {
/*    */       }
/*    */       catch (ClassNotFoundException e) {
/* 67 */         Log.error(e);
/* 68 */         throw new IOException("Failed to pull state from store - class not found");
/*    */       }
/*    */     }
/*    */ 
/* 72 */     return hashMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.newdawn.slick.muffin.FileMuffin
 * JD-Core Version:    0.6.2
 */