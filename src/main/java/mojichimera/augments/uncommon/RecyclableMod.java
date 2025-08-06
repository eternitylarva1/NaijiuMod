package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class RecyclableMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(RecyclableMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
//        if (!card.isEthereal)
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
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new EnergizedBluePower((AbstractCreature)AbstractDungeon.player, EFFECT)));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (rawDescription.contains(CARD_TEXT[0])) {
            return insertAfterText(rawDescription, CARD_TEXT[1]);
//        }
//        return insertAfterText(insertBeforeText(rawDescription, CARD_TEXT[0]), CARD_TEXT[1]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new RecyclableMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
