package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class CatalystMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(CatalystMod.class.getSimpleName());
    public static final String DESCRIPTION_KEY = "!" + ID + "!";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;
    private static final int PERCENT = 200;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.costForTurn = ++card.cost;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.isAttackOrSkill(card)
                && (usesAction(card, BouncingFlaskAction.class) || usesClass(card, PoisonPower.class))
                && AugmentHelper.hasStaticCost(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDeadOrEscaped() && mo.hasPower("Poison")) {
                        int poison = mo.getPower("Poison").amount;
                        addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new PoisonPower(mo, AbstractDungeon.player, poison * EFFECT), poison * EFFECT));
                    }
                }

                this.isDone = true;
            }
        });
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENT));
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new CatalystMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}