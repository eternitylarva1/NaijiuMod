package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

import java.util.ArrayList;

public class GluttonousMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(GluttonousMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int CARDS = 1;

    public GluttonousMod() {
        this.priority = -1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard preview = getPreview(card);
                if (preview == null) {
                    if (!AbstractDungeon.player.hand.isEmpty()) {
                        ArrayList<AbstractCard> edible = new ArrayList<>();
                        for (AbstractCard c : AbstractDungeon.player.hand.group) {
                            if (isEdible(c)) {
                                edible.add(c);
                            }
                        }
                        if (!edible.isEmpty()) {
                            AbstractCard c = edible.get(AbstractDungeon.cardRandomRng.random(edible.size() - 1));
                            AbstractDungeon.player.hand.moveToExhaustPile(c);
//                            CardModifierManager.addModifier(c, (AbstractCardModifier)new PreviewedMod());
//                            MultiCardPreview.add(card, new AbstractCard[] { c });
                            card.cardsToPreview = c;
                            preview = c;
                            card.initializeDescription();
                        }
                    }
                }
                if (preview != null) {
                    AbstractCard copy = preview.makeStatEquivalentCopy();
                    copy.purgeOnUse = true;
                    if (copy.target == AbstractCard.CardTarget.ENEMY && target != null && target instanceof AbstractMonster && !target.isDeadOrEscaped()) {
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy, (AbstractMonster) target, card.energyOnUse, true, true));
                    } else {
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy, true, card.energyOnUse, true, true));
                    }
                }
                this.isDone = true;
            }
        });
    }

    private boolean isEdible(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.isAttackOrSkill(card)
                && noShenanigans(card);
    }

    private AbstractCard getPreview(AbstractCard card) {
        if (!MojiHelper.isInCombat()) return null;
//        for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
//            if (CardModifierManager.hasModifier(c, PreviewedMod.ID))
//                return c;
//        }
//        return null;
        return card.cardsToPreview;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isReplayable(card)
                && !AugmentHelper.hasMultiPreviewMod(card, GluttonousMod.ID);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        AbstractCard preview = getPreview(card);
        if (preview == null) {
            return insertAfterText(rawDescription, CARD_TEXT[0]);
        } else {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[1], preview.name));
        }
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new GluttonousMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

//    public boolean isInherent(AbstractCard card) {
//        return this.inherentHack;
//    }
}
