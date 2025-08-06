package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.DynvarCarrier;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.mojichimera;
import mojichimera.util.OnManualDiscardSubscriber;

public class ReflexMod extends AbstractAugment implements DynvarCarrier, OnManualDiscardSubscriber {
    public static final String ID = mojichimera.makeID(ReflexMod.class.getSimpleName());
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
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (card instanceof Reflex) {
            return magic + getBaseVal(card);
        }
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return true;
    }

    @Override
    public void onManualDiscard(AbstractCard card) {
        addToBot(new DrawCardAction(AbstractDungeon.player, getBaseVal(card)));
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        card.initializeDescription();
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof Reflex) {
            return rawDescription;
        }
        return insertAfterText(rawDescription, String.format(card.upgraded ? CARD_TEXT[1] : CARD_TEXT[0], new Object[] { DESCRIPTION_KEY }));
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ReflexMod(); }

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
