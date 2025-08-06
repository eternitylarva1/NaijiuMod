package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class ConcentratedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ConcentratedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int CARDS = 3;
    private static final int UPGRADE_CARDS = 2;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost -= 2;
        if (card.cost < 0)
            card.cost = 0;
        card.costForTurn = card.cost;
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        card.initializeDescription();
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasStaticCost(card, 2);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, card.upgraded ? UPGRADE_CARDS : CARDS, false));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], card.upgraded ? UPGRADE_CARDS : CARDS));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ConcentratedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}

