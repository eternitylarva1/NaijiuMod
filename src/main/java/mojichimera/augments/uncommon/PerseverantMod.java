package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

public class PerseverantMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PerseverantMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public static final float EXTRA_MULTIPLIER = 0.5F;

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasBlock(card);
    }

    @Override
    public float modifyBlockFinal(float block, AbstractCard card) {
        if (!MojiHelper.isInCombat())
            return block;
        return block * ((1 - (float)AbstractDungeon.player.currentHealth / (float)AbstractDungeon.player.maxHealth) * EXTRA_MULTIPLIER + 1);
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PerseverantMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
