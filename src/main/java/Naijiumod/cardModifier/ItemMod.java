package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;

import java.util.ArrayList;

import static Naijiumod.patchs.OnCardGeneratedPatches.getCardnaijiu;

public class ItemMod extends AbstractCardModifier {
  public static final String ID = ModHelper.makePath("ItemMod");
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public int uses;
  
  boolean masterCardRemoved = false;
  
  public ItemMod(int uses) {
    this.uses = uses;
  }
  @Override
  public boolean isInherent(AbstractCard card) {
    return true;
  }
  @Override
  public boolean shouldApply(AbstractCard card) {
    return !CardModifierManager.hasModifier(card, ID);
  }
  @Override
  public String identifier(AbstractCard card) {
    return ID;
  }
  
  public String modifyDescription(String rawDescription, AbstractCard card) {
    return rawDescription + TEXT[0] + this.uses+"/"+getCardnaijiu(card);

  }

  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    this.uses--;
    card.initializeDescription();
    AbstractCard masterCard = StSLib.getMasterDeckEquivalent(card);
    if (masterCard != null) {
      ItemMod itemMod = null;
      ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(masterCard, identifier(null));
      for (AbstractCardModifier mod : mods) {
        if (mod instanceof ItemMod) {
          itemMod = (ItemMod)mod;
          itemMod.uses--;
        } 
      } 
      masterCard.initializeDescription();
      if (itemMod != null && itemMod.uses == 0) {
        AbstractDungeon.player.masterDeck.removeCard(masterCard);
        this.masterCardRemoved = true;
      } 
    } 
  }
  
  public AbstractCardModifier makeCopy() {
    return new ItemMod(this.uses);
  }
}
