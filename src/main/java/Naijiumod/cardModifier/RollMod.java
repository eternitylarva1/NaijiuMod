package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class RollMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(RollMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  public float modifyBaseBlock(float block, AbstractCard card) {
    return block * 0.6666667F;
  }
  
  public boolean validCard(AbstractCard card) {
    return (card.cost != -2 && card.baseBlock >= 3);
  }
  
  public String getPrefix() {
    return TEXT[0];
  }
  
  public String getSuffix() {
    return TEXT[1];
  }
  
  public String getAugmentDescription() {
    return TEXT[2];
  }
  
  public String modifyDescription(String rawDescription, AbstractCard card) {
    return insertAfterText(rawDescription, CARD_TEXT[0]);
  }
  
  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new NextTurnBlockPower((AbstractCreature)AbstractDungeon.player, card.block)));
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.COMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new RollMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
