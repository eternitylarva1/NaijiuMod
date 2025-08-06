package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.DynvarCarrier;
import Naijiumod.utils.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.OnManualDiscardSubscriber;

public class EducationalMod extends AbstractAugment implements DynvarCarrier, OnManualDiscardSubscriber {
    public static final String ID = mojichimera.makeID(EducationalMod.class.getSimpleName());
    public static final String DESCRIPTION_KEY = "!" + ID + "!";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;
    private static final int UPGRADE_EFFECT = 1;
    public boolean modified;
    public boolean upgraded;

    public int getBaseVal(AbstractCard card) {
        return EFFECT + getEffectiveUpgrades(card) * UPGRADE_EFFECT;
    }

    /* AbstractAugment */

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card);
    }

    @Override
    public void onManualDiscard(AbstractCard card) {
        for(int i = 0; i < this.getBaseVal(card); ++i) {
            Wiz.atb(new UpgradeRandomCardAction());
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
        return insertAfterText(rawDescription, String.format(card.upgraded ? CARD_TEXT[1] : CARD_TEXT[0], new Object[] { DESCRIPTION_KEY }));
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new EducationalMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    /* DynvarCarrier */
    @Override
    public String key() { return ID; }

    @Override
    public int val(AbstractCard card) { return getBaseVal(card); }

    @Override
    public int baseVal(AbstractCard card) { return getBaseVal(card); }

    @Override
    public boolean modified(AbstractCard card) { return this.modified; }

    @Override
    public boolean upgraded(AbstractCard card) {
        this.modified = (card.timesUpgraded != 0 || card.upgraded);
        this.upgraded = (card.timesUpgraded != 0 || card.upgraded);
        return this.upgraded;
    }
}
