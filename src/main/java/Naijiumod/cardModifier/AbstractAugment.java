package Naijiumod.cardModifier;

import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.utils.FormatHelper;
import Naijiumod.utils.Wiz;
import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import basemod.patches.whatmod.WhatMod;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.util.UpgradeData;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.CompileDriverAction;
import com.megacrit.cardcrawl.actions.defect.FTLAction;
import com.megacrit.cardcrawl.actions.defect.FissionAction;
import com.megacrit.cardcrawl.actions.unique.CalculatedGambleAction;
import com.megacrit.cardcrawl.actions.unique.DropkickAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.unique.HeelHookAction;
import com.megacrit.cardcrawl.actions.utility.ConditionalDrawAction;
import com.megacrit.cardcrawl.actions.watcher.InnerPeaceAction;
import com.megacrit.cardcrawl.actions.watcher.SanctityAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractAugment extends AbstractCardModifier {

  private static final String[] HELPER_TEXT = (CardCrawlGame.languagePack.getUIString(ModHelper.makePath(AbstractAugment.class.getSimpleName()))).TEXT;

  private static final String EXHAUST_TEXT = " NL " + FormatHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]) + LocalizedStrings.PERIOD;

  private static final String INNATE_TEXT = FormatHelper.capitalize(GameDictionary.INNATE.NAMES[0]) + LocalizedStrings.PERIOD + " NL ";

  private static final String ETHEREAL_TEXT = FormatHelper.capitalize(GameDictionary.ETHEREAL.NAMES[0]) + LocalizedStrings.PERIOD + " NL ";

  private static final String RETAIN_TEXT = FormatHelper.capitalize(GameDictionary.RETAIN.NAMES[0]) + LocalizedStrings.PERIOD + " NL ";

  private static final String UNPLAYABLE_TEXT = FormatHelper.capitalize(GameDictionary.UNPLAYABLE.NAMES[0]) + LocalizedStrings.PERIOD + " NL ";

  public static final float HUGE_BUFF = 1.5F;
  
  public static final float MAJOR_BUFF = 1.3333334F;
  
  public static final float MODERATE_BUFF = 1.25F;
  
  public static final float MINOR_BUFF = 1.2F;
  
  public static final float HUGE_DEBUFF = 0.5F;
  
  public static final float MAJOR_DEBUFF = 0.6666667F;
  
  public static final float MODERATE_DEBUFF = 0.75F;
  
  public static final float MINOR_DEBUFF = 0.8F;
  
  private static AbstractCard baseCheck;
  
  private static final ArrayList<AbstractCard> cardsToCheck = new ArrayList<>();
  
  public enum AugmentRarity {
    COMMON, UNCOMMON, RARE, SPECIAL;
    
    public String toString() {
      return name().charAt(0) + name().substring(1).toLowerCase();
    }
  }
  
  public String getPrefix() {
    return "";
  }
  
  public String getSuffix() {
    return "";
  }
  
  public String getAugmentDescription() {
    return "";
  }
  
  public List<TooltipInfo> additionalTooltips(AbstractCard card) {
    return null;
  }
  
  public String modifyName(String cardName, AbstractCard card) {
    String[] nameParts = removeUpgradeText(cardName);
    return getPrefix() + nameParts[0] + getSuffix() + nameParts[1];
  }
  
  public CardBorderGlowManager.GlowInfo getGlowInfo() {
    return null;
  }
  
  public boolean hasThisMod(AbstractCard card) {
    return CardModifierManager.modifiers(card).stream().anyMatch(m -> m.identifier(card).equals(identifier(card)));
  }
  
  public boolean lastCardPlayedCheck(Predicate<AbstractCard> p) {
    if (AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty())
      return false; 
    return p.test(AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1));
  }
  
  public int getEffectiveUpgrades(AbstractCard card) {
    int upgrades = card.timesUpgraded;
    if (card instanceof MultiUpgradeCard)
      upgrades = ((MultiUpgradeCard)card).upgradesPerformed(); 
    if (upgrades < 0)
      upgrades *= -1; 
    return upgrades;
  }
  
  public boolean betterCanPlay(AbstractCard cardWithThisMod, AbstractCard cardToCheck) {
    return true;
  }
  
  public void onDamaged(AbstractCard c) {}
  
  public void onUpgradeCheck(AbstractCard card) {}
  
  public void onCreatedMidCombat(AbstractCard card) {}
  
  public void updateDynvar(AbstractCard card) {}
  
  public boolean atBattleStartPreDraw(AbstractCard card) {
    return false;
  }
  
  public boolean canApplyTo(AbstractCard card) {
    return true;
  }
  
  public static AbstractCard makeNewInstance(AbstractCard card) {
    try {
      return (AbstractCard)card.getClass().newInstance();
    } catch (IllegalAccessException|InstantiationException ignored) {
      return null;
    } 
  }
  
  private static void setCardChecks(AbstractCard card) {
    cardsToCheck.clear();
    baseCheck = card.makeCopy();
    cardsToCheck.add(baseCheck);
    if (card instanceof BranchingUpgradesCard) {
      AbstractCard normalCheck = card.makeCopy();
      ((BranchingUpgradesCard)normalCheck).setUpgradeType(BranchingUpgradesCard.UpgradeType.NORMAL_UPGRADE);
      normalCheck.upgrade();
      cardsToCheck.add(normalCheck);
      AbstractCard branchCheck = card.makeCopy();
      ((BranchingUpgradesCard)branchCheck).setUpgradeType(BranchingUpgradesCard.UpgradeType.BRANCH_UPGRADE);
      branchCheck.upgrade();
      cardsToCheck.add(branchCheck);
    } else if (card instanceof MultiUpgradeCard) {
      for (int i = 0; i < ((MultiUpgradeCard)baseCheck).getUpgrades().size(); i++) {
        AbstractCard upgradeTest = card.makeCopy();
        ((UpgradeData)((MultiUpgradeCard)upgradeTest).getUpgrades().get(i)).upgrade();
        cardsToCheck.add(upgradeTest);
      } 
    } else {
      AbstractCard upgradeCheck = card.makeCopy();
      upgradeCheck.upgrade();
      cardsToCheck.add(upgradeCheck);
    } 
  }
  
  public static boolean cardCheck(AbstractCard card, Predicate<AbstractCard> p) {
    boolean ret = false;
    try {
      setCardChecks(card);
      ret = p.test(card);
    } catch (Exception e) {

    } 
    return ret;
  }
  
  public static boolean customCheck(Predicate<AbstractCard> pred) {
    return cardsToCheck.stream().allMatch(pred);
  }
  
  public static boolean customCheck(AbstractCard c, Predicate<AbstractCard> pred) {
    return (pred.test(c) && cardsToCheck.stream().allMatch(pred));
  }
  
  public static boolean upgradesAVariable() {
    return (upgradesDamage() || upgradesBlock() || upgradesMagic());
  }
  
  public static boolean upgradesDamage() {
    return cardsToCheck.stream().anyMatch(c -> (c.baseDamage > baseCheck.baseDamage));
  }
  
  public static boolean upgradesBlock() {
    return cardsToCheck.stream().anyMatch(c -> (c.baseBlock > baseCheck.baseBlock));
  }
  
  public static boolean upgradesMagic() {
    return (cardsToCheck.stream().anyMatch(c -> (c.baseMagicNumber > baseCheck.baseMagicNumber)) && usesMagic(baseCheck));
  }
  
  public static boolean doesntDowngradeMagic() {
    return (cardsToCheck.stream().allMatch(c -> (c.baseMagicNumber >= baseCheck.baseMagicNumber)) && usesMagic(baseCheck));
  }
  
  public static boolean reachesDamage(int amount) {
    return (baseCheck.baseDamage >= amount || cardsToCheck.stream().anyMatch(c -> (c.baseDamage >= amount)));
  }
  
  public static boolean reachesBlock(int amount) {
    return (baseCheck.baseBlock >= amount || cardsToCheck.stream().anyMatch(c -> (c.baseBlock >= amount)));
  }
  
  public static boolean reachesMagic(int amount) {
    return ((baseCheck.baseMagicNumber >= amount || cardsToCheck.stream().anyMatch(c -> (c.baseMagicNumber >= amount))) && usesMagic(baseCheck));
  }
  
  public static boolean doesntUpgradeCost() {
    return cardsToCheck.stream().allMatch(c -> (c.cost == baseCheck.cost));
  }
  
  public static boolean notExhaust(AbstractCard card) {
    return (doesntExhaust(card) && doesntUpgradeExhaust());
  }
  
  public static boolean doesntUpgradeExhaust() {
    return cardsToCheck.stream().allMatch(c -> (c.exhaust == baseCheck.exhaust && c.purgeOnUse == baseCheck.purgeOnUse && Objects.equals(ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c), ExhaustiveField.ExhaustiveFields.baseExhaustive.get(baseCheck)) && Objects.equals(ExhaustiveField.ExhaustiveFields.exhaustive.get(c), ExhaustiveField.ExhaustiveFields.exhaustive.get(baseCheck))));
  }
  
  public static boolean notEthereal(AbstractCard card) {
    return (!card.isEthereal && doesntUpgradeEthereal());
  }
  
  public static boolean doesntUpgradeEthereal() {
    return cardsToCheck.stream().anyMatch(c -> (c.isEthereal == baseCheck.isEthereal));
  }
  
  public static boolean notInnate(AbstractCard card) {
    return (!card.isInnate && doesntUpgradeInnate());
  }
  
  public static boolean doesntUpgradeInnate() {
    return cardsToCheck.stream().allMatch(c -> (c.isInnate == baseCheck.isInnate));
  }
  
  public static boolean notRetain(AbstractCard card) {
    return (!card.selfRetain && doesntUpgradeRetain());
  }
  
  public static boolean doesntUpgradeRetain() {
    return cardsToCheck.stream().allMatch(c -> (c.selfRetain == baseCheck.selfRetain));
  }
  
  public static boolean notReshuffle(AbstractCard card) {
    return (!card.shuffleBackIntoDrawPile && doesntUpgradeReshuffle());
  }
  
  public static boolean doesntUpgradeReshuffle() {
    return cardsToCheck.stream().allMatch(c -> (c.shuffleBackIntoDrawPile == baseCheck.shuffleBackIntoDrawPile));
  }
  
  public static boolean doesntUpgradeTargeting() {
    return cardsToCheck.stream().allMatch(c -> (c.target == baseCheck.target));
  }
  
  public static boolean usesEnemyTargeting() {
    return cardsToCheck.stream().allMatch(AbstractAugment::targetsEnemy);
  }
  
  public static boolean canOverrideTargeting(AbstractCard card, AbstractCard.CardTarget desiredType) {
    if (desiredType == card.target || card.target == AbstractCard.CardTarget.NONE)
      return true; 
    switch (desiredType) {
      case SELF_AND_ENEMY:
        return (card.target == AbstractCard.CardTarget.ENEMY || card.target == AbstractCard.CardTarget.SELF);
      case ALL:
        return (card.target == AbstractCard.CardTarget.ALL_ENEMY || card.target == AbstractCard.CardTarget.SELF);
    } 
    return false;
  }
  
  public static boolean targetsEnemy(AbstractCard card) {
    return (card.target == AbstractCard.CardTarget.ENEMY || card.target == AbstractCard.CardTarget.SELF_AND_ENEMY);
  }
  
  public static boolean usesVanillaTargeting(AbstractCard card) {
    return (card.target == AbstractCard.CardTarget.ENEMY || card.target == AbstractCard.CardTarget.ALL_ENEMY || card.target == AbstractCard.CardTarget.SELF || card.target == AbstractCard.CardTarget.NONE || card.target == AbstractCard.CardTarget.SELF_AND_ENEMY || card.target == AbstractCard.CardTarget.ALL);
  }
  
  public static boolean usesMagic(AbstractCard card) {
    final boolean[] usesMagicBool = { false };
    if (card.baseMagicNumber > 0 && StringUtils.containsIgnoreCase(card.rawDescription, "!M!") && !(card instanceof com.megacrit.cardcrawl.cards.colorless.PanicButton) && !(card instanceof com.megacrit.cardcrawl.cards.purple.Halt)) {
      ClassPool pool = Loader.getClassPool();
      try {
        CtClass ctClass = pool.get(card.getClass().getName());
        ctClass.defrost();
        CtMethod[] methods = ctClass.getDeclaredMethods();
        for (CtMethod method : methods) {
          try {
            method.instrument(new ExprEditor() {
                  public void edit(FieldAccess f) {
                    if (f.getFieldName().equals("magicNumber") && f.isReader())
                      usesMagicBool[0] = true; 
                  }
                });
          } catch (Exception exception) {}
        } 
      } catch (NotFoundException ignored) {
        return false;
      } 
    } 
    return usesMagicBool[0];
  }
  
  public static boolean hasACurse() {
    if (!CardCrawlGame.isInARun())
      return true; 
    return AbstractDungeon.player.masterDeck.group.stream().anyMatch(c -> (c.type == AbstractCard.CardType.CURSE));
  }
  
  public static boolean allowOrbMods() {
    if (!CardCrawlGame.isInARun())
      return true;
    return false;
 }
  
  public static boolean characterCheck(Predicate<AbstractPlayer> p) {
    if (!CardCrawlGame.isInARun())
      return true; 
    return p.test(Wiz.adp());
  }
  
  public static boolean isNormalCard(AbstractCard card) {
    return (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS);
  }
  
  public static boolean doesntExhaust(AbstractCard card) {
    return (!card.exhaust && !card.purgeOnUse && ((Integer)ExhaustiveField.ExhaustiveFields.baseExhaustive.get(card)).intValue() == -1 && ((Integer)ExhaustiveField.ExhaustiveFields.exhaustive.get(card)).intValue() == -1);
  }
  
  public static boolean doesntOverride(AbstractCard card, String method, Class<?>... paramtypez) {
    return doesntOverride(card, AbstractCard.class, method, paramtypez);
  }
  
  public static boolean doesntOverride(Object o, Class<?> clazz, String method, Class<?>... paramtypez) {
    try {
      return o.getClass().getMethod(method, paramtypez).getDeclaringClass().equals(clazz);
    } catch (NoSuchMethodException noSuchMethodException) {
      return false;
    } 
  }
  
  public static boolean noInterfaces(AbstractCard card) {
    try {
      ClassPool pool = Loader.getClassPool();
      CtClass ctClass = pool.get(card.getClass().getName());
      return ((ctClass.getInterfaces()).length == 0);
    } catch (NotFoundException notFoundException) {
      return false;
    } 
  }
  
  public static boolean noShenanigans(AbstractCard card) {
    return (!((Boolean) InterruptUseCardFieldPatches.InterceptUseField.interceptUse.get(card)).booleanValue() && noInterfaces(card) &&
      doesntOverride(card, "canUse", new Class[] { AbstractPlayer.class, AbstractMonster.class }) && doesntOverride(card, "tookDamage", new Class[0]) && 
      doesntOverride(card, "didDiscard", new Class[0]) && 
      doesntOverride(card, "switchedStance", new Class[0]) && 
      doesntOverride(card, "triggerWhenDrawn", new Class[0]) && 
      doesntOverride(card, "triggerWhenCopied", new Class[0]) && 
      doesntOverride(card, "triggerOnEndOfTurnForPlayingCard", new Class[0]) && 
      doesntOverride(card, "triggerOnOtherCardPlayed", new Class[] { AbstractCard.class }) && doesntOverride(card, "triggerOnGainEnergy", new Class[] { int.class, boolean.class }) && doesntOverride(card, "switchedStance", new Class[0]) && 
      doesntOverride(card, "triggerOnManualDiscard", new Class[0]) && 
      doesntOverride(card, "triggerOnCardPlayed", new Class[] { AbstractCard.class }) && doesntOverride(card, "triggerOnScry", new Class[0]) && 
      doesntOverride(card, "triggerExhaustedCardsOnStanceChange", new Class[] { AbstractStance.class }) && doesntOverride(card, "triggerAtStartOfTurn", new Class[0]) && 
      doesntOverride(card, "onPlayCard", new Class[] { AbstractCard.class, AbstractMonster.class }) && doesntOverride(card, "atTurnStart", new Class[0]) && 
      doesntOverride(card, "atTurnStartPreDraw", new Class[0]) && 
      doesntOverride(card, "onChoseThisOption", new Class[0]) && 
      doesntOverride(card, "onRetained", new Class[0]) && 
      doesntOverride(card, "triggerOnExhaust", new Class[0]));
  }
  
  public static boolean usesAction(AbstractCard card, Class<? extends AbstractGameAction> clazz) {
    return usesClass(card, clazz);
  }
  
  public static boolean usesClass(AbstractCard card, final Class<?> clazz) {
    final boolean[] usesAction = { false };
    ClassPool pool = Loader.getClassPool();
    try {
      CtClass ctClass = pool.get(card.getClass().getName());
      ctClass.defrost();
      CtMethod ctUse = ctClass.getDeclaredMethod("use");
      ctUse.instrument(new ExprEditor() {
            public void edit(NewExpr e) {
              if (e.getClassName().equals(clazz.getName()))
                usesAction[0] = true; 
            }
            
            public void edit(MethodCall m) {
              try {
                CtMethod check = m.getMethod();
                check.instrument(new ExprEditor() {
                      public void edit(NewExpr e) {
                        if (e.getClassName().equals(clazz.getName()))
                          usesAction[0] = true; 
                      }
                    });
              } catch (Exception exception) {}
            }
          });
    } catch (Exception exception) {}
    return usesAction[0];
  }
  
  public static boolean noCardModDescriptionChanges(AbstractCard card) {
    for (AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
      if (!doesntOverride(mod, AbstractCardModifier.class, "modifyDescription", new Class[] { String.class, AbstractCard.class }))
        return false; 
    } 
    return true;
  }
  
  public static Class<?>[] drawClasses = new Class[] { 
      DrawCardAction.class, ConditionalDrawAction.class, DropkickAction.class, HeelHookAction.class, CalculatedGambleAction.class, ExpertiseAction.class, CompileDriverAction.class, FTLAction.class, FissionAction.class, InnerPeaceAction.class, 
      SanctityAction.class };
  
  public static boolean drawsCards(AbstractCard card) {
    final boolean[] foundDrawCard = { false };
    try {
      CtMethod useMethod;
      ClassPool pool = Loader.getClassPool();
      CtClass ctClass = pool.get(card.getClass().getName());
      ctClass.defrost();
      try {
        useMethod = ctClass.getDeclaredMethod("use");
      } catch (NotFoundException ignore) {
        return false;
      } 
      useMethod.instrument(new ExprEditor() {
            public void edit(NewExpr n) {
              try {
                CtConstructor constructor = n.getConstructor();
                CtClass activeClass = constructor.getDeclaringClass();
                if (activeClass != null) {
                  CtClass[] plz = { activeClass };
                  while (activeClass != null && Arrays.<Class<?>>stream(AbstractAugment.drawClasses).noneMatch(clz -> clz.getName().equals(plz[0].getName()))) {
                    activeClass = activeClass.getSuperclass();
                    plz[0] = activeClass;
                  } 
                  if (activeClass != null && Arrays.<Class<?>>stream(AbstractAugment.drawClasses).anyMatch(clz -> clz.getName().equals(plz[0].getName())))
                    foundDrawCard[0] = true; 
                } 
              } catch (Exception exception) {}
            }
          });
    } catch (Exception exception) {}
    return foundDrawCard[0];
  }
  
  public static String[] removeUpgradeText(String name) {
    String[] ret = { name, "" };
    if (name.endsWith("+") || name.endsWith("*")) {
      ret[1] = name.substring(name.length() - 1);
      ret[0] = name.substring(0, name.length() - 1);
    } else {
      int index = name.lastIndexOf("+");
      if (index == -1)
        index = name.lastIndexOf("*"); 
      if (index != -1 && name.substring(index + 1).chars().allMatch(Character::isDigit)) {
        ret[1] = name.substring(index);
        ret[0] = name.substring(0, index);
      } 
    } 
    return ret;
  }
  
  public static String insertBeforeText(String rawDescription, String text) {
    StringBuilder removed = new StringBuilder();
    ArrayList<String> matches = makeMatchers(new String[] { INNATE_TEXT, ETHEREAL_TEXT, RETAIN_TEXT, UNPLAYABLE_TEXT });
    while (matches.stream().anyMatch(rawDescription::startsWith)) {
      for (String match : matches) {
        if (rawDescription.startsWith(match)) {
          rawDescription = rawDescription.substring(match.length());
          removed.append(match);
        } 
      } 
    } 
    return removed + text + rawDescription;
  }
  
  public static String insertAfterText(String rawDescription, String text) {
    StringBuilder removed = new StringBuilder();
    for (String match : makeMatchers(new String[] { EXHAUST_TEXT })) {
      if (rawDescription.endsWith(match)) {
        rawDescription = rawDescription.substring(0, rawDescription.length() - match.length());
        removed.append(match);
      } 
    } 
    return rawDescription + text + removed;
  }
  
  public static ArrayList<String> makeMatchers(String... inputs) {
    ArrayList<String> ret = new ArrayList<>();
    Collections.addAll(ret, inputs);
    for (String s : inputs) {
      ret.add((" [diffRmvS] " + s + " [diffRmvE] ").replace("  ", " "));
      ret.add((" [diffAddS] " + s + " [diffAddE] ").replace("  ", " "));
    } 
    return ret;
  }
  
  public abstract AugmentRarity getModRarity();
  
  protected abstract boolean validCard(AbstractCard paramAbstractCard);
}
