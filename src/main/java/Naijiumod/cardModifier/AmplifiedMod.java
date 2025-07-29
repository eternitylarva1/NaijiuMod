package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AmplifiedMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(AmplifiedMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  private int baseCost;
  
  public void onInitialApplication(AbstractCard card) {
    this.baseCost = Math.max(1, card.cost);
    card.costForTurn = ++card.cost;
  }
  
  public float modifyBaseMagic(float magic, AbstractCard card) {
    return magic * (this.baseCost + 1.0F) / this.baseCost;
  }
  
  public boolean validCard(AbstractCard card) {
    return (card.cost >= 0 && cardCheck(card, c -> (c.baseDamage == -1 && c.baseBlock == -1 && doesntUpgradeCost() && doesntDowngradeMagic() && c.baseMagicNumber > 0 && card.cost <= c.baseMagicNumber)));
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
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.UNCOMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new AmplifiedMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
