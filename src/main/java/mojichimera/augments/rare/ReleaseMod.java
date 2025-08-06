package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.powers.EveryTurnStartPlayPower;

public class ReleaseMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ReleaseMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.addModifier(card, new ExhaustMod());
        card.costForTurn = ++card.cost;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasStaticCost(card)
                && AugmentHelper.isAttackOrSkill(card)
                && AugmentHelper.isPowerizeValid(card)
                && !AugmentHelper.hasChangeTypeMod(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard c = card.makeStatEquivalentCopy();
        CardModifierManager.removeModifiersById(c, ID, true);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EveryTurnStartPlayPower(AbstractDungeon.player, c, EFFECT), EFFECT));
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ReleaseMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}