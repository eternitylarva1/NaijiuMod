package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AbsoluteMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(AbsoluteMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  boolean modMagic;
  
  public void onInitialApplication(AbstractCard card) {
    if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber >= 3)))
      this.modMagic = true; 
  }
  
  public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
    if (card.baseDamage > 0)
      return damage * 1.3333334F; 
    return damage;
  }
  
  public float modifyBaseBlock(float block, AbstractCard card) {
    if (card.baseBlock > 0)
      return block * 1.3333334F; 
    return block;
  }
  
  public float modifyBaseMagic(float magic, AbstractCard card) {
    if (this.modMagic)
      return magic * 1.3333334F; 
    return magic;
  }
  
  public boolean validCard(AbstractCard card) {
    return ((card.cost > 0 || card.cost == -1) && (card.baseDamage > 0 || card.baseBlock > 0 || cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber >= 3))) && card.rarity != AbstractCard.CardRarity.BASIC);
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
    return rawDescription + CARD_TEXT[0];
  }
  
  public boolean betterCanPlay(AbstractCard cardWithThisMod, AbstractCard cardToCheck) {
    if (cardWithThisMod == cardToCheck || hasThisMod(cardToCheck))
      return true; 
    cardToCheck.cantUseMessage = CARD_TEXT[2] + cardWithThisMod.name + CARD_TEXT[3];
    return false;
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.UNCOMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new AbsoluteMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
