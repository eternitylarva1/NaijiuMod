package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AngryMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(AngryMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  public boolean validCard(AbstractCard card) {
    return (card.type == AbstractCard.CardType.ATTACK && card.cost != -2);
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
    if (rawDescription.contains(CARD_TEXT[2]))
      return rawDescription.replace(CARD_TEXT[2], CARD_TEXT[3]); 
    return insertAfterText(rawDescription, CARD_TEXT[0]);
  }
  
  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    addToBot((AbstractGameAction)new MakeTempCardInDiscardAction(card.makeStatEquivalentCopy(), 1));
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.UNCOMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new AngryMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
