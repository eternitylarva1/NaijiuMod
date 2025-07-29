/*     */ package Naijiumod.patchs;
/*     */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*     */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
/*     */ import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import com.google.gson.*;
/*     */
/*     */
/*     */
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import javassist.CtBehavior;
/*     */ 
/*     */ public class InfiniteUpgradesPatches {
/*     */   @SpirePatch(clz = AbstractCard.class, method = "<class>")
/*     */   public static class InfUpgradeField {
/*  25 */     public static SpireField<Boolean> inf = new SpireField(() -> Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public static void infCheck(AbstractCard card) {
/*  29 */     if (((Boolean)InfUpgradeField.inf.get(card)).booleanValue())
/*  30 */       card.upgraded = false; 
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
/*     */   public static class MakeStatEquivalentCopy
/*     */   {
/*     */     @SpireInsertPatch(locator = Locator.class, localvars = {"card"})
/*     */     public static void copyField(AbstractCard __instance, AbstractCard card) {
/*  38 */       if (((Boolean) InfUpgradeField.inf.get(__instance)).booleanValue())
/*  39 */         InfUpgradeField.inf.set(card, Boolean.valueOf(true));
/*     */     }
/*     */     
/*     */     public static class Locator
/*     */       extends SpireInsertLocator
/*     */     {
/*     */       public int[] Locate(CtBehavior ctBehavior) throws Exception {
/*  46 */         Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "timesUpgraded");
/*  47 */         return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = AbstractCard.class, method = "canUpgrade")
/*     */   public static class BypassUpgradeLimit {
/*     */     @SpirePrefixPatch
/*     */     public static SpireReturn<?> plz(AbstractCard __instance) {
/*  56 */       if (((Boolean) InfUpgradeField.inf.get(__instance)).booleanValue()) {
/*  57 */         return SpireReturn.Return(Boolean.valueOf(true));
/*     */       }
/*  59 */       return SpireReturn.Continue();
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
/*     */   public static class FixSaveAndLoadIssues
/*     */   {
/*     */     @SpireInsertPatch(locator = Locator.class, localvars = {"retVal"})
/*     */     public static void forceUpgrade(AbstractCard retVal) {
/*  68 */       GsonBuilder builder = new GsonBuilder();
/*  69 */       if (CardModifierPatches.modifierAdapter == null) {
/*  70 */         CardModifierPatches.initializeAdapterFactory();
/*     */       }
/*     */       
/*  73 */       if (ModSaves.cardModifierSaves != null && CardCrawlGame.saveFile != null) {
/*  74 */         builder.registerTypeAdapterFactory((TypeAdapterFactory)CardModifierPatches.modifierAdapter);
/*  75 */         Gson gson = builder.create();
/*  76 */         ModSaves.ArrayListOfJsonElement cardModifierSaves = (ModSaves.ArrayListOfJsonElement)ModSaves.cardModifierSaves.get(CardCrawlGame.saveFile);
/*  77 */         int i = AbstractDungeon.player.masterDeck.size();
/*  78 */         if (cardModifierSaves != null) {
/*  79 */           JsonElement loaded = (i >= cardModifierSaves.size()) ? null : (JsonElement)cardModifierSaves.get(i);
/*  80 */           if (loaded != null && loaded.isJsonArray()) {
/*  81 */             JsonArray array = loaded.getAsJsonArray();
/*  82 */             for (JsonElement element : array) {
/*  83 */               AbstractCardModifier cardModifier = null;
/*     */               try {
/*  85 */                 cardModifier = (AbstractCardModifier)gson.fromJson(element, (new TypeToken<AbstractCardModifier>() {  }).getType());
/*  86 */               } catch (Exception exception) {}
/*  87 */
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private static class Locator
/*     */       extends SpireInsertLocator
/*     */     {
/*     */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/*  99 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
/* 100 */         return new int[] { LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher)[0] - 1 };
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\InfiniteUpgradesPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */