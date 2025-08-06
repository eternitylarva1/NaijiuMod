package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.mojichimera;

public class ReinforcedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ReinforcedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int UPGRADE_BLOCK = 4;

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.upgraded)
            return block + getEffectiveUpgrades(card) * UPGRADE_BLOCK;
        return block;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return cardCheck(card, c -> upgradesBlock());
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ReinforcedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
