package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.powers.LosePowerPower;

public class FocusedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(FocusedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 2;

    @Override
    public boolean validCard(AbstractCard card) {
        return allowOrbMods()
                && AugmentHelper.isPlayable(card)
                && AugmentHelper.isNormal(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new FocusPower((AbstractCreature)AbstractDungeon.player, EFFECT)));
//        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new LoseFocusPower((AbstractCreature)AbstractDungeon.player, EFFECT)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LosePowerPower(AbstractDungeon.player, new FocusPower(AbstractDungeon.player, EFFECT), EFFECT), EFFECT));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], EFFECT, EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier) new FocusedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
