/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */

import Naijiumod.helpers.ModHelper;
import Naijiumod.hook.MyModConfig;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

/*    */
/*    */ public class MoreNaijiuMod extends AbstractAugment {
/* 10 */   public static final String ID = ModHelper.makePath(MoreNaijiuMod.class.getSimpleName());
/* 11 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*    */   
/*    */   private int baseCost;
private int useAmount=0;
/*    */ 
/*    */@Override
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    this.useAmount++;
    if (this.useAmount==2) {
        this.useAmount = 0;
        if (CardModifierManager.hasModifier(card, ItemMod.ID)) {
            ItemMod itemMod = (ItemMod) CardModifierManager.getModifiers(card, ItemMod.ID).get(0);
            itemMod.uses++;
            card.initializeDescription();
            AbstractCard masterCard = StSLib.getMasterDeckEquivalent(card);
            if (masterCard != null) {
                itemMod = null;
                ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(masterCard, identifier(null));
                for (AbstractCardModifier mod : mods) {
                    if (mod instanceof ItemMod) {
                        itemMod = (ItemMod) mod;
                        itemMod.uses--;
                        if (itemMod.damo < MyModConfig.Damo) {
                            itemMod.damo++;
                        }
                    }
                }
                masterCard.initializeDescription();
            }
        }
        /* 41 */
        /*    */
    }}
/*    */   public void onInitialApplication(AbstractCard card) {
             this.useAmount=0;
/*    */   }
/*    */
            @Override
            public boolean isInherent(AbstractCard card) {
                return true;
            }
/*    */
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 29 */     return (CardModifierManager.hasModifier(card, ItemMod.ID)&&!CardModifierManager.hasModifier(card, this.identifier( card)));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 34 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 39 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 44 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public AugmentRarity getModRarity() {
/* 49 */     return AugmentRarity.COMMON;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 54 */     return (AbstractCardModifier)new MoreNaijiuMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 59 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\FormMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */