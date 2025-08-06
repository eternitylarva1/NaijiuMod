package Naijiumod.cardModifier;


import Naijiumod.helpers.ModHelper;
import Naijiumod.hook.LoadMySpireMod;
import Naijiumod.hook.MyModConfig;
import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;import Naijiumod.helpers.ModHelper;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;
import mojichimera.mojichimera;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Naijiumod.patchs.OnCardGeneratedPatches.getCardnaijiu;

public  class ItemMod extends AbstractCardModifier {
  public static final String ID = ModHelper.makePath("ItemMod");
  
  public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
  
  public int uses;
  
  boolean masterCardRemoved = false;
  public  int damo=0;
  public static ArrayList<AbstractAugment> modmap = new ArrayList<>();
  
  public ItemMod(int uses,int damo) {
    this.uses = uses;
    this.damo=damo;

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

    return rawDescription + " NL "+TEXT[0] + this.uses+"/"+getCardnaijiu(card)+" NL "+TEXT[1]+this.damo+"/"+ MyModConfig.Damo;

  }
  public static AbstractCard addTrueRandomModifier(AbstractCard c) {
    ArrayList<AbstractAugment> values = getModifiers();
    AbstractAugment randomValue = values.get(new Random().nextInt(values.size()-1));

    CardModifierManager.addModifier(c, randomValue);
    return c;
  }
public static ArrayList<AbstractAugment> getModifiers() {
  ArrayList<AbstractAugment> values = new ArrayList<>( LoadMySpireMod.modMap.values());
// 随机获取一个值

  return values ;
}
  public static AbstractCard addRandomModifier(AbstractCard c) {
  ArrayList<AbstractAugment> values = getModifiers();
    AbstractAugment randomValue = values.get(AbstractDungeon.cardRandomRng.random(values.size() - 1));

while(!randomValue.canApplyTo( c)&&values.size()>1){
   values.remove(randomValue);
  randomValue = values.get(AbstractDungeon.cardRandomRng.random(values.size() - 1));
}
    CardModifierManager.addModifier(c, randomValue);
    return c;
  }
  public List<TooltipInfo> additionalTooltips(AbstractCard card)
  {List <TooltipInfo> ret = new ArrayList<>();
    ret.add(new TooltipInfo(TEXT[0],TEXT[2]));
    ret.add(new TooltipInfo(TEXT[1],TEXT[3]));
    return ret ;
  }
  public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    this.uses--;
    if (this.damo < MyModConfig.Damo) {
      this.damo++;
    }
    card.initializeDescription();
    AbstractCard masterCard = StSLib.getMasterDeckEquivalent(card);
    if (masterCard != null) {
      ItemMod itemMod = null;
      ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(masterCard, identifier(null));
      for (AbstractCardModifier mod : mods) {
        if (mod instanceof ItemMod) {
          itemMod = (ItemMod)mod;
          itemMod.uses--;
          if (itemMod.damo < MyModConfig.Damo) {
            itemMod.damo++;
          }
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
    return new ItemMod(this.uses, this.damo);
  }



}
