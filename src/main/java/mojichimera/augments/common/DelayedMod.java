package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;

import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.utils.PortraitHelper;
import Naijiumod.utils.Wiz;
import Naijiumod.cardModifier.PreviewedMod;
import Naijiumod.utils.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.powers.NextTurnStartPlayPower;

public class DelayedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(DelayedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private boolean inherentHack = true;
    private static final int TURN = 1;
    private static final int COPY = 1;

    public DelayedMod() {
        this.priority = 1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.inherentHack = true;
        AbstractCard preview = card.makeStatEquivalentCopy();
        this.inherentHack = false;
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[] { preview });
        card.target = AbstractCard.CardTarget.SELF;
        if (preview.type == AbstractCard.CardType.POWER) {
            CardModifierManager.addModifier(card, new ExhaustMod());
        }
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        if (card.type != AbstractCard.CardType.SKILL) {
            card.type = AbstractCard.CardType.SKILL;
            PortraitHelper.setMaskedPortrait(card);
        }
        card.cost--;
        if (card.cost < 0) {
            card.cost = 0;
        }
        card.costForTurn = card.cost;
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
            if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
                c.upgrade();
                c.initializeDescription();
            }
        }
        card.initializeDescription();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard preview = null;
                for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
                    if (CardModifierManager.hasModifier(c, PreviewedMod.ID))
                        preview = c;
                }
                if (preview != null) {
                    AbstractCard copy = preview.makeStatEquivalentCopy();
                    Wiz.applyToSelf(new NextTurnStartPlayPower(AbstractDungeon.player, copy, TURN, COPY));
//                    Wiz.applyToSelf(new BombPower(AbstractDungeon.player, TURN, COPY, copy));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.isNormal(card)
                && AugmentHelper.hasStaticCost(card, 1)
                && noShenanigans(card)
                && !AugmentHelper.hasMultiPreviewMod(card, DelayedMod.ID);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertBeforeText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new DelayedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public boolean isInherent(AbstractCard card) {
        return this.inherentHack;
    }
}
