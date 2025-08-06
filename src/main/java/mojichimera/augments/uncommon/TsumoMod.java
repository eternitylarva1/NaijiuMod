package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class TsumoMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(TsumoMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 1.5F;
    private static final int PERCENT = 50;
    private static final int PLAYS = 2;
    public static final SpireField<Integer> lastPlayedTurn = new SpireField<>(() -> 0);
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * getMultiplier(card);
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * getMultiplier(card);
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return magic * getMultiplier(card);
        return magic;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        lastPlayedTurn.set(card, AbstractDungeon.actionManager.turn);
    }

    private float getMultiplier(AbstractCard card) {
        if (!MojiHelper.isInCombat())
            return 1.0F;
        return hasPlayedThisTurn(card) ? MULTIPLIER : 1.0F;
    }

    private boolean hasPlayedThisTurn(AbstractCard card) {
        return lastPlayedTurn.get(card) == AbstractDungeon.actionManager.turn;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isReplayable(card)
                && AugmentHelper.reachesVariable(card, 2);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PLAYS, PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new TsumoMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    private boolean shouldGlow(AbstractCard card) {
        if (!MojiHelper.isInCombat()) return false;
        return hasPlayedThisTurn(card);
    }

    public CardBorderGlowManager.GlowInfo getGlowInfo() {
        return new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return TsumoMod.this.hasThisMod(card) && TsumoMod.this.shouldGlow(card);
            }

            public Color getColor(AbstractCard card) {
                return Color.GOLD.cpy();
            }

            public String glowID() {
                return TsumoMod.ID + "Glow";
            }
        };
    }
}
