package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

public class PeaceMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PeaceMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public static final int DRAW = 2;

    @Override
    public boolean validCard(AbstractCard card) {
        return card.color == AbstractCard.CardColor.PURPLE
                && AugmentHelper.isAttackOrSkill(card)
                && AugmentHelper.isPlayable(card)
                && !usesAction(card, ChangeStanceAction.class)
                && !usesClass(card, MantraPower.class);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (AbstractDungeon.player.stance.ID.equals("Calm")) {
            addToBot((AbstractGameAction)new DrawCardAction(DRAW));
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], DRAW));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PeaceMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    private boolean shouldGlow(AbstractCard card) {
        if (!MojiHelper.isInCombat()) return false;
        return AbstractDungeon.player.stance.ID.equals("Calm");
    }

    public CardBorderGlowManager.GlowInfo getGlowInfo() {
        return new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return PeaceMod.this.hasThisMod(card) && PeaceMod.this.shouldGlow(card);
            }

            public Color getColor(AbstractCard card) {
                return Color.GOLD.cpy();
            }

            public String glowID() {
                return PeaceMod.ID + "Glow";
            }
        };
    }
}
