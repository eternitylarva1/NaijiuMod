package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SlayerMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(SlayerMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
    return damage * 0.75F;
  }
  
  public boolean validCard(AbstractCard card) {
    return (card.baseDamage > 1 && card.type == AbstractCard.CardType.ATTACK);
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
  
  public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
    if (target == null)
      return damage; 
    return (AbstractDungeon.getMonsters()).monsters.stream().noneMatch(m -> (!m.isDeadOrEscaped() && m.currentHealth < target.currentHealth)) ? (damage * 2.0F) : damage;
  }
  
  public String modifyDescription(String rawDescription, AbstractCard card) {
    return insertAfterText(rawDescription, CARD_TEXT[0]);
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.COMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new SlayerMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
