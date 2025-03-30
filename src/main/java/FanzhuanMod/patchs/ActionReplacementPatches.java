package FanzhuanMod.patchs;
/*
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

public class ActionReplacementPatches {
  public static AbstractCard cardInUse;
  
  public static AbstractMonster target;
  
  @SpirePatch(clz = AbstractCard.class, method = "<class>")
  public static class InvertedFields {
    public static SpireField<Boolean> isInverted = new SpireField(() -> Boolean.valueOf(false));
    
    public static SpireField<Boolean> toBlock = new SpireField(() -> Boolean.valueOf(false));
  }
  
  @SpirePatches2({@SpirePatch2(clz = GameActionManager.class, method = "addToTop"), @SpirePatch2(clz = GameActionManager.class, method = "addToBottom")})
  public static class ReplaceActions {
    @SpirePrefixPatch
    public static void plz(GameActionManager __instance, @ByRef AbstractGameAction[] action) {
      if ((action[0].getClass().equals(DamageAction.class) || action[0].getClass().equals(GainBlockAction.class)) && 
        ActionReplacementPatches.cardInUse != null && ((Boolean)ActionReplacementPatches.InvertedFields.isInverted.get(ActionReplacementPatches.cardInUse)).booleanValue())
        if (((Boolean)ActionReplacementPatches.InvertedFields.toBlock.get(ActionReplacementPatches.cardInUse)).booleanValue()) {
          action[0] = (AbstractGameAction)new GainBlockAction((AbstractCreature)AbstractDungeon.player, ActionReplacementPatches.cardInUse.block);
        } else {
          if (ActionReplacementPatches.target == null)
            ActionReplacementPatches.target = AbstractDungeon.getRandomMonster(); 
          action[0] = (AbstractGameAction)new DamageAction((AbstractCreature)ActionReplacementPatches.target, new DamageInfo((AbstractCreature)AbstractDungeon.player, ActionReplacementPatches.cardInUse.damage, ActionReplacementPatches.cardInUse.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE);
        }  
    }

  }
  
  @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
  public static class GrabCardInUse {
    @SpireInsertPatch(locator = Locator.class)
    public static void RememberCardPreUseCall(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
      ActionReplacementPatches.cardInUse = c;
      ActionReplacementPatches.target = monster;
    }
    
    @SpireInsertPatch(locator = Locator2.class)
    public static void ForgetCardPostUseCall(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
      ActionReplacementPatches.cardInUse = null;
      ActionReplacementPatches.target = null;
    }
    
    private static class Locator2 extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
        return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
      }
    }
    
    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
        return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
      }
    }
  }
}
*/