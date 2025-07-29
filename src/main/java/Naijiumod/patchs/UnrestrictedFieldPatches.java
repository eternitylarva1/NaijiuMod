/*    */ package Naijiumod.patchs;
/*    */ import com.badlogic.gdx.graphics.Color;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
/*    */
/*    */
/*    */
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
/*    */ 
/*    */ public class UnrestrictedFieldPatches {
/* 11 */   public static final Color boosterColor = Color.valueOf("65ada1");
/* 12 */   public static final Color boosterUpgradeColor = Color.valueOf("c26ad4");
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "<class>")
/*    */   public static class UnrestrictedField {
/* 16 */     public static SpireField<Boolean> unrestricted = new SpireField(() -> Boolean.valueOf(false));
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
/*    */   public static class OverrideEnergy {
/*    */     public static SpireReturn<?> Prefix(AbstractCard __instance) {
/* 22 */       if (((Boolean) UnrestrictedField.unrestricted.get(__instance)).booleanValue() &&
/* 23 */         !AbstractDungeon.actionManager.turnHasEnded) {
/* 24 */         return SpireReturn.Return(Boolean.valueOf(true));
/*    */       }
/*    */       
/* 27 */       return SpireReturn.Continue();
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
/*    */   public static class RenderNewColor {
/*    */     @SpireInsertPatch(locator = Locator.class, localvars = {"costColor"})
/*    */     public static void pls(AbstractCard __instance, SpriteBatch sb, @ByRef Color[] costColor) {
/* 35 */       if (((Boolean) UnrestrictedField.unrestricted.get(__instance)).booleanValue())
/* 36 */         costColor[0] = UnrestrictedFieldPatches.boosterColor; 
/*    */     }
/*    */     
/*    */     private static class Locator
/*    */       extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 42 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "getCost");
/* 43 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
/*    */   public static class MakeStatEquivalentCopy {
/*    */     public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
/* 51 */       UnrestrictedField.unrestricted.set(result, UnrestrictedField.unrestricted.get(self));
/* 52 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\UnrestrictedFieldPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */