/*     */ package Naijiumod.patchs;
/*     */ 
/*     */ import basemod.ReflectionHacks;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpireField;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.Settings;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*     */ import com.megacrit.cardcrawl.helpers.Hitbox;
/*     */ import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
/*     */ import com.megacrit.cardcrawl.ui.FtueTip;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class MultiPreviewFieldPatches
/*     */ {
/*     */   @SpirePatch(clz = AbstractCard.class, method = "<class>")
/*     */   public static class ExtraPreviews {
/*  19 */     public static SpireField<ArrayList<AbstractCard>> previews = new SpireField(ArrayList::new);
/*     */   }
/*     */   
/*     */   public static void addPreview(AbstractCard card, AbstractCard preview) {
/*  23 */     if (card != null && preview != null) {
/*  24 */       ((ArrayList<AbstractCard>)ExtraPreviews.previews.get(card)).add(preview);
/*  25 */       if (preview.cardsToPreview != null && !((ArrayList)ExtraPreviews.previews.get(card)).contains(preview.cardsToPreview)) {
/*  26 */         addPreview(card, preview.cardsToPreview);
/*     */       }
/*  28 */       for (AbstractCard c : ExtraPreviews.previews.get(preview)) {
/*  29 */         if (!((ArrayList)ExtraPreviews.previews.get(card)).contains(c)) {
/*  30 */           addPreview(card, c);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*  36 */   private static Float cardTipPad = null;
/*     */   
/*     */   @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTips")
/*     */   public static class renderSwappablesInSingleViewPatch
/*     */   {
/*     */     public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
/*  42 */       AbstractCard card = (AbstractCard)ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
/*  43 */       if (!card.isLocked && card.isSeen && !((ArrayList) ExtraPreviews.previews.get(card)).isEmpty()) {
/*  44 */         float renderX = 1920.0F * Settings.scale - 1435.0F * Settings.scale;
/*  45 */         float renderY = 795.0F * Settings.scale;
/*  46 */         if (MultiPreviewFieldPatches.cardTipPad == null) {
/*  47 */           MultiPreviewFieldPatches.cardTipPad = (Float)ReflectionHacks.getPrivateStatic(AbstractCard.class, "CARD_TIP_PAD");
/*     */         }
/*  49 */         float horizontal = AbstractCard.IMG_WIDTH * 0.8F + MultiPreviewFieldPatches.cardTipPad.floatValue();
/*  50 */         Hitbox prevHb = (Hitbox)ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "prevHb");
/*  51 */         float vertical = AbstractCard.IMG_HEIGHT * 0.8F + MultiPreviewFieldPatches.cardTipPad.floatValue();
/*  52 */         if (prevHb != null) {
/*  53 */           vertical += prevHb.height;
/*     */         }
/*  55 */         boolean verticalOffset = false;
/*  56 */         if (card.cardsToPreview != null) {
/*  57 */           renderY -= vertical;
/*  58 */           verticalOffset = true;
/*     */         } 
/*  60 */         for (AbstractCard next : ExtraPreviews.previews.get(card)) {
/*  61 */           next.current_x = renderX;
/*  62 */           next.current_y = renderY;
/*  63 */           next.drawScale = 0.8F;
/*  64 */           next.render(sb);
/*  65 */           if (verticalOffset) {
/*  66 */             renderY += vertical;
/*  67 */             renderX -= horizontal;
/*  68 */             verticalOffset = false; continue;
/*     */           } 
/*  70 */           renderY -= vertical;
/*  71 */           verticalOffset = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SpirePatch(clz = AbstractCard.class, method = "renderCardTip")
/*     */   public static class renderSwappablesPreviewPatch
/*     */   {
/*     */     public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
/*  82 */       if (((!__instance.isLocked && __instance.isSeen && !Settings.hideCards && ((Boolean)ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderTip")).booleanValue()) || (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.FTUE && ReflectionHacks.getPrivate(AbstractDungeon.ftue, FtueTip.class, "c") == __instance)) && !((ArrayList) ExtraPreviews.previews.get(__instance)).isEmpty()) {
/*  83 */         if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
/*     */           return;
/*     */         }
/*  86 */         boolean rightSide = (__instance.current_x < Settings.WIDTH * 0.25F);
/*  87 */         if (MultiPreviewFieldPatches.cardTipPad == null) {
/*  88 */           MultiPreviewFieldPatches.cardTipPad = (Float)ReflectionHacks.getPrivateStatic(AbstractCard.class, "CARD_TIP_PAD");
/*     */         }
/*  90 */         float renderX = (AbstractCard.IMG_WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F * 0.8F + MultiPreviewFieldPatches.cardTipPad.floatValue()) * __instance.drawScale;
/*  91 */         float horizontal = (AbstractCard.IMG_WIDTH * 0.8F + MultiPreviewFieldPatches.cardTipPad.floatValue()) * __instance.drawScale;
/*  92 */         if (!rightSide) {
/*  93 */           horizontal *= -1.0F;
/*     */         }
/*  95 */         float vertical = (AbstractCard.IMG_HEIGHT * 0.8F + MultiPreviewFieldPatches.cardTipPad.floatValue()) * __instance.drawScale;
/*  96 */         boolean verticalOffset = false;
/*  97 */         if (rightSide) {
/*  98 */           renderX = __instance.current_x + renderX;
/*     */         } else {
/* 100 */           renderX = __instance.current_x - renderX;
/*     */         } 
/* 102 */         float renderY = __instance.current_y + (AbstractCard.IMG_HEIGHT / 2.0F - AbstractCard.IMG_HEIGHT / 2.0F * 0.8F) * __instance.drawScale;
/* 103 */         if (__instance.cardsToPreview != null)
/*     */         {
/*     */           
/* 106 */           renderX += horizontal;
/*     */         }
/* 108 */         for (AbstractCard next : ExtraPreviews.previews.get(__instance)) {
/* 109 */           next.current_x = renderX;
/* 110 */           next.current_y = renderY;
/* 111 */           __instance.drawScale *= 0.8F;
/* 112 */           next.render(sb);
/* 113 */           renderX += horizontal;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\MultiPreviewFieldPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */