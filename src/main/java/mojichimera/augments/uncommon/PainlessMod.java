package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.DynvarCarrier;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class PainlessMod extends AbstractAugment implements DynvarCarrier {
    public static final String ID = mojichimera.makeID(PainlessMod.class.getSimpleName());
    public static final String DESCRIPTION_KEY = "!" + ID + "!";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 5;
    private static final int UPGRADE_EFFECT = 2;
    public boolean modified;
    public boolean upgraded;

    public int getBaseVal(AbstractCard card) {
        return EFFECT + getEffectiveUpgrades(card) * UPGRADE_EFFECT;
    }

    /* AbstractAugment */

    @Override
    public void onInitialApplication(AbstractCard card) {
            CardModifierManager.addModifier(card, new EtherealMod());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isEtherealValid(card)
                && AugmentHelper.isPlayable(card);
    }

    @Override
    public void onExhausted(AbstractCard card) {
        card.flash(Color.RED.cpy());
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, getBaseVal(card)));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { DESCRIPTION_KEY }));
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PainlessMod(); }

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
