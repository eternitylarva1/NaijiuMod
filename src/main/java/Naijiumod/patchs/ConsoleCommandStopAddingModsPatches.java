/*    */ package Naijiumod.patchs;
/*    */ import basemod.devcommands.deck.DeckAdd;
/*    */ import basemod.devcommands.hand.HandAdd;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
/*    */ import com.megacrit.cardcrawl.actions.GameActionManager;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import javassist.CtBehavior;

import java.util.ArrayList;

/*    */
/*    */ public class ConsoleCommandStopAddingModsPatches {
/*    */   @SpirePatch2(clz = HandAdd.class, method = "execute")
/*    */   public static class stopRollingModsOnSpawnedCardsPlz {
/*    */     @SpireInsertPatch(locator = Locator.class, localvars = {"copy"})
/*    */     public static void patch(AbstractCard copy) {
/* 17 */       RolledModFieldPatches.RolledModField.rolled.set(copy, Boolean.valueOf(true));
/*    */     }
/*    */     
/*    */     private static class Locator extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 22 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
/* 23 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = DeckAdd.class, method = "execute")
/*    */   public static class stopRollingModsOnSpawnedCardsPlz2 {
/*    */     @SpireInsertPatch(locator = Locator.class, localvars = {"copy"})
/*    */     public static void patch(AbstractCard copy) {
/* 32 */       RolledModFieldPatches.RolledModField.rolled.set(copy, Boolean.valueOf(true));
/*    */     }
/*    */     
/*    */     private static class Locator extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 37 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
/* 38 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\ConsoleCommandStopAddingModsPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */