/*     */ package Naijiumod.utils;
/*     */ 
/*     */ import basemod.Pair;
/*     */ import basemod.abstracts.CustomCard;
/*     */ import com.badlogic.gdx.graphics.Color;
/*     */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*     */ import com.badlogic.gdx.graphics.Pixmap;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.graphics.glutils.FrameBuffer;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.ByRef;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.helpers.CardLibrary;
/*     */ import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PortraitHelper
/*     */ {
/*  23 */   private static final Texture attackMask = TextureLoader.getTexture("NaijiuModResources/images/cards/AttackMask.png");
/*  24 */   private static final Texture skillMask = TextureLoader.getTexture("NaijiuModResources/images/cards/SkillMask.png");
/*  25 */   private static final Texture powerMask = TextureLoader.getTexture("NaijiuModResources/images/cards/PowerMask.png");
/*     */   private static final int WIDTH = 250;
/*     */   private static final int HEIGHT = 190;
/*  28 */   private static final HashMap<Pair<String, AbstractCard.CardType>, Pair<TextureAtlas.AtlasRegion, Texture>> hashedTextures = new HashMap<>();
/*     */   
/*     */   public static void setMaskedPortrait(AbstractCard card) {
/*  31 */     Pair<String, AbstractCard.CardType> key = new Pair(card.cardID, card.type);
/*  32 */     if (hashedTextures.containsKey(key)) {
/*  33 */       card.portrait = (TextureAtlas.AtlasRegion)((Pair)hashedTextures.get(key)).getKey();
/*     */     } else {
/*  35 */       Texture temp = makeMaskedTexture(card, 2);
/*  36 */       card.portrait = new TextureAtlas.AtlasRegion(makeMaskedTexture(card, 1), 0, 0, 250, 190);
/*  37 */       hashedTextures.put(key, new Pair(card.portrait, temp));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Texture makeMaskedTexture(AbstractCard card, int multi) {
/*  42 */     int width = 250 * multi;
/*  43 */     int height = 190 * multi;
/*     */     
/*  45 */     AbstractCard baseCard = CardLibrary.getCard(card.cardID).makeCopy();
/*  46 */     TextureAtlas.AtlasRegion t = baseCard.portrait;
/*  47 */     FrameBuffer fb = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
/*  48 */     SpriteBatch sb = new SpriteBatch();
/*  49 */     OrthographicCamera og = new OrthographicCamera(width, height);
/*  50 */     t.flip(false, true);
/*  51 */     if (baseCard.type == AbstractCard.CardType.ATTACK) {
/*  52 */       if (card.type == AbstractCard.CardType.POWER) {
/*     */         
/*  54 */         og.zoom = 0.976F;
/*  55 */         og.translate(-3.0F, 0.0F);
/*     */       } else {
/*     */         
/*  58 */         og.zoom = 0.9F;
/*  59 */         og.translate(0.0F, -10.0F);
/*     */       } 
/*  61 */     } else if (baseCard.type == AbstractCard.CardType.POWER) {
/*  62 */       if (card.type == AbstractCard.CardType.ATTACK) {
/*     */         
/*  64 */         og.zoom = 0.9F;
/*  65 */         og.translate(0.0F, -10.0F);
/*     */       } else {
/*     */         
/*  68 */         og.zoom = 0.825F;
/*  69 */         og.translate(-1.0F, -18.0F);
/*     */       }
/*     */     
/*  72 */     } else if (card.type == AbstractCard.CardType.POWER) {
/*     */       
/*  74 */       og.zoom = 0.976F;
/*  75 */       og.translate(-3.0F, 0.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  80 */     og.update();
/*  81 */     sb.setProjectionMatrix(og.combined);
/*     */     
/*  83 */     ImageHelper.beginBuffer(fb);
/*  84 */     sb.setBlendFunction(770, 771);
/*  85 */     fb.begin();
/*  86 */     sb.begin();
/*     */     
/*  88 */     sb.setColor(Color.WHITE.cpy());
/*  89 */     sb.draw((TextureRegion)t, -width / 2.0F, -height / 2.0F, -width / 2.0F, -height / 2.0F, width, height, 1.0F, 1.0F, 0.0F);
/*  90 */     sb.draw((TextureRegion)t, -t.packedWidth / 2.0F * multi, -t.packedHeight / 2.0F * multi, -t.packedWidth / 2.0F * multi, -t.packedHeight / 2.0F * multi, (t.packedWidth * multi), (t.packedHeight * multi), 1.0F, 1.0F, 0.0F);
/*  91 */     sb.setBlendFunction(774, 0);
/*  92 */     sb.setProjectionMatrix((new OrthographicCamera(width, height)).combined);
/*     */     
/*  94 */     Texture mask = skillMask;
/*  95 */     if (card.type == AbstractCard.CardType.ATTACK) {
/*  96 */       mask = attackMask;
/*  97 */     } else if (card.type == AbstractCard.CardType.POWER) {
/*  98 */       mask = powerMask;
/*     */     } 
/* 100 */     sb.draw(mask, -width / 2.0F, -height / 2.0F, -width / 2.0F, -height / 2.0F, width, height, 1.0F, 1.0F, 0.0F, 0, 0, mask.getWidth(), mask.getHeight(), false, true);
/*     */     
/* 102 */     sb.end();
/* 103 */     fb.end();
/* 104 */     t.flip(false, true);
/* 105 */     return (Texture)fb.getColorBufferTexture();
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = SingleCardViewPopup.class, method = "loadPortraitImg")
/*     */   public static class FixSCVHopefully {
/*     */     @SpirePostfixPatch
/*     */     public static void dontExplode(SingleCardViewPopup __instance, @ByRef Texture[] ___portraitImg, AbstractCard ___card) {
/* 112 */       Pair<String, AbstractCard.CardType> key = new Pair(___card.cardID, ___card.type);
/* 113 */       if (PortraitHelper.hashedTextures.containsKey(key))
/* 114 */         ___portraitImg[0] = PortraitHelper.makeMaskedTexture(___card, 2); 
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = CustomCard.class, method = "getPortraitImage", paramtypez = {})
/*     */   public static class FixCustomCardHopefully
/*     */   {
/*     */     @SpirePostfixPatch
/*     */     public static void plz(CustomCard __instance, @ByRef Texture[] __result) {
/* 123 */       Pair<String, AbstractCard.CardType> key = new Pair(__instance.cardID, __instance.type);
/* 124 */       if (PortraitHelper.hashedTextures.containsKey(key))
/* 125 */         __result[0] = PortraitHelper.makeMaskedTexture((AbstractCard)__instance, 2); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugment\\util\PortraitHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */