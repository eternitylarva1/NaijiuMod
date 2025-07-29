/*    */ package Naijiumod.utils;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.Texture;
/*    */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*    */ import java.util.HashMap;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureLoader
/*    */ {
/* 18 */   private static HashMap<String, Texture> textures = new HashMap<>();
/* 19 */   public static final Logger logger = LogManager.getLogger(TextureLoader.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Texture getTexture(String textureString) {
/* 27 */     if (textures.get(textureString) == null) {
/*    */       try {
/* 29 */         loadTexture(textureString);
/* 30 */       } catch (GdxRuntimeException e) {
/* 31 */         logger.error("Could not find texture: " + textureString);
/* 32 */         return getTexture("CardAugmentsResources/images/ui/missing_texture.png");
/*    */       } 
/*    */     }
/* 35 */     return textures.get(textureString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void loadTexture(String textureString) throws GdxRuntimeException {
/* 46 */     logger.info("Card Augments | Loading Texture: " + textureString);
/* 47 */     Texture texture = new Texture(textureString);
/* 48 */     texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
/* 49 */     textures.put(textureString, texture);
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugment\\util\TextureLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */