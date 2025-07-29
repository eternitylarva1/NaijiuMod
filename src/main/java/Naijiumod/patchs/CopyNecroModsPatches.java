/*    */ package Naijiumod.patchs;
/*    */ 
/*    */ import basemod.helpers.CardModifierManager;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
/*    */ import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
/*    */ import com.megacrit.cardcrawl.vfx.NecronomicurseEffect;
/*    */ 
/*    */ public class CopyNecroModsPatches {
/* 14 */   static AbstractCard backupCard = null;
/*    */   
/*    */   @SpirePatches2({@SpirePatch2(clz = Necronomicurse.class, method = "onRemoveFromMasterDeck"), @SpirePatch2(clz = Necronomicurse.class, method = "triggerOnExhaust")})
/*    */   public static class CopyMods
/*    */   {
/*    */     @SpirePrefixPatch
/*    */     public static void plz(AbstractCard __instance) {
/* 21 */       CopyNecroModsPatches.backupCard = __instance;
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = MakeTempCardInHandAction.class, method = "<ctor>", paramtypez = {AbstractCard.class, int.class})
/*    */   public static class ApplyMods {
/*    */     @SpirePostfixPatch
/*    */     public static void plz(AbstractCard card) {
/* 29 */       if (CopyNecroModsPatches.backupCard != null) {
/* 30 */         CardModifierManager.copyModifiers(CopyNecroModsPatches.backupCard, card, true, false, false);
/* 31 */         CopyNecroModsPatches.backupCard = null;
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = NecronomicurseEffect.class, method = "<ctor>")
/*    */   public static class ApplyMods2 {
/*    */     @SpirePostfixPatch
/*    */     public static void plz(AbstractCard card) {
/* 40 */       if (CopyNecroModsPatches.backupCard != null) {
/* 41 */         CardModifierManager.copyModifiers(CopyNecroModsPatches.backupCard, card, true, false, false);
/* 42 */         CopyNecroModsPatches.backupCard = null;
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\CopyNecroModsPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */