package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.powers.LosePowerPower;

public class PostpaidMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PostpaidMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int DISCARD = 1;
    private static final int RETAIN = 2;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, DISCARD, false));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RetainCardPower(AbstractDungeon.player, RETAIN), RETAIN));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LosePowerPower(AbstractDungeon.player, new RetainCardPower(AbstractDungeon.player, RETAIN), RETAIN), RETAIN));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], DISCARD, RETAIN));
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PostpaidMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}

