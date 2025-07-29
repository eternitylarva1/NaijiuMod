/*    */ package Naijiumod.patchs;
/*    */
/*    */ import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.JankMod;
import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import basemod.helpers.CardModifierManager;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.*;
/*    */
/*    */
/*    */
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;

/*    */
/*    */ public class OnUpgradePatches {
/*    */   public static void onUpgrade(AbstractCard card) {
/* 13 */     for (AbstractCardModifier m : CardModifierManager.modifiers(card)) {
/* 14 */       if (m instanceof AbstractAugment)
/* 15 */         ((AbstractAugment)m).onUpgradeCheck(card); 
/*    */     } 
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = AbstractCard.class, method = "upgradeDamage")
/*    */   public static class DamageUpgrade
/*    */   {
/*    */     @SpirePrefixPatch
/*    */     public static SpireReturn<?> boost(AbstractCard __instance, @ByRef int[] amount) {
/* 24 */       if (((Boolean)InfiniteUpgradesPatches.InfUpgradeField.inf.get(__instance)).booleanValue()) {
/* 25 */         amount[0] = amount[0] + Math.max(0, __instance.timesUpgraded - 1);
/*    */       }
/* 27 */       if (CardModifierManager.hasModifier(__instance, JankMod.ID)) {
/* 28 */         JankMod jm = (JankMod) CardModifierManager.getModifiers(__instance, JankMod.ID).get(0);
/* 29 */         jm.upDamage = amount[0];
/* 30 */         return SpireReturn.Return();
/*    */       }
/* 32 */       return SpireReturn.Continue();
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = AbstractCard.class, method = "upgradeBlock")
/*    */   public static class BlockUpgrade {
/*    */     @SpirePrefixPatch
/*    */     public static SpireReturn<?> boost(AbstractCard __instance, @ByRef int[] amount) {
/* 40 */       if (((Boolean)InfiniteUpgradesPatches.InfUpgradeField.inf.get(__instance)).booleanValue()) {
/* 41 */         amount[0] = amount[0] + Math.max(0, __instance.timesUpgraded - 1);
/*    */       }
/* 43 */       if (CardModifierManager.hasModifier(__instance, JankMod.ID)) {
/* 44 */         JankMod jm = (JankMod) CardModifierManager.getModifiers(__instance, JankMod.ID).get(0);
/* 45 */         jm.upBlock = amount[0];
/* 46 */         return SpireReturn.Return();
/*    */       } 
/* 48 */       return SpireReturn.Continue();
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = AbstractCard.class, method = "upgradeMagicNumber")
/*    */   public static class MagicUpgrade {
/*    */     @SpirePrefixPatch
/*    */     public static SpireReturn<?> boost(AbstractCard __instance, @ByRef int[] amount) {
/* 56 */       if (((Boolean)InfiniteUpgradesPatches.InfUpgradeField.inf.get(__instance)).booleanValue()) {
/* 57 */         amount[0] = amount[0] + Math.max(0, __instance.timesUpgraded - 1);
/*    */       }
/* 59 */       if (CardModifierManager.hasModifier(__instance, JankMod.ID)) {
/* 60 */         JankMod jm = (JankMod) CardModifierManager.getModifiers(__instance, JankMod.ID).get(0);
/* 61 */         jm.upMagic = amount[0];
/* 62 */         return SpireReturn.Return();
/*    */       } 
/* 64 */       return SpireReturn.Continue();
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = AbstractCard.class, method = "upgradeName")
/*    */   public static class FixStackOfPlusSymbols {
/*    */     @SpireInsertPatch(locator = Locator.class)
/*    */     public static void plz(AbstractCard __instance) {
/* 72 */       if (((Boolean)InfiniteUpgradesPatches.InfUpgradeField.inf.get(__instance)).booleanValue())
/* 73 */         __instance.name = __instance.originalName + "+" + __instance.timesUpgraded; 
/*    */     }
/*    */     
/*    */     private static class Locator
/*    */       extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 79 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "initializeTitle");
/* 80 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\OnUpgradePatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */