package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.defect.CompileDriverAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class CompileMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(CompileMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public boolean validCard(AbstractCard card) {
        return allowOrbMods()
                && AugmentHelper.isPlayable(card)
                && AugmentHelper.isNormal(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new CompileDriverAction(AbstractDungeon.player, EFFECT));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.contains(CARD_TEXT[2])) {
            return rawDescription.replace(CARD_TEXT[2], String.format(CARD_TEXT[1], EFFECT + 1));
        } else {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[0], EFFECT));
        }
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier) new CompileMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
