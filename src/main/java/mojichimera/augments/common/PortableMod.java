package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class PortableMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PortableMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int ENERGY = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost--;
        if (card.cost < 0)
            card.cost = 0;
        card.costForTurn = card.cost;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasStaticCost(card, 1);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new LoseEnergyAction(ENERGY));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PortableMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
