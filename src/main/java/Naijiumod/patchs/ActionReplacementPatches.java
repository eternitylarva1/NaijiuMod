/*    */ package Naijiumod.patchs;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.*;
/*    */
/*    */
/*    */
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.GameActionManager;
/*    */ import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import javassist.CtBehavior;
/*    */ 
/*    */ public class ActionReplacementPatches {
/*    */   public static AbstractCard cardInUse;
/*    */   public static AbstractMonster target;
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "<class>")
/*    */   public static class InvertedFields {
/* 21 */     public static SpireField<Boolean> isInverted = new SpireField(() -> Boolean.valueOf(false));
/* 22 */     public static SpireField<Boolean> toBlock = new SpireField(() -> Boolean.valueOf(false));
/*    */   }
/*    */   
/*    */   @SpirePatches2({@SpirePatch2(clz = GameActionManager.class, method = "addToTop"), @SpirePatch2(clz = GameActionManager.class, method = "addToBottom")})
/*    */   public static class ReplaceActions
/*    */   {
/*    */     @SpirePrefixPatch
/*    */     public static void plz(GameActionManager __instance, @ByRef AbstractGameAction[] action) {
/* 30 */       if ((action[0].getClass().equals(DamageAction.class) || action[0].getClass().equals(GainBlockAction.class)) &&
/* 31 */         ActionReplacementPatches.cardInUse != null && ((Boolean) InvertedFields.isInverted.get(ActionReplacementPatches.cardInUse)).booleanValue()) {
/* 32 */         if (((Boolean) InvertedFields.toBlock.get(ActionReplacementPatches.cardInUse)).booleanValue()) {
/* 33 */           action[0] = (AbstractGameAction)new GainBlockAction((AbstractCreature)AbstractDungeon.player, ActionReplacementPatches.cardInUse.block);
/*    */         } else {
/* 35 */           if (ActionReplacementPatches.target == null) {
/* 36 */             ActionReplacementPatches.target = AbstractDungeon.getRandomMonster();
/*    */           }
/* 38 */           action[0] = (AbstractGameAction)new DamageAction((AbstractCreature)ActionReplacementPatches.target, new DamageInfo((AbstractCreature)AbstractDungeon.player, ActionReplacementPatches.cardInUse.damage, ActionReplacementPatches.cardInUse.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE);
/*    */         } 
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
/*    */   public static class GrabCardInUse
/*    */   {
/*    */     @SpireInsertPatch(locator = Locator.class)
/*    */     public static void RememberCardPreUseCall(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
/* 49 */       ActionReplacementPatches.cardInUse = c;
/* 50 */       ActionReplacementPatches.target = monster;
/*    */     }
/*    */     
/*    */     @SpireInsertPatch(locator = Locator2.class)
/*    */     public static void ForgetCardPostUseCall(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
/* 55 */       ActionReplacementPatches.cardInUse = null;
/* 56 */       ActionReplacementPatches.target = null;
/*    */     }
/*    */     
/*    */     private static class Locator2 extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 61 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
/* 62 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */     
/*    */     private static class Locator extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 68 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
/* 69 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\ActionReplacementPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */