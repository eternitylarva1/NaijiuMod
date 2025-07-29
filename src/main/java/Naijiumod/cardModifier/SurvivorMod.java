package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SurvivorMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(SurvivorMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  public float modifyBaseBlock(float block, AbstractCard card) {
    return block * 1.3333334F;
  }
  
  public boolean validCard(AbstractCard card) {
    return (card.cost != -2 && card.baseBlock >= 3);
  }
  
  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    addToBot((AbstractGameAction)new DiscardAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, 1, false));
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
    return rawDescription + String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(1) });
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.COMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new SurvivorMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
