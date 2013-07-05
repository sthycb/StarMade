/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Font;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.SpriteSheet;
/*    */ import org.newdawn.slick.SpriteSheetFont;
/*    */ import org.newdawn.slick.util.Log;
/*    */ 
/*    */ public class SpriteSheetFontTest extends BasicGame
/*    */ {
/*    */   private Font font;
/*    */   private static AppGameContainer container;
/*    */ 
/*    */   public SpriteSheetFontTest()
/*    */   {
/* 21 */     super("SpriteSheetFont Test");
/*    */   }
/*    */ 
/*    */   public void init(GameContainer container)
/*    */     throws SlickException
/*    */   {
/* 28 */     SpriteSheet sheet = new SpriteSheet("testdata/spriteSheetFont.png", 32, 32);
/* 29 */     this.font = new SpriteSheetFont(sheet, ' ');
/*    */   }
/*    */ 
/*    */   public void render(GameContainer container, Graphics g)
/*    */   {
/* 36 */     g.setBackground(Color.gray);
/* 37 */     this.font.drawString(80.0F, 5.0F, "A FONT EXAMPLE", Color.red);
/* 38 */     this.font.drawString(100.0F, 50.0F, "A MORE COMPLETE LINE");
/*    */   }
/*    */ 
/*    */   public void update(GameContainer container, int delta)
/*    */     throws SlickException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void keyPressed(int key, char c)
/*    */   {
/* 51 */     if (key == 1) {
/* 52 */       System.exit(0);
/*    */     }
/* 54 */     if (key == 57)
/*    */       try {
/* 56 */         container.setDisplayMode(640, 480, false);
/*    */       } catch (SlickException e) {
/* 58 */         Log.error(e);
/*    */       }
/*    */   }
/*    */ 
/*    */   public static void main(String[] argv)
/*    */   {
/*    */     try
/*    */     {
/* 75 */       container = new AppGameContainer(new SpriteSheetFontTest());
/* 76 */       container.setDisplayMode(800, 600, false);
/* 77 */       container.start();
/*    */     } catch (SlickException e) {
/* 79 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.newdawn.slick.tests.SpriteSheetFontTest
 * JD-Core Version:    0.6.2
 */