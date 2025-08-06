package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class StrangeMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(StrangeMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    public StrangeMod() {
        this.priority = 1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = false;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.exhaust)
                && cardCheck(card, c -> doesntUpgradeExhaust())
                && AugmentHelper.isNormal(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.contains(CARD_TEXT[3])) {
            return rawDescription.replace(CARD_TEXT[3], CARD_TEXT[4]);
        }
        if (rawDescription.contains(CARD_TEXT[1])) {
            return rawDescription.replace(CARD_TEXT[1], CARD_TEXT[2]);
        }
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new StrangeMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
