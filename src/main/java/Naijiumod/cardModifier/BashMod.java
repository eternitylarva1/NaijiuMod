package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class BashMod extends AbstractAugment {
  public static final String ID = ModHelper.makePath(BashMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  public static final int EFFECT = 1;
  
  private boolean modifiedBase;
  
  public void onInitialApplication(AbstractCard card) {
    if (card instanceof com.megacrit.cardcrawl.cards.red.Bash || card instanceof com.megacrit.cardcrawl.cards.blue.BeamCell || card instanceof com.megacrit.cardcrawl.cards.purple.CrushJoints) {
      card.baseMagicNumber++;
      card.magicNumber++;
      this.modifiedBase = true;
    } 
  }
  
  public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
    return damage * 0.75F;
  }
  
  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    if (!this.modifiedBase && target != null)
      addToBot((AbstractGameAction)new ApplyPowerAction(target, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new VulnerablePower(target, 1, false))); 
  }
  
  public boolean validCard(AbstractCard card) {
    return (card.cost >= 0 && card.type == AbstractCard.CardType.ATTACK && card.baseDamage > 1 && cardCheck(card, c -> usesEnemyTargeting()));
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
    if (this.modifiedBase)
      return rawDescription; 
    return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(1) }));
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.UNCOMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new BashMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
}
