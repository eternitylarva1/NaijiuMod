package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.mojichimera;

public class OnSaleMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(OnSaleMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int COST = 1;

//    @Override
//    public void onInitialApplication(AbstractCard card) {
//        upgradeCost(card);
//    }

    @Override
    public boolean atBattleStartPreDraw(AbstractCard card) {
        upgradeCost(card);
        return false;
    }

    @Override
    public void onCreatedMidCombat(AbstractCard card) {
        upgradeCost(card);
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        upgradeCost(card);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost >= (card.upgraded ? 1 : 2)
                && cardCheck(card, c -> !doesntUpgradeCost())
                && doesntOverride(card, "canUpgrade", new Class[0]);
    }

    private void upgradeCost(AbstractCard card) {
        if (card.upgraded) {
            AbstractCard copy = card.makeCopy();
            copy.upgrade();
            card.cost = copy.cost - COST;
            card.costForTurn = card.cost;
        }
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new OnSaleMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}

