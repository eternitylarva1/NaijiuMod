package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;
import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ShivMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(ShivMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  private static final int SHIVS = 1;
  
  public boolean validCard(AbstractCard card) {
    return (card.cost != -2 && (card.baseBlock > 1 || card.cardsToPreview instanceof Shiv));
  }
  
  public void onInitialApplication(AbstractCard card) {
 
  }
  
  public float modifyBaseBlock(float block, AbstractCard card) {
    if (card.baseBlock > 1)
      return block * 0.8F; 
    return block;
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
    return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(1) }));
  }
  
  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    addToBot((AbstractGameAction)new MakeTempCardInHandAction((AbstractCard)new Shiv(), 1));
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.COMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new ShivMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
