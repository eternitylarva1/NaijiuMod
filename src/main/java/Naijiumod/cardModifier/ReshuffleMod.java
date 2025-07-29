package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;
import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ReshuffleMod extends AbstractAugment {
  public static final String ID =  ModHelper.makePath(ReshuffleMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  public void onInitialApplication(AbstractCard card) {
    card.shuffleBackIntoDrawPile = true;
  }
  
  public boolean validCard(AbstractCard card) {
    return (card.cost != -2 && card.type != AbstractCard.CardType.POWER && cardCheck(card, c -> (notReshuffle(c) && notExhaust(c))));
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
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.COMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new ReshuffleMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
