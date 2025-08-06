package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.mojichimera;
import mojichimera.powers.SteelizePower;

public class SteelMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(SteelMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;
    private static final int PERCENT = 50;

    @Override
    public void onDrawn(AbstractCard card) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SteelizePower(AbstractDungeon.player, EFFECT), EFFECT));
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return true;
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], EFFECT, PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SteelMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
