package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;
import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.FollowUpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FollowUpMod extends AbstractAugment {
  public static final String ID =  ModHelper.makePath(FollowUpMod.class.getSimpleName());
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
  
  public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
    return damage * 0.8F;
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
  
  public String modifyDescription(String rawDescription, AbstractCard card) {
    if (rawDescription.contains(CARD_TEXT[2]))
      return rawDescription.replace(CARD_TEXT[2], CARD_TEXT[3]); 
    return insertAfterText(rawDescription, CARD_TEXT[0]);
  }
  
  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    addToBot((AbstractGameAction)new FollowUpAction());
  }
  
  public AbstractAugment.AugmentRarity getModRarity() {
    return AbstractAugment.AugmentRarity.COMMON;
  }
  
  public AbstractCardModifier makeCopy() {
    return (AbstractCardModifier)new FollowUpMod();
  }
  
  public String identifier(AbstractCard card) {
    return ID;
  }
  
  public CardBorderGlowManager.GlowInfo getGlowInfo() {
    return new CardBorderGlowManager.GlowInfo() {
        public boolean test(AbstractCard abstractCard) {
          return (FollowUpMod.this.hasThisMod(abstractCard) && FollowUpMod.this.lastCardPlayedCheck(c -> (c.type == AbstractCard.CardType.ATTACK)));
        }
        
        public Color getColor(AbstractCard abstractCard) {
          return Color.GOLD.cpy();
        }
        
        public String glowID() {
          return FollowUpMod.ID + "Glow";
        }
      };
  }
}
