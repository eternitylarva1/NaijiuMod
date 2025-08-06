package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;

import Naijiumod.patchs.EchoFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class AttentiveMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(AttentiveMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int CARDS = 2;

    @Override
    public void onInitialApplication(AbstractCard card) {
        EchoFieldPatches.EchoFields.echo.set(card, (Integer) EchoFieldPatches.EchoFields.echo.get(card) + 1);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.isEchoValid(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!card.purgeOnUse) {
            addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, CARDS, true));
        }
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], CARDS));
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new AttentiveMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}

