/*    */ package Naijiumod.patchs;
/*    */ import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import basemod.helpers.CardModifierManager;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.CardGroup;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.events.city.TheLibrary;
/*    */ import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
/*    */ import com.megacrit.cardcrawl.neow.NeowEvent;
/*    */ import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
/*    */ import java.util.ArrayList;
/*    */ import javassist.CtBehavior;
/*    */ 
/*    */ public class CopyTheDamnModPatches {
/* 20 */   public static final ArrayList<AbstractCardModifier> modsToCopy = new ArrayList<>();
/*    */   public static boolean needsCopy;
/*    */   
/*    */   @SpirePatch2(clz = GremlinMatchGame.class, method = "updateMatchGameLogic")
/*    */   public static class ModifyMatchGameCards {
/*    */     @SpireInsertPatch(locator = Locator.class)
/*    */     public static void patch(AbstractCard ___chosenCard) {
/* 27 */       for (AbstractCardModifier m : CardModifierManager.modifiers(___chosenCard)) {
/* 28 */         if (m instanceof AbstractAugment) {
/* 29 */           CopyTheDamnModPatches.modsToCopy.add(m);
/*    */         }
/*    */       } 
/* 32 */       CopyTheDamnModPatches.needsCopy = true;
/*    */     }
/*    */     
/*    */     private static class Locator extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 37 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
/* 38 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = ShowCardAndObtainEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class, boolean.class})
/*    */   public static class CopyTheMod {
/*    */     public static void Postfix(AbstractCard card) {
/* 46 */       if (CopyTheDamnModPatches.needsCopy) {
/* 47 */         for (AbstractCardModifier m : CopyTheDamnModPatches.modsToCopy) {
/* 48 */           CardModifierManager.addModifier(card, m.makeCopy());
/*    */         }
/* 50 */         RolledModFieldPatches.RolledModField.rolled.set(card, Boolean.valueOf(true));
/* 51 */         CopyTheDamnModPatches.needsCopy = false;
/*    */       } 
/* 53 */       CopyTheDamnModPatches.modsToCopy.clear();
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = TheLibrary.class, method = "update")
/*    */   public static class FixLibraryCopy {
/*    */     @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
/*    */     public static void patch(AbstractCard c) {
/* 61 */       for (AbstractCardModifier m : CardModifierManager.modifiers(AbstractDungeon.gridSelectScreen.selectedCards.get(0))) {
/* 62 */         if (m instanceof AbstractAugment) {
/* 63 */           CardModifierManager.addModifier(c, m.makeCopy());
/*    */         }
/*    */       } 
/* 66 */       RolledModFieldPatches.RolledModField.rolled.set(c, RolledModFieldPatches.RolledModField.rolled.get(AbstractDungeon.gridSelectScreen.selectedCards.get(0)));
/*    */     }
/*    */     
/*    */     private static class Locator extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 71 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
/* 72 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = NeowEvent.class, method = "update")
/*    */   public static class StopTossingMods {
/*    */     @SpireInsertPatch(locator = Locator.class, localvars = {"group"})
/*    */     public static void plz(CardGroup group) {
/* 81 */       for (int i = 0; i < AbstractDungeon.gridSelectScreen.selectedCards.size(); i++) {
/* 82 */         for (AbstractCardModifier mod : CardModifierManager.modifiers(AbstractDungeon.gridSelectScreen.selectedCards.get(i))) {
/* 83 */           if (mod instanceof AbstractAugment) {
/* 84 */             CardModifierManager.addModifier(group.group.get(group.group.size() - 1 - i), mod.makeCopy());
/*    */           }
/*    */         } 
/*    */         
/* 88 */         RolledModFieldPatches.RolledModField.rolled.set(group.group.get(group.group.size() - 1 - i), Boolean.valueOf(true));
/*    */       } 
/*    */     }
/*    */     
/*    */     public static class Locator
/*    */       extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctBehavior) throws Exception {
/* 95 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "openConfirmationGrid");
/* 96 */         return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\CopyTheDamnModPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */